package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto.ResultResponseDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.ResultCheckerFacade;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.ResultDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultAnnouncerFacadeTest {
    ResultResponseRepository resultResponseRepository = new ResultResponseRepositoryTestImpl();
    ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);

    @Test
    @DisplayName("Should return LOSE MESSAGE when ticket lose")
    public void should_return_response_with_lose_message_if_ticket_lose() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 10, 12, 12, 0, 0);
        String id = "111";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().setUpForTest(
                resultResponseRepository,
                resultCheckerFacade,
                Clock.systemUTC());

        ResultDto mockedResultDto = ResultDto.builder()
                .id(id)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        when(resultCheckerFacade.findById(id)).thenReturn(mockedResultDto);

        //when
        final ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(id);

        //then
        ResultResponseDto expectedResultResponseDto = ResultResponseDto.builder()
                .id(id)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        ResultAnnouncerResponseDto expectedResponse = ResultAnnouncerResponseDto.builder()
                .resultResponseDto(expectedResultResponseDto)
                .message(ResponseMessage.LOSE_MESSAGE.message).build();
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Shound return WIN MESSAGE when ticket is winning")
    public void should_return_response_with_win_message_if_ticket_is_winning() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 10, 12, 12, 0, 0);
        String id = "111";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().setUpForTest(
                resultResponseRepository,
                resultCheckerFacade,
                Clock.systemUTC());

        ResultDto mockedResultDto = ResultDto.builder()
                .id(id)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findById(id)).thenReturn(mockedResultDto);

        //when
        final ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(id);

        //then
        ResultResponseDto expectedResultResponseDto = ResultResponseDto.builder()
                .id(id)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultAnnouncerResponseDto expectedResponse = ResultAnnouncerResponseDto.builder()
                .resultResponseDto(expectedResultResponseDto)
                .message(ResponseMessage.WIN_MESSAGE.message).build();
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return WAIT MESSAGE when the date is before announcement time")
    public void should_return_response_with_wait_message_if_date_is_before_announcement_time() {
//given
        LocalDateTime drawDate = LocalDateTime.of(2024, 10, 12, 12, 0, 0);
        String id = "111";
        final Clock clock = Clock.fixed(LocalDateTime.of(2024, 10, 10, 10, 0, 0)
                .toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().setUpForTest(
                resultResponseRepository,
                resultCheckerFacade,
                clock);

        ResultDto mockedResultDto = ResultDto.builder()
                .id(id)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findById(id)).thenReturn(mockedResultDto);

        //when
        final ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(id);

        //then
        ResultResponseDto expectedResultResponseDto = ResultResponseDto.builder()
                .id(id)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultAnnouncerResponseDto expectedResponse = ResultAnnouncerResponseDto.builder()
                .resultResponseDto(expectedResultResponseDto)
                .message(ResponseMessage.WAIT_MESSAGE.message).build();
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return ALREADY CHCECKED MESSAGE when hash was previously checked")
    public void should_return_already_checked_message_if_hash_was_previously_checked() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 10, 12, 12, 0, 0);
        String id = "111";
        ResultDto mockedResultDto = ResultDto.builder()
                .id(id)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        when(resultCheckerFacade.findById(id)).thenReturn(mockedResultDto);
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().setUpForTest(
                resultResponseRepository,
                resultCheckerFacade,
                Clock.systemUTC());
        final ResultAnnouncerResponseDto resultAnnouncerResponseDto1 = resultAnnouncerFacade.checkResult(id);
        String idUnderTest = resultAnnouncerResponseDto1.resultResponseDto().id();

        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(idUnderTest);

        //then
        ResultAnnouncerResponseDto expectedResponse = ResultAnnouncerResponseDto.builder()
                .resultResponseDto(resultAnnouncerResponseDto1.resultResponseDto())
                .message(ResponseMessage.ALREADY_CHECKED.message).build();
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return HASH DOESN'T EXIST MESSAGE when hash doesn't exist")
    public void should_return_response_with_hash_doesnt_exit_message_if_hash_doesnt_exist() {
        //given
        String id = "111";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().setUpForTest(
                resultResponseRepository,
                resultCheckerFacade,
                Clock.systemUTC());
        when(resultCheckerFacade.findById(id)).thenReturn(null);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(id);

        //then
        ResultAnnouncerResponseDto expectedResponse = ResultAnnouncerResponseDto.builder()
                .resultResponseDto(null)
                .message(ResponseMessage.ID_DOES_NOT_EXIST_MESSAGE.message).build();
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResponse);
    }


}
