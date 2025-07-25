package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
interface TicketRepository extends MongoRepository<Ticket, Integer> {

//    Ticket save(Ticket ticket);

    List<Ticket> findAllByDrawDate(LocalDateTime drawDate);

    Ticket findById(String id);
}
