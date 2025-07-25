package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class PlayersRepositoryTestImpl implements PlayersRepository {

    private final Map<String, Player> db = new ConcurrentHashMap<>();

    @Override
    public Optional<Player> findById(final String id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public boolean existsById(final String s) {
        return false;
    }

    @Override
    public <S extends Player> S insert(final S entity) {
        return null;
    }

    @Override
    public <S extends Player> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends Player> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Player> List<S> findAll(final Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Player> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Player> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Player> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Player> boolean exists(final Example<S> example) {
        return false;
    }

    @Override
    public <S extends Player, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Player> S save(final S entity) {
        return null;
    }

    @Override
    public <S extends Player> List<S> saveAll(final Iterable<S> entities) {
        Stream<S> stream = StreamSupport.stream(entities.spliterator(), false);
        List<S> list = stream.toList();
        list.forEach(player -> db.put(player.id(), player));
        return list;
    }

    @Override
    public List<Player> findAll() {
        return List.of();
    }

    @Override
    public List<Player> findAllById(final Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(final String s) {

    }

    @Override
    public void delete(final Player entity) {

    }

    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(final Iterable<? extends Player> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Player> findAll(final Sort sort) {
        return List.of();
    }

    @Override
    public Page<Player> findAll(final Pageable pageable) {
        return null;
    }
}
