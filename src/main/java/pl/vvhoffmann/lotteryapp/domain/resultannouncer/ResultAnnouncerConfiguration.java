package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

@Configuration
class ResultAnnouncerConfiguration {

    @Bean
    public ResultAnnouncerFacade resultAnnouncerFacade(ResultResponseRepository resultResponseRepository, ResultCheckerFacade resultCheckerFacade, Clock clock) {
        return new ResultAnnouncerFacade(resultCheckerFacade, resultResponseRepository, clock);
    }
}
