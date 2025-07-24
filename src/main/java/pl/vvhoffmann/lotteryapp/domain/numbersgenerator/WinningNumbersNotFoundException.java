package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

public class WinningNumbersNotFoundException extends RuntimeException {

    WinningNumbersNotFoundException(String message) {
        super(message);
    }
}