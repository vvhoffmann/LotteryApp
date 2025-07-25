package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class NumberReceiverConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    HashGenerable hashGenerable() {
        return new HashGenerator();
    }

    @Bean
    NumbersReceiverFacade numbersReceiverFacade(TicketRepository ticketRepository, HashGenerable hashGenerator, Clock clock) {
        NumberValidator numberValidator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        return new NumbersReceiverFacade(ticketRepository, drawDateGenerator, hashGenerator, numberValidator);
    }
}