package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.SixRandomNumbersDto;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class SecureRandomNumbersGeneratorTestImpl implements RandomNumbersGenerable {

    private final int LOWER_LIMIT = 1;
    private final int UPPER_LIMIT = 99;
    private final int WINNING_NUMBERS_QUANTITY = 6;

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {

        Set<Integer> winningNumbers = new HashSet<>();
        while (isQuantityOfNumbersLowerThanSix(winningNumbers)){
            Random random = new SecureRandom();
            int randomNumber = random.nextInt((UPPER_LIMIT - LOWER_LIMIT) + 1);
            if(!isNewNumberRepetitive(winningNumbers, randomNumber))
                winningNumbers.add(randomNumber);
        }
        return SixRandomNumbersDto.builder()
                .numbers(winningNumbers)
                .build();
    }

    private boolean isQuantityOfNumbersLowerThanSix(final Set<Integer> winningNumbers) {
        return winningNumbers.size() < WINNING_NUMBERS_QUANTITY;
    }

    private static boolean isNewNumberRepetitive(final Set<Integer> winningNumbers, final int randomNumber) {
        return winningNumbers.contains(randomNumber);
    }
}