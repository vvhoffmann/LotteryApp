package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.SixRandomNumbersDto;

public interface RandomNumbersGenerable {

    int LOWER_LIMIT = 1;
    int UPPER_LIMIT = 99;
    int RANDOM_BUMBERS_QUANTITY = 25;
    int WINNING_NUMBERS_QUANTITY = 6;

    SixRandomNumbersDto generateSixRandomNumbers();
}
