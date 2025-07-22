package pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record SixRandomNumbersDto(Set<Integer> numbers) {
}