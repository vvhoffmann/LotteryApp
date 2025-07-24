package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;

class ResultCheckerConfiguration {

    ResultCheckerFacade setUpForTest(WinningNumbersGeneratorFacade winningNumbersGeneratorFacade,
                                     NumbersReceiverFacade numbersReceiverFacade,
                                     PlayersRepository playersRepository) {
        WinnersGenerator winnersGenerator = new WinnersGenerator();
        return new ResultCheckerFacade(winningNumbersGeneratorFacade, numbersReceiverFacade, playersRepository, winnersGenerator);
    }
}