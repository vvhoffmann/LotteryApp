package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;

class WinningNumbersGeneratorConfiguration {

    WinningNumbersGeneratorFacade setUpForTest(RandomNumbersGenerable randomNumbersGenerator,
                                               WinningNumbersRepository winningNumbersRepository,
                                               NumbersReceiverFacade numbersReceiverFacade) {

        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new WinningNumbersGeneratorFacade(randomNumbersGenerator, winningNumbersRepository, winningNumbersValidator, numbersReceiverFacade);
    }
}