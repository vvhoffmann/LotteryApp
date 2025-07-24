package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

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
    TicketRepository ticketRepository() {
        return new TicketRepository() {
            @Override
            public List<Ticket> findAllTicketsByDrawDate(LocalDateTime drawDate) {
                return null;
            }

            @Override
            public Ticket findByHash(String hash) {
                return null;
            }

            @Override
            public Ticket save(Ticket savedTicket) {
                return null;
            }
        };
    }

    @Bean
    NumbersReceiverFacade numbersReceiverFacade(TicketRepository ticketRepository, HashGenerable hashGenerator, Clock clock) {
        NumberValidator numberValidator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        return new NumbersReceiverFacade(ticketRepository, drawDateGenerator, hashGenerator, numberValidator);
    }
}