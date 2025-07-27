package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;

@Configuration
class ResultCheckerConfiguration {

    @Bean
    ResultCheckerFacade resultCheckerFacade(WinningNumbersGeneratorFacade winningNumbersGeneratorFacade,
                                            NumbersReceiverFacade numbersReceiverFacade,
                                            PlayersRepository playersRepository) {
        WinnersGenerator winnersGenerator = new WinnersGenerator();
        return new ResultCheckerFacade(winningNumbersGeneratorFacade, numbersReceiverFacade, playersRepository, winnersGenerator);
    }
}