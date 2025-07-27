package pl.vvhoffmann.lotteryapp.domain.resultchecker;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}