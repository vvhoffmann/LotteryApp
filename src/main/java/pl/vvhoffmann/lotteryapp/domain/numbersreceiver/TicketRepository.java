package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

//    Ticket save(Ticket ticket);

    List<Ticket> findAllByDrawDate(LocalDateTime drawDate);

    Optional<Ticket> findById(String id);
}
