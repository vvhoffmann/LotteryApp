package pl.vvhoffmann.lotteryapp.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryNumberReceiverRepositoryTestImpl implements NumberReceiverRepository {

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

}
