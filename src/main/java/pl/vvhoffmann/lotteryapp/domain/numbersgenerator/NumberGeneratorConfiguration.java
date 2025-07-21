package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;

class NumberGeneratorConfiguration {

    NumbersGeneratorFacade setUpForTest(RandomNumbersGenerable randomNumbersGenerator,
                                        WinningNumbersRepository winningNumbersRepository,
                                        NumbersReceiverFacade numbersReceiverFacade) {

        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new NumbersGeneratorFacade(randomNumbersGenerator, winningNumbersRepository, winningNumbersValidator, numbersReceiverFacade);
    }
}