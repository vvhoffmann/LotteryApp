package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Document
record ResultResponse(

        @Id
        String id,

        LocalDateTime drawDate,
        Set<Integer> numbers,
        Set<Integer> hitNumbers,
        boolean isWinner
) {
}