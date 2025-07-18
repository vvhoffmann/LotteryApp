package  pl.vvhoffmann.lotteryapp.domain.numberreceiver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {
    @Test
    @DisplayName("Should return success when user gave 6 numbers")
    public void should_return_success_when_user_gave_six_numbers() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();
        //when
        final String result = numberReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        //then
        assertThat(result).isEqualTo("success");
    }

    @Test
    @DisplayName("Should return failed when user gave less than 6 numbers")
    public void should_return_failed_when_user_gave_less_than_six_numbers() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();
        //when
        final String result = numberReceiverFacade.inputNumbers(Set.of(2, 3, 4, 5, 6));
        //then
        assertThat(result).isEqualTo("failed");
    }

    @Test
    @DisplayName("Should return failed when user gave more than 6 numbers")
    public void should_return_failed_when_user_gave_more_than_six_numbers() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();
        //when
        final String result = numberReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6, 7));
        //then
        assertThat(result).isEqualTo("failed");
    }

    @Test
    @DisplayName("Should return failed when user gave at least one number out of range 1-99")
    public void should_return_failed_when_user_gave_at_least_one_number_out_of_range_1_to_99() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();
        //when
        final String result = numberReceiverFacade.inputNumbers(Set.of(1, 2, 30, 4, 500, 6));
        //
        assertThat(result).isEqualTo("failed");
    }

}
