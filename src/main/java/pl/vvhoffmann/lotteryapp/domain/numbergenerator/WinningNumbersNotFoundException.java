package pl.vvhoffmann.lotteryapp.domain.numbergenerator;

class WinningNumbersNotFoundException extends RuntimeException {

    WinningNumbersNotFoundException(String message) {
        super(message);
    }
}