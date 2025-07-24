package pl.vvhoffmann.lotteryapp.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.vvhoffmann.lotteryapp.BaseIntegrationTest;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersNotFoundException;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.NumberReceiverResponseDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserPlayerLotteryAndWonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    public WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;

    @Test
    public void should_user_win_and_should_system_generate_winner() throws Exception {
        //step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given
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
        //when
        //then
        await().atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(5))
                .until(() -> {
                            try {
                                return !winningNumbersGeneratorFacade.retrieveWinningNumbersByDrawDate(drawDate).winningNumbers().isEmpty();
                            } catch (WinningNumbersNotFoundException e) {
                                return false;
                            }
                        }
                );

        //step 3: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 16-11-2022 10:00 and system returned OK(200) with message: “success” and Ticket (DrawDate:19.11.2022 12:00 (Saturday), TicketId: sampleTicketId)
        //given

        //when
        final ResultActions perform = mockMvc.perform(post("/inputNumbers")
                .content("""
                        {
                        "inputNumbers": [1,2,3,4,5,6]
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        final MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        final NumberReceiverResponseDto responseDto = objectMapper.readValue(response, NumberReceiverResponseDto.class);
        assertAll(
                () -> assertThat(responseDto.ticketDto().drawDate()).isEqualTo(drawDate),
                () -> assertThat(responseDto.ticketDto().numbers()).isEqualTo(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6))),
                () -> assertThat(responseDto.ticketDto().ticketId()).isNotNull(),
                () -> assertThat(responseDto.message()).isEqualTo("success")
        );
        
        //step 4: 3 days and 1 minute passed, and it is 1 minute after the draw date (19.11.2022 12:01)
        //step 5: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it with 6 hits
        //step 6: 3 hours passed, and it is 1 minute after announcement time (19.11.2022 15:01)
        //step 7: user made GET /results/sampleTicketId and system returned 200 (OK)
    }

}
