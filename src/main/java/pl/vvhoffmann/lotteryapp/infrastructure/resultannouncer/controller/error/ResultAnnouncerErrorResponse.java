package pl.vvhoffmann.lotteryapp.infrastructure.resultannouncer.controller.error;

import org.springframework.http.HttpStatus;

public record ResultAnnouncerErrorResponse(
        String message,
        HttpStatus status
){
}