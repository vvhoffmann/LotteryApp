package pl.vvhoffmann.lotteryapp.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.vvhoffmann.lotteryapp.domain.numberreceiver.dto.InputNumberResultDto;

import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final NumberValidator numberValidator;

    public InputNumberResultDto inputNumbers(Set<Integer> userNumbers)
    {
        return InputNumberResultDto.builder()
                .message(
                numberValidator.areAllNumbersInRange(userNumbers) ? "success" : "failed"
                )
                .build();
    }

}