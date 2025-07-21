package pl.vvhoffmann.lotteryapp.domain.numbergenerator;

import java.util.Set;

class NumbersGeneratorTestImpl implements RandomNumbersGenerable{

    private final Set<Integer> generatedNumbers;

    NumbersGeneratorTestImpl() {
        this.generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }

    NumbersGeneratorTestImpl(final Set<Integer> generatedNumbers) {
        this.generatedNumbers = generatedNumbers;
    }

    @Override
    public Set<Integer> generateSixRandomNumbers() {
        return Set.of();
    }
}