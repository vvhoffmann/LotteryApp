package pl.vvhoffmann.lotteryapp.infrastructure.resultchecker.scheduler.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
class ResultCheckerSchedulerErrorHandler {

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    @ExceptionHandler(WinningNumbersNotGeneratedException.class)
    public ResultCheckerSchedulerErrorResponse handleWinningNumbersNotGeneratedException(WinningNumbersNotGeneratedException e)
    {
        String message = e.getMessage();
        log.error(message);
        return new ResultCheckerSchedulerErrorResponse(message, HttpStatus.EXPECTATION_FAILED);
    }
}