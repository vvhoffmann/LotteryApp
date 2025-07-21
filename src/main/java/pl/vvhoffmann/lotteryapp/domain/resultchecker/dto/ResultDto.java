package pl.vvhoffmann.lotteryapp.domain.resultchecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ResultDto(String id,
                        Set<Integer> numbers,
                        LocalDateTime drawDate,
                        boolean isWinner) {
}