package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

import pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.vvhoffmann.lotteryapp.domain.resultannouncer.dto.ResultResponseDto;

class ResultAnnouncerMapper {


    static ResultResponseDto mapFromResultResponseToResultResponseDto(final ResultResponse resultFromCache) {
        return ResultResponseDto.builder()
                .id(resultFromCache.id())
                .drawDate(resultFromCache.drawDate())
                .numbers(resultFromCache.numbers())
                .hitNumbers(resultFromCache.hitNumbers())
                .isWinner(resultFromCache.isWinner())
                .build();
    }

    static ResultAnnouncerResponseDto mapFromResultResponseToResultAnnouncerResponseDto(final ResultResponse resultFromCache) {
        return ResultAnnouncerResponseDto.builder()
                .resultResponseDto(mapFromResultResponseToResultResponseDto(resultFromCache))
                .message(ResponseMessage.ALREADY_CHECKED.message)
                .build();
    }

}
