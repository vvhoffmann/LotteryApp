package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import java.util.List;
import java.util.Optional;

public interface PlayersRepository {

    List<Player> saveAll(List<Player> players);

    Optional<Player> findById(String id);
}