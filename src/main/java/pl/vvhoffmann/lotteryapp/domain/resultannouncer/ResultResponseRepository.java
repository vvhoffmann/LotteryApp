package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import java.util.Optional;

interface ResultResponseRepository {

    ResultResponse save(ResultResponse resultResponse);

    boolean existsById(String id);

    Optional<ResultResponse> findById(String id);
}