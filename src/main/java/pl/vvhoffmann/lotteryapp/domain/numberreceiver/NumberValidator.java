package pl.vvhoffmann.lotteryapp.domain.numberreceiver;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
class NumberValidator {

    private static final int MAX_NUMBERS_FROM_USER = 6;
    private static final int MAXIMAL_NUMBER_VALUE_FROM_USER = 99;
    private static final int MINIMAL_NUMBER_VALUE_FROM_USER = 1;

    boolean areAllNumbersInRange(final Set<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number >= MINIMAL_NUMBER_VALUE_FROM_USER)
                .filter(number -> number <= MAXIMAL_NUMBER_VALUE_FROM_USER)
                .count() == MAX_NUMBERS_FROM_USER;
    }
}
