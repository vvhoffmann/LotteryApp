package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.OneRandomNumberResponseDto;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

class RandomNumbersGenerator implements RandomNumbersGenerable {

    private final int LOWER_LIMIT = 1;
    private final int UPPER_LIMIT = 99;
    private final int WINNING_NUMBERS_QUANTITY = 6;

    private final OneRandomNumberFetcher client;

    RandomNumbersGenerator(OneRandomNumberFetcher client) {
        this.client = client;
    }

    @Override
    public Set<Integer> generateSixRandomNumbers() {

        Set<Integer> winningNumbers = new HashSet<>();
        while (isQuantityOfNumbersLowerThanSix(winningNumbers)){
            OneRandomNumberResponseDto oneRandomNumberResponseDto = client.retrieveOneRandomNumber(LOWER_LIMIT, UPPER_LIMIT);
            int randomNumber = oneRandomNumberResponseDto.number();
            if(!isNewNumberRepetitive(winningNumbers, randomNumber))
                winningNumbers.add(randomNumber);
        }
        return winningNumbers.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private boolean isQuantityOfNumbersLowerThanSix(final Set<Integer> winningNumbers) {
        return winningNumbers.size() < WINNING_NUMBERS_QUANTITY;
    }

    private static boolean isNewNumberRepetitive(final Set<Integer> winningNumbers, final int randomNumber) {
        return winningNumbers.contains(randomNumber);
    }
}