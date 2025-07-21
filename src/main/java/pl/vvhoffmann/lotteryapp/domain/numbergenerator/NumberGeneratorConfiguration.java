package pl.vvhoffmann.lotteryapp.domain.numbergenerator;

import pl.vvhoffmann.lotteryapp.domain.numberreceiver.NumberReceiverFacade;

class NumberGeneratorConfiguration {

    NumberGeneratorFacade setUpForTest(RandomNumbersGenerable randomNumbersGenerator,
                                       WinningNumbersRepository winningNumbersRepository,
                                       NumberReceiverFacade numberReceiverFacade) {

        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new NumberGeneratorFacade(randomNumbersGenerator, winningNumbersRepository, winningNumbersValidator, numberReceiverFacade);
    }
}