package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.OneRandomNumberResponseDto;

import java.security.SecureRandom;
import java.util.Random;

class SecureRandomOneNumberFetcher implements OneRandomNumberFetcher {

    @Override
    public OneRandomNumberResponseDto retrieveOneRandomNumber(final int lowerLimit, final int upperLimit) {
        Random random = new SecureRandom();
        return OneRandomNumberResponseDto.builder()
                .number(random.nextInt(upperLimit - lowerLimit) + 1)
                .build();

    }
}
