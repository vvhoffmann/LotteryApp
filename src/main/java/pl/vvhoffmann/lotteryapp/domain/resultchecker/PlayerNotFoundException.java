package pl.vvhoffmann.lotteryapp.domain.resultchecker;

class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
