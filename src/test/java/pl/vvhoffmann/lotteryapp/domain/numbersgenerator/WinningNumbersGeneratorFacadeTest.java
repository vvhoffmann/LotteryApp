package pl.vvhoffmann.lotteryapp.domain.numbersgenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.WinningNumbersDto;
import pl.vvhoffmann.lotteryapp.domain.numbersreceiver.NumbersReceiverFacade;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WinningNumbersGeneratorFacadeTest {

    private final WinningNumbersRepository winningNumbersRepository = new NumbersGeneratorRepositoryTestImpl();
    private final NumbersReceiverFacade numbersReceiverFacade = mock(NumbersReceiverFacade.class);

    @Test
    @DisplayName("Should return numbers set of size 6")
    public void should_return_set_of_required_size() {
        //given
        RandomNumbersGenerable numbersGenerator = new WinningNumbersGeneratorTestImpl();
        when(numbersReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().setUpForTest(numbersGenerator, winningNumbersRepository, numbersReceiverFacade);
        //when
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        //then
        assertThat(winningNumbersDto.winningNumbers().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("Should return set of 6 numbers in range 1-99")
    public void should_return_set_of_required_size_within_required_range() {
        //given
        RandomNumbersGenerable numbersGenerator = new WinningNumbersGeneratorTestImpl();
        when(numbersReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().setUpForTest(numbersGenerator, winningNumbersRepository, numbersReceiverFacade);
        //when
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        //then
        int upperLimit = 99;
        int lowerLimit = 1;
        Set<Integer> winningNumbers = winningNumbersDto.winningNumbers();
        boolean numbersInRange = winningNumbers.stream()
                        .allMatch(number -> number >= lowerLimit && number <= upperLimit);
        assertTrue(numbersInRange);
    }

    @Test
    @DisplayName("Should throw an Exception when number is not in range 1-99")
    public void should_throw_an_exception_when_number_not_in_range() {
        //given
        Set<Integer> outOfRange = Set.of(1, 2, 3, 4, 100, 7);
        RandomNumbersGenerable numbersGenerator = new WinningNumbersGeneratorTestImpl(outOfRange);
        when(numbersReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().setUpForTest(numbersGenerator, winningNumbersRepository, numbersReceiverFacade);
        //when
        //then
        assertThrows(WinningNumberValidationException.class, winningNumbersGeneratorFacade::generateWinningNumbers, "Number is out of range");

    }

    @Test
    @DisplayName("Should throw an Exception when there are less numbers than 6")
    public void should_throw_an_exception_when_there_is_less_numbers_than_six() {
        //given
        Set<Integer> outOfRange = Set.of(1, 2, 3, 4, 7);
        RandomNumbersGenerable numbersGenerator = new WinningNumbersGeneratorTestImpl(outOfRange);
        when(numbersReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().setUpForTest(numbersGenerator, winningNumbersRepository, numbersReceiverFacade);
        //when
        //then
        assertThrows(WinningNumberValidationException.class, winningNumbersGeneratorFacade::generateWinningNumbers, "There are more/less than 6 numbers");

    }

    @Test
    @DisplayName("Should throw an Exception when there are more than 6 numbers")
    public void should_throw_an_exception_when_there_is_more_than_six_numbers() {
        //given
        Set<Integer> outOfRange = Set.of(1, 2, 3, 4, 5, 6, 7);
        RandomNumbersGenerable numbersGenerator = new WinningNumbersGeneratorTestImpl(outOfRange);
        when(numbersReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().setUpForTest(numbersGenerator, winningNumbersRepository, numbersReceiverFacade);
        //when
        //then
        assertThrows(WinningNumberValidationException.class,
                winningNumbersGeneratorFacade::generateWinningNumbers,
                "There are more/less than 6 numbers");

    }

    @Test
    @DisplayName("Should return set of unique values")
    public void should_return_set_of_unique_values() {
        //given
        RandomNumbersGenerable numbersGenerator = new WinningNumbersGeneratorTestImpl();
        when(numbersReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().setUpForTest(numbersGenerator, winningNumbersRepository, numbersReceiverFacade);
        //when
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        //then
        int winningNumbersSize = new HashSet<>(winningNumbersDto.winningNumbers()).size();
        assertThat(winningNumbersSize).isEqualTo(6);
    }

    @Test
    @DisplayName("Should return winning numbers by given date")
    public void it_should_return_winning_numbers_by_given_date() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2024, 10, 12, 12, 0, 0);
        Set<Integer> generatedWinningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .numbers(generatedWinningNumbers)
                .drawDate(drawDate)
                .id(id)
                .build();
        winningNumbersRepository.save(winningNumbers);

        RandomNumbersGenerable numbersGenerator = new WinningNumbersGeneratorTestImpl(generatedWinningNumbers);
        when(numbersReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().setUpForTest(numbersGenerator, winningNumbersRepository, numbersReceiverFacade);
        //when
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.retrieveWinningNumbersByDrawDate(drawDate);
        //then
        WinningNumbersDto expectedWinningNumbersDto = WinningNumbersDto.builder()
                .winningNumbers(generatedWinningNumbers)
                .drawDate(drawDate)
                .build();
        assertThat(winningNumbersDto).isEqualTo(expectedWinningNumbersDto);
    }

    @Test
    @DisplayName("Should throw an exception when retrieving numbers by given date failed")
    public void should_throw_an_exception_when_retrieving_numbers_by_given_date_failed() {
        //given
        final LocalDateTime drawDate = LocalDateTime.of(2024, 10, 13, 12, 0, 0);
        RandomNumbersGenerable numbersGenerator = new WinningNumbersGeneratorTestImpl();
        when(numbersReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().setUpForTest(numbersGenerator, winningNumbersRepository, numbersReceiverFacade);
        //when
        //then
        assertThrows(WinningNumbersNotFoundException.class,
                () -> winningNumbersGeneratorFacade.retrieveWinningNumbersByDrawDate(drawDate),
                "Winning numbers on " + drawDate + " not found");
    }

}
