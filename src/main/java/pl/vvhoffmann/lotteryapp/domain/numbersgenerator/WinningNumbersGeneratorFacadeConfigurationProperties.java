package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix= "lottery.number-generator.facade")
public record WinningNumbersGeneratorFacadeConfigurationProperties(int count, int lowerLimit, int upperLimit) {
}
