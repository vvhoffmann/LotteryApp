package pl.vvhoffmann.lotteryapp.domain.numberreceiver;


import java.util.Set;
import java.util.stream.Collectors;

public class NumberReceiverFacade {

    public String inputNumbers(Set<Integer> numbers)
    {
        final Set<Integer> verifiedNumbers = numbers.stream()
                .filter(number -> number >= 1)
                .filter(number -> number <= 99)
                .collect(Collectors.toSet());
        return verifiedNumbers.size() == 6 ? "success" : "failed";
    }
}
