package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

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

class ResultResponseRepositoryTestImpl implements ResultResponseRepository {

    Map<String, ResultResponse> db = new ConcurrentHashMap<>();

    @Override
    public boolean existsById(final String id) {
        return db.containsKey(id);
    }

    @Override
    public Optional<ResultResponse> findById(final String id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public <S extends ResultResponse> S insert(final S entity) {
        return null;
    }

    @Override
    public <S extends ResultResponse> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends ResultResponse> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ResultResponse> List<S> findAll(final Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends ResultResponse> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }

    @Override
    public <S extends ResultResponse> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ResultResponse> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ResultResponse> boolean exists(final Example<S> example) {
        return false;
    }

    @Override
    public <S extends ResultResponse, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ResultResponse> S save(final S entity) {
        db.put(entity.id(), entity);
        return entity;
    }

    @Override
    public <S extends ResultResponse> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<ResultResponse> findAll() {
        return List.of();
    }

    @Override
    public List<ResultResponse> findAllById(final Iterable<String> strings) {
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
    public void delete(final ResultResponse entity) {

    }

    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(final Iterable<? extends ResultResponse> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ResultResponse> findAll(final Sort sort) {
        return List.of();
    }

    @Override
    public Page<ResultResponse> findAll(final Pageable pageable) {
        return null;
    }
}
