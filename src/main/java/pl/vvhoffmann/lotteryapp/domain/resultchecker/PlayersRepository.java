package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayersRepository extends MongoRepository<Player, String> {

    Optional<Player> findById(String id);
}