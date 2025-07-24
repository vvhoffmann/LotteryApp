package pl.vvhoffmann.lotteryapp.infrastructure.numbersgenerator.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.WinningNumbersDto;

@AllArgsConstructor
@Log4j2
@Component
class WinningNumbersScheduler {

    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;

    @Scheduled(cron = "${lottery.number-generator.lotteryRunOccurrence}")
    public WinningNumbersDto generateWinningNumbers() {
        log.info("Running winning numbers scheduler");
        final WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        log.info("Generated winning numbers {} on date {}", winningNumbersDto.winningNumbers(), winningNumbersDto.drawDate());
        return winningNumbersDto;
    }
}