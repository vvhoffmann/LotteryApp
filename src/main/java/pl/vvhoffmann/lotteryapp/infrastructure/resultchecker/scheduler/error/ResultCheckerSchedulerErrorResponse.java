package pl.vvhoffmann.lotteryapp.infrastructure.resultchecker.scheduler.error;

import org.springframework.http.HttpStatus;

public record ResultCheckerSchedulerErrorResponse (
        String message,
        HttpStatus status
){
}