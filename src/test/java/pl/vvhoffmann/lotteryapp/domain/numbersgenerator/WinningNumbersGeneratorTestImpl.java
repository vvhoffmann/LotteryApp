package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.SixRandomNumbersDto;

import java.util.Set;

class WinningNumbersGeneratorTestImpl implements RandomNumbersGenerable{

    private final Set<Integer> generatedNumbers;

    WinningNumbersGeneratorTestImpl() {
        this.generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }

    WinningNumbersGeneratorTestImpl(final Set<Integer> generatedNumbers) {
        this.generatedNumbers = generatedNumbers;
    }

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        return SixRandomNumbersDto.builder()
                .numbers(generatedNumbers)
                .build();
    }
}