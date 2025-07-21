package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import lombok.AllArgsConstructor;
import pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto.ResultResponseDto;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.ResultCheckerFacade;
import pl.vvhoffmann.lotteryapp.domain.resultchecker.dto.ResultDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@AllArgsConstructor
public class ResultAnnouncerFacade {

    public static LocalTime RESULT_ANNOUNCMENT_TIME = LocalTime.of(12, 5);

    private final ResultCheckerFacade resultCheckerFacade;
    private final ResultResponseRepository resultResponseRepository;
    private final Clock clock;

    public ResultAnnouncerResponseDto checkResult(String id) {
        if (resultResponseRepository.existsById(id)) {
            final Optional<ResultResponse> optionalFromCache = resultResponseRepository.findById(id);
            if (optionalFromCache.isPresent()) {
                final ResultResponse responseFromCache = optionalFromCache.get();
                return ResultAnnouncerMapper.mapFromResultResponseToResultAnnouncerResponseDto(responseFromCache);
            }
        }
            ResultDto resultDto = resultCheckerFacade.findById(id);
            if (resultDto == null)
                return new ResultAnnouncerResponseDto(null, ResponseMessage.ID_DOES_NOT_EXIST_MESSAGE.message);
            ResultResponseDto resultResponseDto = buildResponseDto(resultDto);
            resultResponseRepository.save(buildResultResponse(resultResponseDto));
            if (resultResponseRepository.existsById(id) && !isAfterResultAnnouncementTime(resultDto))
                return new ResultAnnouncerResponseDto(resultResponseDto, ResponseMessage.WAIT_MESSAGE.message);
            if (resultCheckerFacade.findById(id).isWinner())
                return new ResultAnnouncerResponseDto(resultResponseDto, ResponseMessage.WIN_MESSAGE.message);
            return new ResultAnnouncerResponseDto(resultResponseDto, ResponseMessage.LOSE_MESSAGE.message);
    }

    private boolean isAfterResultAnnouncementTime(final ResultDto resultDto) {
        LocalDateTime announcementDateTime = LocalDateTime.of(resultDto.drawDate().toLocalDate(), RESULT_ANNOUNCMENT_TIME);
        return LocalDateTime.now(clock).isAfter(announcementDateTime);
    }

    private ResultResponse buildResultResponse(final ResultResponseDto resultResponseDto) {
        return ResultResponse.builder()
                .id(resultResponseDto.id())
                .numbers(resultResponseDto.numbers())
                .hitNumbers(resultResponseDto.hitNumbers())
                .drawDate(resultResponseDto.drawDate())
                .isWinner(resultResponseDto.isWinner())
                .build();
    }

    private ResultResponseDto buildResponseDto(final ResultDto resultDto) {
        return ResultResponseDto.builder()
                .id(resultDto.id())
                .numbers(resultDto.numbers())
                .hitNumbers(resultDto.hitNumbers())
                .drawDate(resultDto.drawDate())
                .isWinner(resultDto.isWinner())
                .build();
    }
}