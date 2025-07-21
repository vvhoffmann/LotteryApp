package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class PlayersRepositoryTestImpl implements PlayersRepository {

    private final Map<String, Player> db = new ConcurrentHashMap<>();

    @Override
    public List<Player> saveAll(final List<Player> players) {
        players.forEach(player -> db.put(player.id(), player));
        return players;
    }

    @Override
    public Optional<Player> findById(final String id) {
        return Optional.ofNullable(db.get(id));
    }
}
