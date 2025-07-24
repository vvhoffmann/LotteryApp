package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;

@Configuration
public class WinningNumbersGeneratorConfiguration {

    @Bean
    public WinningNumbersGeneratorFacade winningNumbersGeneratorFacade(RandomNumbersGenerable randomNumberGenerator,
                                                                       WinningNumbersRepository winningNumbersRepository,
                                                                       NumbersReceiverFacade numbersReceiver,
                                                                       WinningNumbersGeneratorFacadeConfigurationProperties properties) {
        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new WinningNumbersGeneratorFacade(randomNumberGenerator, winningNumbersRepository, winningNumbersValidator, numbersReceiver, properties);
    }

    public WinningNumbersGeneratorFacade setUpForTest(RandomNumbersGenerable randomNumbersGenerator,
                                                      WinningNumbersRepository winningNumbersRepository,
                                                      NumbersReceiverFacade numbersReceiverFacade) {

        WinningNumbersGeneratorFacadeConfigurationProperties properties =
                new WinningNumbersGeneratorFacadeConfigurationProperties(25, 1, 99);

        return winningNumbersGeneratorFacade(randomNumbersGenerator, winningNumbersRepository, numbersReceiverFacade, properties);
    }
}