package pl.vvhoffmann.lotteryapp.infrastructure.apivalidation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.vvhoffmann.lotteryapp.infrastructure.apivalidation.dto.ApiValidationErrorDto;

import java.util.List;

@ControllerAdvice
public class ApiValidationErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiValidationErrorDto handleValidationException(MethodArgumentNotValidException ex)
    {
        final List<String> errors = getErrorsFromException(ex);
        return new ApiValidationErrorDto(errors, HttpStatus.BAD_REQUEST);

    }

    private List<String> getErrorsFromException(final MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}