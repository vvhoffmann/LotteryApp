package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;

class ResultCheckerConfiguration {

    ResultCheckerFacade setUpForTest(NumbersGeneratorFacade numbersGeneratorFacade,
                                     NumbersReceiverFacade numbersReceiverFacade,
                                     PlayersRepository playersRepository)
    {
        WinnersGenerator winnersGenerator = new WinnersGenerator();
        return new ResultCheckerFacade(numbersGeneratorFacade, numbersReceiverFacade, playersRepository, winnersGenerator);
    }
}