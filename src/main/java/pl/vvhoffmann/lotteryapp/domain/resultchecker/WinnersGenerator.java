package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class WinnersGenerator {

    private final int MINIMAL_NUMBERS_WHEN_PLAYER_WON = 3;


    List<Player> retrieveWinners(final List<Ticket> tickets, final Set<Integer> winningNumbers) {
        return tickets.stream()
                .map(ticket -> {
                    Set<Integer> hitNumbers = calculateHits(winningNumbers, ticket);
                    return buildPlayer(ticket, hitNumbers);
                })
                .collect(Collectors.toList());
    }

    private Player buildPlayer(final Ticket ticket, final Set<Integer> hitNumbers) {
        return Player.builder()
                .isWinner(isWinner(hitNumbers))
                .id(ticket.id())
                .numbers(ticket.numbers())
                .hitNumbers(hitNumbers)
                .drawDate(ticket.drawDate())
                .build();
    }

    private Set<Integer> calculateHits(Set<Integer> winningNumbers, Ticket ticket) {
        return ticket.numbers().stream()
                .filter(winningNumbers::contains)
                .collect(Collectors.toSet());
    }

    private boolean isWinner(Set<Integer> hitNumbers) {
        return hitNumbers.size() >= MINIMAL_NUMBERS_WHEN_PLAYER_WON;
    }
}
