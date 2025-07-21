package pl.vvhoffmann.lotteryapp.domain.resultchecker;

import lombok.AllArgsConstructor;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.NumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.WinningNumbersDto;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.TicketDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.PlayerDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.ResultDto;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ResultCheckerFacade {

    NumbersGeneratorFacade numbersGeneratorFacade;
    NumbersReceiverFacade numbersReceiverFacade;
    PlayerRepository playerRepository;
    WinnersGenerator winnersGenerator;

    public PlayerDto generateWinner() {
        List<TicketDto> allTicketsByDate = numbersReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = ResultCheckerMapper.mapFromTicketDtoToTicket(allTicketsByDate);
        WinningNumbersDto winningNumbersDto = numbersGeneratorFacade.generateWinningNumbers();
        final Set<Integer> winningNumbers = winningNumbersDto.winningNumbers();
        if (winningNumbers.isEmpty()) {
            return PlayerDto.builder()
                    .message("Winning numbers failed to retrieve")
                    .build();
        }

        final List<Player> players = winnersGenerator.retrieveWinners(tickets, winningNumbers);
        playerRepository.saveAll(players);
        return PlayerDto.builder()
                .results(ResultCheckerMapper.mapPlayersToResults(players))
                .message("Winners succeed to retrieve")
                .build();

    }

    public ResultDto findById(String id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        return ResultDto.builder()
                .id(player.id())
                .numbers(player.numbers())
                .drawDate(player.drawDate())
                .isWinner(player.isWinner())
                .build();
    }
}
