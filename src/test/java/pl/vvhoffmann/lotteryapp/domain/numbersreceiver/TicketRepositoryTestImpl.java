package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class TicketRepositoryTestImpl implements TicketRepository {

    Map<String, Ticket> db = new ConcurrentHashMap<>();

    @Override
    public Ticket save(final Ticket ticket) {
        db.put(ticket.id(), ticket);
        return ticket;
    }

    @Override
    public List<Ticket> findAllTicketsByDrawDate(final LocalDateTime drawDate) {
        return db.values().stream()
                .filter(ticket -> ticket.drawDate().equals(drawDate))
                .toList();
    }

    @Override
    public Ticket findByHash(final String hash) {
        return db.get(hash);
    }
}