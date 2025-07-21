package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import java.util.Optional;

interface ResponseRepository {

    ResultResponse save(ResultResponse resultResponse);

    boolean existsById(String id);

    Optional<ResultResponse> findById(String id);
}