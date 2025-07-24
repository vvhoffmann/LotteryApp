package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.vvhoffmann.lotteryapp.domain.AdjustableClock;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.NumberReceiverResponseDto;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.dto.TicketDto;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumbersReceiverFacadeTest {

    private final TicketRepository ticketRepository = new TicketRepositoryTestImpl();
    Clock clock = Clock.systemUTC();


    @Test
    @DisplayName("Should return correct response when user gave 6 numbers")
    public void should_return_correct_response_when_user_input_six_numbers_in_range() {
        //given
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration()
                .numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();

        TicketDto generatedTicket = TicketDto.builder()
                .ticketId(hashGenerator.getHash())
                .numbers(numbersFromUser)
                .drawDate(nextDrawDate)
                .build();

        //when
        final NumberReceiverResponseDto result = numbersReceiverFacade.inputNumbers(numbersFromUser);

        //then
        NumberReceiverResponseDto expectedResponse = new NumberReceiverResponseDto(
                generatedTicket,
                ValidationResult.INPUT_SUCCESS.message);
        assertThat(result).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return 'failed' when user gave less than 6 numbers")
    public void should_return_failed_when_user_gave_less_than_six_numbers() {
        //given
        HashGenerator hashGenerator = new HashGenerator();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration()
                .numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5);
        //when
        final NumberReceiverResponseDto resultResponse = numbersReceiverFacade.inputNumbers(numbersFromUser);
        //then
        NumberReceiverResponseDto expectedResponse = new NumberReceiverResponseDto(
                null,
                ValidationResult.NOT_SIX_NUMBERS_GIVEN.message);
        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return 'failed' when user gave more than 6 numbers")
    public void should_return_failed_when_user_gave_more_than_six_numbers() {
        //given
        HashGenerator hashGenerator = new HashGenerator();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration()
                .numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6, 7, 8);
        //when
        final NumberReceiverResponseDto resultResponse = numbersReceiverFacade.inputNumbers(numbersFromUser);
        //then
        NumberReceiverResponseDto expectedResponse = new NumberReceiverResponseDto(
                null,
                ValidationResult.NOT_SIX_NUMBERS_GIVEN.message);
        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return 'failed' when user gave at least one number out of range 1-99")
    public void should_return_failed_when_user_gave_at_least_one_number_out_of_range_1_to_99() {
        //given
        HashGenerator hashGenerator = new HashGenerator();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 30, 4, 500, 6);
        //when
        final NumberReceiverResponseDto resultResponse = numbersReceiverFacade.inputNumbers(numbersFromUser);
        //
        NumberReceiverResponseDto expectedResponse = new NumberReceiverResponseDto(null, ValidationResult.NOT_IN_RANGE.message);
        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return 'failed' when user gave at least one negative number out of range 1-99")
    public void should_return_failed_when_user_gave_at_least_one_negative_number_out_of_range_1_to_99() {
        //given
        HashGenerator hashGenerator = new HashGenerator();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, -4, 5, 6);
        //when
        final NumberReceiverResponseDto resultResponse = numbersReceiverFacade.inputNumbers(numbersFromUser);
        //
        NumberReceiverResponseDto expectedResponse = new NumberReceiverResponseDto(null, ValidationResult.NOT_IN_RANGE.message);
        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return correct hash")
    public void should_return_correct_hash() {
        //given
        HashGenerator hashGenerator = new HashGenerator();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        //when
        String hash = numbersReceiverFacade.inputNumbers(numbersFromUser).ticketDto().ticketId();

        //then
        assertThat(hash).isNotNull();
        assertThat(hash).hasSize(36);
    }

    @Test
    @DisplayName("Should return correct draw date")
    public void should_return_correct_draw_date() {
        //given
        Clock clock = Clock.fixed(LocalDateTime.of(2024, 10, 10, 10, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        HashGenerator hashGenerator = new HashGenerator();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        //when
        LocalDateTime ticketDrawDate = numbersReceiverFacade.inputNumbers(numbersFromUser).ticketDto().drawDate();

        //then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 10, 12, 12, 0, 0);
        assertThat(ticketDrawDate).isEqualTo(expectedDrawDate);
    }

    @Test
    @DisplayName("Should return saturday 12:00 draw time when it's saturday and it's earlier than 12:00")
    public void should_return_saturday_noon_draw_date_when_it_is_earlier_saturday_time_than_saturday_noon() {
        //given
        Clock clock = Clock.fixed(LocalDateTime.of(2024, 10, 12, 10, 23, 0).toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        HashGenerator hashGenerator = new HashGenerator();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        //when
        LocalDateTime ticketDrawDate = numbersReceiverFacade.inputNumbers(numbersFromUser).ticketDto().drawDate();

        //then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 10, 12, 12, 0, 0);
        assertThat(ticketDrawDate).isEqualTo(expectedDrawDate);
    }

    @Test
    @DisplayName("Should return next saturday 12:00 draw time when it's saturday and it's 12:00")
    public void should_return_next_saturday_noon_draw_date_when_it_is_saturday_noon() {
        //given
        Clock clock = Clock.fixed(LocalDateTime.of(2024, 10, 12, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        HashGenerator hashGenerator = new HashGenerator();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        //when
        LocalDateTime ticketDrawDate = numbersReceiverFacade.inputNumbers(numbersFromUser).ticketDto().drawDate();

        //then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 10, 19, 12, 0, 0);
        assertThat(ticketDrawDate).isEqualTo(expectedDrawDate);
    }

    @Test
    @DisplayName("Should return next saturday 12:00 draw time when it's saturday and it's after 12:00")
    public void should_return_next_saturday_noon_draw_date_when_it_is_saturday_afternoon() {
        //given
        Clock clock = Clock.fixed(LocalDateTime.of(2024, 10, 12, 13, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        HashGenerator hashGenerator = new HashGenerator();
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        final Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        //when
        LocalDateTime ticketDrawDate = numbersReceiverFacade.inputNumbers(numbersFromUser).ticketDto().drawDate();

        //then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 10, 19, 12, 0, 0);
        assertThat(ticketDrawDate).isEqualTo(expectedDrawDate);
    }

    @Test
    @DisplayName("Should return tickets with correct draw date")
    public void should_return_tickets_with_correct_draw_date() {
        //given
        HashGenerator hashGenerator = new HashGenerator();

        Instant fixedInstant = LocalDateTime.of(2024, 10, 17, 12, 0, 0).toInstant(ZoneOffset.UTC);
        ZoneId zoneId = ZoneId.of("Europe/London");
        AdjustableClock clock = new AdjustableClock(fixedInstant, zoneId);

        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        NumberReceiverResponseDto numberReceiverResponseDto = numbersReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        clock.plusDays(1);
        NumberReceiverResponseDto numberReceiverResponseDto1 = numbersReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        clock.plusDays(1);
        NumberReceiverResponseDto numberReceiverResponseDto2 = numbersReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        clock.plusDays(1);
        NumberReceiverResponseDto numberReceiverResponseDto3 = numbersReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        TicketDto ticketDto = numberReceiverResponseDto.ticketDto();
        TicketDto ticketDto1 = numberReceiverResponseDto1.ticketDto();
        LocalDateTime expectedDrawDate = ticketDto.drawDate();

        //when
        final List<TicketDto> ticketDtos = numbersReceiverFacade.retrieveAllTicketsByNextDrawDate(expectedDrawDate);

        //
        assertThat(ticketDtos).containsOnly(ticketDto, ticketDto1);
    }

    @Test
    @DisplayName("Should return empty collection when there are no tickets for the draw date")
    public void should_return_empty_collections_if_there_are_no_tickets() {
        // given
        HashGenerable hashGenerator = new HashGenerator();
        Clock clock = Clock.fixed(LocalDateTime.of(2024, 10, 12, 11, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        LocalDateTime drawDate = LocalDateTime.now(clock);

        // when
        List<TicketDto> allTicketsByDate = numbersReceiverFacade.retrieveAllTicketsByNextDrawDate(drawDate);
        // then
        assertThat(allTicketsByDate).isEmpty();
    }

    @Test
    @DisplayName("It should return empty collection")
    public void it_should_return_empty_collections_if_given_date_is_after_next_drawDate() {
        // given
        HashGenerable hashGenerator = new HashGenerator();

        Clock clock = Clock.fixed(LocalDateTime.of(2024, 10, 13, 13, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.of("Europe/London"));
        NumbersReceiverFacade numbersReceiverFacade = new NumberReceiverConfiguration().numbersReceiverFacade(ticketRepository, hashGenerator, clock);
        NumberReceiverResponseDto numberReceiverResponseDto = numbersReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));

        LocalDateTime drawDate = numberReceiverResponseDto.ticketDto().drawDate();

        // when
        List<TicketDto> allTicketsByDate = numbersReceiverFacade.retrieveAllTicketsByNextDrawDate(drawDate.plusWeeks(1L));
        // then
        assertThat(allTicketsByDate).isEmpty();
    }

}
