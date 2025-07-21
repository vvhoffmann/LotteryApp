package pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto;

import lombok.Builder;

@Builder
public record NumberReceiverResponseDto(
        TicketDto ticketDto,
        String message) {
}