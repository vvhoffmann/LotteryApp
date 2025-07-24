package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import pl.vvhoffmann.lotteryapp.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

class ResultAnnouncerConfiguration {

    public ResultAnnouncerFacade setUpForTest(ResultResponseRepository resultResponseRepository, ResultCheckerFacade resultCheckerFacade, Clock clock) {
        return new ResultAnnouncerFacade(resultCheckerFacade, resultResponseRepository, clock);
    }
}
