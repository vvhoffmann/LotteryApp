package pl.vvhoffmann.lotteryapp.domain.numbergenerator;

public class WinningNumberValidationException extends IllegalStateException {
    public WinningNumberValidationException(String message) {
        super(message);
    }
}
