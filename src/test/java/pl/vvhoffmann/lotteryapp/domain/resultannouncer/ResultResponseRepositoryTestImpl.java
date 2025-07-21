package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class ResultResponseRepositoryTestImpl implements ResultResponseRepository {

    Map<String, ResultResponse> db = new ConcurrentHashMap<>();

    @Override
    public ResultResponse save(final ResultResponse resultResponse) {
        db.put(resultResponse.id(), resultResponse);
        return resultResponse;
    }

    @Override
    public boolean existsById(final String id) {
        return db.containsKey(id);
    }

    @Override
    public Optional<ResultResponse> findById(final String id) {
        return Optional.ofNullable(db.get(id));
    }
}
