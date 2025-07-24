package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record ResultResponse(
        String id,
        LocalDateTime drawDate,
        Set<Integer> numbers,
        Set<Integer> hitNumbers,
        boolean isWinner
) {
}
