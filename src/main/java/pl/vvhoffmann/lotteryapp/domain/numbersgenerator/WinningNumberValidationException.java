package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

public class WinningNumberValidationException extends IllegalStateException {
    public WinningNumberValidationException(String message) {
        super(message);
    }
}
