package pl.vvhoffmann.lotteryapp.infrastructure.resultchecker.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersGeneratorFacade;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.ResultCheckerFacade;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.PlayersDto;
import pl.vvhoffmann.lotteryapp.infrastructure.resultchecker.scheduler.error.WinningNumbersNotGeneratedException;

@AllArgsConstructor
@Component
@Log4j2
class ResultCheckerScheduler {

    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    private final ResultCheckerFacade resultCheckerFacade;

    @Scheduled(cron = "${lottery.result-checker.lotteryRunOccurrence}")
    public PlayersDto checkResults(){
        log.info("ResultChecker scheduler started");
        if(!winningNumbersGeneratorFacade.areNextDrawDateWinningNumbersGenerated())
        {
            log.error("Winning numbers are not generated");
            throw new WinningNumbersNotGeneratedException();
        }
        log.info("Winning numbers have been fetched");
        return resultCheckerFacade.generateResults();
    }
}