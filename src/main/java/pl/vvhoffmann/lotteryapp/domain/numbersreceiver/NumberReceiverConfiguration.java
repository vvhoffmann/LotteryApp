package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import java.time.Clock;

public class NumberReceiverConfiguration {

    public NumbersReceiverFacade setUpForTest(TicketRepository ticketRepository, Clock clock, HashGenerable hashGenerator) {
        NumberValidator numberValidator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        return new NumbersReceiverFacade(ticketRepository, drawDateGenerator, hashGenerator, numberValidator);
    }
}