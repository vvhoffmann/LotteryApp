package pl.vvhoffmann.lotteryapp.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.vvhoffmann.lotteryapp.BaseIntegrationTest;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersNotFoundException;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.NumberReceiverResponseDto;
import pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.ResultNotFoundException;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.ResultCheckerFacade;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.ResultDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class UserPlayerLotteryAndWonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    public WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;

    @Autowired
    public ResultCheckerFacade resultCheckerFacade;

    @Test
    public void should_user_win_and_should_system_generate_winner() throws Exception {
        //step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [1, 2, 3, 4, 5, 6, 82, 82, 83, 83, 86, 57, 10, 81, 53, 93, 50, 54, 31, 88, 15, 43, 79, 32, 43]
                                """.trim()
                        )));


        //step 2: system fetched winning numbers for draw date: 12.10.2024 12:00
        // given
        LocalDateTime drawDate = LocalDateTime.of(2024, 10, 12, 12, 0, 0);
        //when && then
        await().atMost(Duration.ofSeconds(24))
                .pollInterval(Duration.ofSeconds(1L))
                .until(() -> {
                            try {
                                return !winningNumbersGeneratorFacade.retrieveWinningNumbersByDrawDate(drawDate).winningNumbers().isEmpty();
                            } catch (WinningNumbersNotFoundException e) {
                                return false;
                            }
                        }
                );


        //step 3: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 10-10-2024 11:00 and system returned OK(200) with message: “success” and Ticket
        // (DrawDate:12.10.2024 12:00 (Saturday), TicketId: sampleTicketId)
        //given
        //when
        final ResultActions performInputNumbers = mockMvc.perform(post("/inputNumbers")
                .content("""
                        {
                        "inputNumbers": [1,2,3,4,5,6]
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        final MvcResult mvcResult = performInputNumbers.andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        final NumberReceiverResponseDto responseDto = objectMapper.readValue(response, NumberReceiverResponseDto.class);
        String ticketId = responseDto.ticketDto().ticketId();
        assertAll(
                () -> assertThat(responseDto.ticketDto().drawDate()).isEqualTo(drawDate),
                () -> assertThat(responseDto.ticketDto().numbers()).isEqualTo(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6))),
                () -> assertThat(ticketId).isNotNull(),
                () -> assertThat(responseDto.message()).isEqualTo("SUCCESS")
        );
        log.info("registered ticket with id " + ticketId);


        //step 4: user made GET /results/notExistingId and system returned 404(NOT_FOUND) and body with
        // (message: Not found for id: notExistingId and status NOT_FOUND)
        //given
        String id ="123";
        String url = "/results/" + id;
        //when
        final ResultActions performGetResultWithNoExistingId = mockMvc.perform(get(url));
        //then
        performGetResultWithNoExistingId.andExpect(status().isNotFound())
                .andExpect(content().json(
                    """
                    {
                            "message": "Result with id 123 not found",
                            "status": "NOT_FOUND"
                    }
                    """.trim()
                ));


        //step 5: 2 days and 55 minutes passed, and it is 5 minute before draw (12.10.2024 11:55)
        // given && when && then
        clock.plusDaysAndMinutes(2, 55);
        log.info("2 days and 55 minutes passed");

        //step 6: system generated result for TicketId: sampleTicketId with draw date 12.10.2024 12:00, and saved it with 6 hits
        await().atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1L))
                        .until(
                                () -> {
                                    try{
                                        final ResultDto result = resultCheckerFacade.findByTicketId(ticketId);
                                        return !result.numbers().isEmpty();
                                    } catch (ResultNotFoundException e) {
                                        return false;
                                    }
                                }
                        );


        //step 7: 6 minutes passed and it is 1 minute after the draw (12.10.2024 12:01)
        clock.plusMinutes(6);
        log.info("6 minutes passed");


        //step 8: user made GET /results/sampleTicketId and system returned 200 (OK)
        //given
        url = "/results/" + ticketId;
        log.info(url);
        //when
        final ResultActions performGetResultWithExistingId = mockMvc.perform(get(url));
        //then
        final MvcResult mvcResultGetMethod = performGetResultWithExistingId.andExpect(status().isOk()).andReturn();
        final String jsonFromGetMethod = mvcResultGetMethod.getResponse().getContentAsString();
        final ResultAnnouncerResponseDto resultAnnouncerResponseDto = objectMapper.readValue(jsonFromGetMethod, ResultAnnouncerResponseDto.class);
        assertAll(
                () -> assertThat(resultAnnouncerResponseDto.resultResponseDto().id()).isEqualTo(ticketId),
                () -> assertThat(resultAnnouncerResponseDto.message()).isEqualTo("Congratulations, you won!"),
                () -> assertThat(resultAnnouncerResponseDto.resultResponseDto().hitNumbers()).hasSize(6)
        );
    }
}