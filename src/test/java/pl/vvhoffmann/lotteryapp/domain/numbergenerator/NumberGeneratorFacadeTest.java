package pl.vvhoffmann.lotteryapp.domain.numbergenerator;

import pl.vvhoffmann.lotteryapp.domain.numberreceiver.NumberReceiverFacade;

import static org.mockito.Mockito.mock;

class NumberGeneratorFacadeTest {

    private final WinningNumbersRepository winningNumbersRepository = new NumbersGeneratorRepositoryTestImpl();
    private final NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);




}
