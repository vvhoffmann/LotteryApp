package pl.vvhoffmann.lotteryapp.domain.numberreceiver;

import pl.vvhoffmann.lotteryapp.domain.numberreceiver.dto.TicketDto;

public class TicketMapper {

    public static TicketDto mapTicketToTicketDto(Ticket ticket) {
        return TicketDto.builder()
                .drawDate(ticket.drawDate())
                .ticketId(ticket.id())
                .numbers(ticket.numbers())
                .build();
    }

    public static Ticket mapTicketDtotoTicket(TicketDto ticketDto) {
        return Ticket.builder()
                .id(ticketDto.ticketId())
                .drawDate(ticketDto.drawDate())
                .numbers(ticketDto.numbers())
                .build();
    }
}
