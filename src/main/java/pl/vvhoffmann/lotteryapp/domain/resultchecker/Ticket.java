package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Document
record Ticket(
        String id,
        Set<Integer> numbers,
        LocalDateTime drawDate
) {
}