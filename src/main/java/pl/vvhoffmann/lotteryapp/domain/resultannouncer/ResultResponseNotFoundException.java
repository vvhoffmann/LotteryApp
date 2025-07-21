package pl.vvhoffmann.lotteryapp.domain.resultannouncer;

public class ResultResponseNotFoundException extends RuntimeException {

    ResultResponseNotFoundException(String message) {
        super(message);
    }
}