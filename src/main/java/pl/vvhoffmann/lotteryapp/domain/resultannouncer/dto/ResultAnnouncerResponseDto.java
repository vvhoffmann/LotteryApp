package pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto;

import lombok.Builder;

@Builder
public record ResultAnnouncerResponseDto(
        ResultResponseDto resultResponseDto,
        String message
) {
}