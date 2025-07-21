package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import java.time.LocalDateTime;
import java.util.List;

interface TicketRepository {

    Ticket save(Ticket ticket);

    List<Ticket> findAllTicketsByDrawDate(LocalDateTime drawDate);

    Ticket findByHash(String hash);
}
