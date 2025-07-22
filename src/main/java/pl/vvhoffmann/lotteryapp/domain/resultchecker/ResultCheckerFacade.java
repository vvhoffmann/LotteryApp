package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import lombok.AllArgsConstructor;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.WinningNumbersDto;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.TicketDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.PlayersDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.ResultDto;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ResultCheckerFacade {

    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    NumbersReceiverFacade numbersReceiverFacade;
    PlayersRepository playersRepository;
    WinnersGenerator winnersGenerator;

    public PlayersDto generateWinners() {
        List<TicketDto> allTicketsByDate = numbersReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = ResultCheckerMapper.mapFromTicketDtoToTicket(allTicketsByDate);
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        final Set<Integer> winningNumbers = winningNumbersDto.winningNumbers();
        if (winningNumbers.isEmpty()) {
            return PlayersDto.builder()
                    .message("Winning numbers failed to retrieve")
                    .build();
        }

        final List<Player> players = winnersGenerator.retrieveWinners(tickets, winningNumbers);
        playersRepository.saveAll(players);
        return PlayersDto.builder()
                .results(ResultCheckerMapper.mapPlayersToResults(players))
                .message("Winners succeed to retrieve")
                .build();

    }

    public ResultDto findById(String id) {
        Player player = playersRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        return ResultDto.builder()
                .id(player.id())
                .numbers(player.numbers())
                .drawDate(player.drawDate())
                .isWinner(player.isWinner())
                .build();
    }
}
