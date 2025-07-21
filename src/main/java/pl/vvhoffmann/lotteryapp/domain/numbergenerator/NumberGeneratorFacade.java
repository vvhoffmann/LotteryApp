package pl.vvhoffmann.lotteryapp.domain.numbergenerator;

import lombok.AllArgsConstructor;
import pl.vvhoffmann.lotteryapp.domain.numbergenerator.dto.WinningNumbersDto;
import pl.vvhoffmann.lotteryapp.domain.numberreceiver.NumberReceiverFacade;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class NumberGeneratorFacade {

    RandomNumbersGenerable randomNumbersGenerator;
    WinningNumbersRepository winningNumbersRepository;
    WinningNumbersValidator winningNumbersValidator;
    NumberReceiverFacade numberReceiverFacade;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = numberReceiverFacade.retrieveNextDrawDate();
        Set<Integer> winningNumbers = randomNumbersGenerator.generateSixRandomNumbers();
        final Set<Integer> validatedNumbers = winningNumbersValidator.validate(winningNumbers);
        final WinningNumbers savedWinningNumbers = winningNumbersRepository.save(WinningNumbers.builder()
                .numbers(validatedNumbers)
                .drawDate(nextDrawDate)
                .build()
        );
        return WinningNumbersDto.builder()
                .winningNumbers(savedWinningNumbers.numbers())
                .drawDate(savedWinningNumbers.drawDate())
                .build();
    }

    public WinningNumbersDto retrieveWinningNumbersByDrawDate(LocalDateTime drawDate) {
        WinningNumbers numbersByDate = winningNumbersRepository.findNumbersByDrawDate(drawDate)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Winning numbers on " + drawDate + " not found"));
        return WinningNumbersDto.builder()
                .winningNumbers(numbersByDate.numbers())
                .drawDate(numbersByDate.drawDate())
                .build();

    }

    public boolean areNextDrawDateWinningNumbersGenerated() {
        LocalDateTime nextDrawDate = numberReceiverFacade.retrieveNextDrawDate();
        return winningNumbersRepository.existsByDate(nextDrawDate);
    }
}
