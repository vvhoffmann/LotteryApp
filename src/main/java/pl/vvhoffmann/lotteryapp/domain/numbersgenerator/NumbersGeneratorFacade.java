package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import lombok.AllArgsConstructor;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.WinningNumbersDto;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class NumbersGeneratorFacade {

    RandomNumbersGenerable randomNumbersGenerator;
    WinningNumbersRepository winningNumbersRepository;
    WinningNumbersValidator winningNumbersValidator;
    NumbersReceiverFacade numbersReceiverFacade;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = numbersReceiverFacade.retrieveNextDrawDate();
        Set<Integer> winningNumbers = randomNumbersGenerator.generateSixRandomNumbers();
        final Set<Integer> validatedNumbers = winningNumbersValidator.validate(winningNumbers);
        winningNumbersRepository.save(WinningNumbers.builder()
                .numbers(validatedNumbers)
                .drawDate(nextDrawDate)
                .build()
        );
        return WinningNumbersDto.builder()
                .winningNumbers(validatedNumbers)
                .drawDate(nextDrawDate)
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
        LocalDateTime nextDrawDate = numbersReceiverFacade.retrieveNextDrawDate();
        return winningNumbersRepository.existsByDate(nextDrawDate);
    }
}
