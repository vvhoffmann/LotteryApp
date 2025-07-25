package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ResultResponseRepository extends MongoRepository<ResultResponse, String> {

//    ResultResponse save(ResultResponse resultResponse);

//    boolean existsById(String id);

//    Optional<ResultResponse> findById(String id);
}