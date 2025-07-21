package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.TicketDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.ResultDto;

import java.util.List;
import java.util.stream.Collectors;

class ResultCheckerMapper {

    static List<Ticket> mapFromTicketDtoToTicket(List<TicketDto> ticketDtoList) {
        return ticketDtoList.stream()
                .map(ticketDto -> Ticket.builder()
                        .id(ticketDto.ticketId())
                        .numbers(ticketDto.numbers())
                        .drawDate(ticketDto.drawDate())
                        .build()
                ).collect(Collectors.toList());
    }

    static List<ResultDto> mapPlayersToResults(final List<Player> players) {
        return players.stream()
                .map(player -> ResultDto.builder()
                        .id(player.id())
                        .numbers(player.numbers())
                        .drawDate(player.drawDate())
                        .isWinner(player.isWinner())
                        .build())
                .collect(Collectors.toList());
    }
}
