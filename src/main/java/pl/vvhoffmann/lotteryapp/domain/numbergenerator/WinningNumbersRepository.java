package pl.vvhoffmann.lotteryapp.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Optional;

interface WinningNumbersRepository {

    Optional<WinningNumbers> findNumbersByDrawDate(LocalDateTime drawDate);

    boolean existsByDate(LocalDateTime drawDate);

    WinningNumbers save(WinningNumbers winningNumbers);
}
