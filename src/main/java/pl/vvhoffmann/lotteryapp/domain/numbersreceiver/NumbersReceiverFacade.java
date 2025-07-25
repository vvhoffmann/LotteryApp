package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import lombok.AllArgsConstructor;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.NumberReceiverResponseDto;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class NumbersReceiverFacade {

    private final TicketRepository ticketRepository;
    private final DrawDateGenerator drawDateGenerator;
    private final HashGenerable hashGenerator;
    private final NumberValidator numberValidator;

    public NumberReceiverResponseDto inputNumbers(Set<Integer> userNumbers) {

        final List<ValidationResult> validationNumbersList = numberValidator.validate(userNumbers);

        if (!validationNumbersList.isEmpty()) {
            String resultMessage = numberValidator.createResultMessage();
            return NumberReceiverResponseDto.builder()
                    .ticketDto(null)
                    .message(resultMessage)
                    .build();
        }

        String ticketId = hashGenerator.getHash();
        LocalDateTime drawDate = drawDateGenerator.getNextDrawDate();

        TicketDto generatedTicket = TicketDto.builder()
                .ticketId(ticketId)
                .numbers(userNumbers)
                .drawDate(drawDate)
                .build();

        final Ticket savedTicket = Ticket.builder()
                .id(ticketId)
                .numbers(generatedTicket.numbers())
                .drawDate(generatedTicket.drawDate())
                .build();

        ticketRepository.save(savedTicket);

        return NumberReceiverResponseDto.builder()
                .ticketDto(generatedTicket)
                .message(ValidationResult.INPUT_SUCCESS.message)
                .build();
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate() {
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();
        return retrieveAllTicketsByNextDrawDate(nextDrawDate);
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime date) {
        LocalDateTime drawDate = drawDateGenerator.getNextDrawDate();
        if (date.isAfter(drawDate)) {
            return Collections.emptyList();
        }

        return ticketRepository.findAllByDrawDate(date)
                .stream()
                .filter(ticket -> ticket.drawDate().isEqual(date))
                .map(ticket -> TicketDto.builder()
                        .ticketId(ticket.id())
                        .numbers(ticket.numbers())
                        .drawDate(ticket.drawDate()).build())
                .toList();
    }

    public LocalDateTime retrieveNextDrawDate() {
        return drawDateGenerator.getNextDrawDate();
    }


    public TicketDto findByHash(String hash) {
        Ticket ticket = ticketRepository.findById(hash);
        return TicketDto.builder()
                .ticketId(ticket.id())
                .numbers(ticket.numbers())
                .drawDate(ticket.drawDate())
                .build();
    }
}