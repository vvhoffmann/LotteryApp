package pl.vvhoffmann.lotteryapp.infrastructure.numbersreceiver.controller.dto;

import java.util.List;

public record InputNumbersRequestDto (

        List<Integer> inputNumbers
) {
}
