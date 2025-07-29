package pl.vvhoffmann.lotteryapp.infrastructure.resultchecker.scheduler.error;

public class WinningNumbersNotGeneratedException extends RuntimeException {
    public WinningNumbersNotGeneratedException() {
        super("Winning numbers generation failed");
    }
}
