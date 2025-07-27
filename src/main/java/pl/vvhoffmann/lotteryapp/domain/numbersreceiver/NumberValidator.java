package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class NumberValidator {

    private static final int QUANTITY_OF_NUMBERS_FROM_USER = 6;
    private static final int MAXIMAL_NUMBER_VALUE_FROM_USER = 99;
    private static final int MINIMAL_NUMBER_VALUE_FROM_USER = 1;

    List<ValidationResult> errors;

    List<ValidationResult> validate(final Set<Integer> userNumbers) {
        errors = new LinkedList<>();
        if (!isNumbersSizeEqualSix(userNumbers))
            errors.add(ValidationResult.NOT_SIX_NUMBERS_GIVEN);
        if (!areNumbersInRange(userNumbers))
            errors.add(ValidationResult.NOT_IN_RANGE);
        return errors;
    }

    private boolean isNumbersSizeEqualSix(Set<Integer> numbersFromUser) {
        return numbersFromUser.size() == QUANTITY_OF_NUMBERS_FROM_USER;
    }

    boolean areNumbersInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .allMatch(number -> number >= MINIMAL_NUMBER_VALUE_FROM_USER && number <= MAXIMAL_NUMBER_VALUE_FROM_USER);
    }

    String createResultMessage() {
        return this.errors
                .stream()
                .map(validationResult -> validationResult.message)
                .collect(Collectors.joining(", "));

    }
}
