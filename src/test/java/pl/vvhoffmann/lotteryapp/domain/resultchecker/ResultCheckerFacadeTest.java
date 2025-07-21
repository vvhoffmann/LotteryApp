package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.WinningNumbersDto;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.TicketDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.PlayersDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.ResultDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultCheckerFacadeTest {

    PlayersRepository playersRepository = new PlayersRepositoryTestImpl();
    NumbersGeneratorFacade numbersGeneratorFacade = mock(NumbersGeneratorFacade.class);
    NumbersReceiverFacade numbersReceiverFacade = mock(NumbersReceiverFacade.class);

    @Test
    @DisplayName("Should save all players with correct message")
    public void should_save_all_players() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 10, 12, 12,0,0);
        when(numbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of(1,2,3,4,5,6))
                .build());
        when(numbersReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .ticketId("Ticket1")
                                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .ticketId("Ticket2")
                                .numbers(Set.of(1, 2, 3, 7, 8, 9))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .ticketId("Ticket3")
                                .numbers(Set.of(4, 5, 6, 7, 8, 9))
                                .drawDate(drawDate)
                                .build())
        );
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().setUpForTest(
                                                                                    numbersGeneratorFacade,
                                                                                    numbersReceiverFacade,
                                                                                    playersRepository);
        //when
        PlayersDto winnersDto = resultCheckerFacade.generateWinners();
        //then
        List<ResultDto> resultDtos = winnersDto.results();
        ResultDto expectedDto1 = ResultDto.builder()
                .id("Ticket1")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1,2,3,4,5,6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultDto expectedResult2 = ResultDto.builder()
                .id("Ticket2")
                .numbers(Set.of(1, 2, 3, 7, 8, 9))
                .hitNumbers(Set.of(1,2,3))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultDto expectedResult3 = ResultDto.builder()
                .id("Ticket3")
                .numbers(Set.of( 4, 5, 6, 7, 8, 9))
                .hitNumbers(Set.of(4,5,6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        assertThat(resultDtos).contains(expectedDto1, expectedResult2, expectedResult3);
        String message = winnersDto.message();
        assertThat(message).isEqualTo("Winners succeed to retrieve");
    }

    @Test
    @DisplayName("Should generate correct Result record with correct data")
    public void should_generate_result_with_correct_data_filled()
    {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 10, 12, 12,0,0);
        when(numbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of(1,2,3,4,5,6))
                .build());
        String id = "Ticket1";
        when(numbersReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .ticketId(id)
                                .numbers(Set.of(7, 8, 9, 10, 11, 12))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .ticketId("not-correct")
                                .numbers(Set.of(7, 8, 9, 10, 11, 13))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .ticketId("not-correct-2")
                                .numbers(Set.of(7, 8, 9, 10, 11, 14))
                                .drawDate(drawDate)
                                .build())
        );
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().setUpForTest(
                numbersGeneratorFacade,
                numbersReceiverFacade,
                playersRepository);
        resultCheckerFacade.generateWinners();
        //when
        ResultDto resultDto = resultCheckerFacade.findById("Ticket1");
        //then
        ResultDto expectedResult = ResultDto.builder()
                .id("Ticket1")
                .numbers(Set.of(7, 8, 9, 10, 11, 12))
                .hitNumbers(null)
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        assertThat(resultDto).isEqualTo(expectedResult);
    }

    @Test
    public void it_should_generate_fail_message_when_winningNumbers_is_empty() {
        //given
        when(numbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of())
                .build());
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().setUpForTest(
                                                    numbersGeneratorFacade,
                                                    numbersReceiverFacade,
                                                    playersRepository);
        //when
        PlayersDto playersDto = resultCheckerFacade.generateWinners();
        //then
        String message = playersDto.message();
        assertThat(message).isEqualTo("Winning numbers failed to retrieve");

    }
}
