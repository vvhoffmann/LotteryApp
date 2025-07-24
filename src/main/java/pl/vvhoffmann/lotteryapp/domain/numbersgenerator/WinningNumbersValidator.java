package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import java.util.Set;

class WinningNumbersValidator {

    private final int LOWER_LIMIT = 1;
    private final int UPPER_LIMIT = 99;
    private final int WINNING_NUMBERS_QUANTITY = 6;

    public Set<Integer> validate(final Set<Integer> winningNumbers) {
        if (anyOfNumbersOutOfRange(winningNumbers))
            throw new WinningNumberValidationException("Number is out of range");
        if (!isCorrectQuantityOfNumbers(winningNumbers))
            throw new WinningNumberValidationException("There are more/less than " + WINNING_NUMBERS_QUANTITY + " numbers");
        return winningNumbers;
    }

    private boolean anyOfNumbersOutOfRange(final Set<Integer> winningNumbers) {
        return winningNumbers.stream()
                .anyMatch(number -> number < LOWER_LIMIT || number > UPPER_LIMIT);
    }

    private boolean isCorrectQuantityOfNumbers(final Set<Integer> winningNumbers) {
        return winningNumbers.size() == WINNING_NUMBERS_QUANTITY;
    }
}
