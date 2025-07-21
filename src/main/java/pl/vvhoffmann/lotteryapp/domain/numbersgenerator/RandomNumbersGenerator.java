package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class RandomNumbersGenerator implements RandomNumbersGenerable {

    private final int LOWER_LIMIT = 1;
    private final int UPPER_LIMIT = 99;
    private final int WINNING_NUMBERS_QUANTITY = 6;

    private final int RANDOM_NUMBER_BOUND = (UPPER_LIMIT - LOWER_LIMIT) + 1;

    @Override
    public Set<Integer> generateSixRandomNumbers() {

        Set<Integer> winningNumbers = new HashSet<>();
        while (isQuantityOfNumbersLowerThanSix(winningNumbers)){
            int randomNumber = generateRandomNumber();
            if(!isNewNumberRepetitive(winningNumbers, randomNumber))
                winningNumbers.add(generateRandomNumber());
        }
        return winningNumbers;
    }

    private boolean isQuantityOfNumbersLowerThanSix(final Set<Integer> winningNumbers) {
        return winningNumbers.size() < WINNING_NUMBERS_QUANTITY;
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(RANDOM_NUMBER_BOUND) + 1;
    }

    private static boolean isNewNumberRepetitive(final Set<Integer> winningNumbers, final int randomNumber) {
        return winningNumbers.contains(randomNumber);
    }
}