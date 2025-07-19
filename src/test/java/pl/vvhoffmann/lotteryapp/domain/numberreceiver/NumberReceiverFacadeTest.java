package pl.vvhoffmann.lotteryapp.domain.numberreceiver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.vvhoffmann.lotteryapp.domain.AdjustableClock;
import pl.vvhoffmann.lotteryapp.domain.numberreceiver.dto.InputNumberResultDto;
import pl.vvhoffmann.lotteryapp.domain.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {

    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2023, 2, 15, 11, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());

    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(
            new NumberValidator(),
            new InMemoryNumberReceiverRepositoryTestImpl(),
            clock
    );

    @Test
    @DisplayName("Should return 'success' when user gave 6 numbers")
    public void should_return_success_when_user_gave_six_numbers() {
        //given
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        //when
        final InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("success");
    }

    @Test
    @DisplayName("Should return 'failed' when user gave less than 6 numbers")
    public void should_return_failed_when_user_gave_less_than_six_numbers() {
        //given
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5);
        //when
        final InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("failed");
    }

    @Test
    @DisplayName("Should return 'failed' when user gave more than 6 numbers")
    public void should_return_failed_when_user_gave_more_than_six_numbers() {
        //given
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        final InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("failed");
    }

    @Test
    @DisplayName("Should return 'failed' when user gave at least one number out of range 1-99")
    public void should_return_failed_when_user_gave_at_least_one_number_out_of_range_1_to_99() {
        //given
        final Set<Integer> numbersFromUser = Set.of(1, 2, 30, 4, 500, 6);
        //when
        final InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //
        assertThat(result.message()).isEqualTo("failed");
    }

    @Test
    @DisplayName("Should return 'saved to database' when user gave 6 numbers")
    public void should_return_saved_to_database_when_user_gave_six_numbers() {
        //given
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        final InputNumberResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //when
        final List<TicketDto> ticketDtos = numberReceiverFacade.getUsersNumbers(result.drawDate());
        //
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .ticketId(result.ticketId())
                        .drawDate(result.drawDate())
                        .numbers(result.userNumbers())
                        .build()
        );
    }

}
