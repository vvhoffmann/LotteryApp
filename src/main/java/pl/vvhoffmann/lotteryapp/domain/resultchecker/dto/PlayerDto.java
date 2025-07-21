package pl.vvhoffmann.lotteryapp.domain.resultchecker.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PlayerDto(List<ResultDto> results,
                        String message) {
}