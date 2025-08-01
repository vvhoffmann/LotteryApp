package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.SixRandomNumbersDto;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.WinningNumbersDto;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class WinningNumbersGeneratorFacade {

    private static final Logger log = LogManager.getLogger(WinningNumbersGeneratorFacade.class);
    private final RandomNumbersGenerable randomNumbersGenerator;
    private final WinningNumbersRepository winningNumbersRepository;
    private final WinningNumbersValidator winningNumbersValidator;
    private final NumbersReceiverFacade numbersReceiverFacade;
    private final WinningNumbersGeneratorFacadeConfigurationProperties properties;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = numbersReceiverFacade.retrieveNextDrawDate();
        SixRandomNumbersDto winningNumbersDto = randomNumbersGenerator.generateSixRandomNumbers(properties.count(), properties.lowerLimit(), properties.upperLimit());
        final Set<Integer> validatedNumbers = winningNumbersValidator.validate(winningNumbersDto.numbers());
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
        log.info("Retrieved winning numbers {} from date {} ", numbersByDate, drawDate);
        return WinningNumbersDto.builder()
                .winningNumbers(numbersByDate.numbers())
                .drawDate(numbersByDate.drawDate())
                .build();

    }

    public boolean areNextDrawDateWinningNumbersGenerated() {
        LocalDateTime nextDrawDate = numbersReceiverFacade.retrieveNextDrawDate();
        return winningNumbersRepository.existsByDrawDate(nextDrawDate);
    }
}
