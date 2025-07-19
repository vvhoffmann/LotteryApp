package pl.vvhoffmann.lotteryapp.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.vvhoffmann.lotteryapp.domain.numberreceiver.dto.InputNumberResultDto;
import pl.vvhoffmann.lotteryapp.domain.numberreceiver.dto.TicketDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final NumberValidator numberValidator;
    private final NumberReceiverRepository numberReceiverRepository;
    private final Clock clock;

    public InputNumberResultDto inputNumbers(Set<Integer> userNumbers) {
        final boolean areAllNumbersInRange = numberValidator.areAllNumbersInRange(userNumbers);
        if (!areAllNumbersInRange)
            return InputNumberResultDto.builder()
                    .message("failed")
                    .build();

        String ticketId = UUID.randomUUID().toString();
        LocalDateTime drawDate = LocalDateTime.now(clock);
        final Ticket savedTicket = numberReceiverRepository.save(new Ticket(ticketId, drawDate, userNumbers));
        return InputNumberResultDto.builder()
                .message("success")
                .ticketId(ticketId)
                .drawDate(drawDate)
                .userNumbers(userNumbers)
                .build();
    }

    public List<TicketDto> getUsersNumbers(LocalDateTime drawDate) {
        final List<Ticket> allTicketsByDrawDate = numberReceiverRepository.findAllTicketsByDrawDate(drawDate);
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::mapTicketToTicketDto)
                .toList();
    }

}