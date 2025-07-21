package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Ticket(String id, LocalDateTime drawDate, Set<Integer> numbers) {
}
