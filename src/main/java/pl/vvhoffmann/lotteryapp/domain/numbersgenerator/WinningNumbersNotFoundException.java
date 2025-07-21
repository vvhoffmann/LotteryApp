package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

class WinningNumbersNotFoundException extends RuntimeException {

    WinningNumbersNotFoundException(String message) {
        super(message);
    }
}