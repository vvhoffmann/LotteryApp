package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Document
record Ticket(
        @Id
        String id,

        LocalDateTime drawDate,

        Set<Integer> numbers
) {
}