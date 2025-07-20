package pl.vvhoffmann.lotteryapp.domain.numberreceiver;

import java.time.Clock;

public class NumberReceiverConfiguration {

    public NumberReceiverFacade setUpForTest(TicketRepository ticketRepository, Clock clock, HashGenerable hashGenerator) {
        NumberValidator numberValidator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        return new NumberReceiverFacade(ticketRepository, drawDateGenerator, hashGenerator, numberValidator);
    }
}