package pl.vvhoffmann.lotteryapp.infrastructure.resultannouncer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.vvhoffmann.lotteryapp.domain.resultannouncer.ResultAnnouncerFacade;
import pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto.ResultAnnouncerResponseDto;

@RestController
@AllArgsConstructor
class ResultAnnouncerRestController {

    ResultAnnouncerFacade resultAnnouncerFacade;

    @GetMapping("/results/{ticketId}")
    public ResponseEntity<ResultAnnouncerResponseDto> chceckResultsById(@PathVariable("ticketId") String ticketId)
    {
        final ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(ticketId);
        return new ResponseEntity<>(resultAnnouncerResponseDto, HttpStatus.NOT_FOUND);
    }
}