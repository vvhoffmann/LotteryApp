package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Player(String id,
              LocalDateTime drawDate,
              Set<Integer> numbers,
              boolean isWinner) {
}