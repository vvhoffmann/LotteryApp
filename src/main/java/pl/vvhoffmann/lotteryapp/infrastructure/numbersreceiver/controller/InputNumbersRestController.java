package pl.vvhoffmann.lotteryapp.infrastructure.numbersreceiver.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.NumberReceiverResponseDto;
import pl.vvhoffmann.lotteryapp.infrastructure.numbersreceiver.controller.dto.InputNumbersRequestDto;

import java.util.HashSet;
import java.util.Set;

@RestController
@AllArgsConstructor
@Log4j2
public class InputNumbersRestController {

    private final NumbersReceiverFacade numbersReceiverFacade;

    @PostMapping("/inputNumbers")
    public ResponseEntity<NumberReceiverResponseDto> inputNumbers(@RequestBody @Valid InputNumbersRequestDto numbers)
    {
        Set<Integer> receivedNumbers = new HashSet<>(numbers.inputNumbers());
        NumberReceiverResponseDto responseDto = numbersReceiverFacade.inputNumbers(receivedNumbers);
        return ResponseEntity.ok(responseDto);
    }
}