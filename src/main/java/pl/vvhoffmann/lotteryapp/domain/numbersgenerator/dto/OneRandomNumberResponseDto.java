package pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto;

import lombok.Builder;

@Builder
public record OneRandomNumberResponseDto(int number) {

}