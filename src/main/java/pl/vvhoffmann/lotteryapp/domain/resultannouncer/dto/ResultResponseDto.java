package pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ResultResponseDto(
        String id,
        LocalDateTime drawDate,
        Set<Integer> numbers,
        Set<Integer> hitNumbers,
        boolean isWinner
) {
}