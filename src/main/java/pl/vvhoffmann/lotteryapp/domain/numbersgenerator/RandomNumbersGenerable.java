package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.SixRandomNumbersDto;

public interface RandomNumbersGenerable {

    SixRandomNumbersDto generateSixRandomNumbers(int count, int lowerLimit, int upperLimit);
}