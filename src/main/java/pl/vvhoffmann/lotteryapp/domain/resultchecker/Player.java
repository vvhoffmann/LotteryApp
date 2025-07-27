package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Document
record Player(
        @Id
        String id,

        LocalDateTime drawDate,
        Set<Integer> numbers,
        Set<Integer> hitNumbers,
        boolean isWinner) {
}