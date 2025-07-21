package pl.vvhoffmann.lotteryapp.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class NumbersGeneratorRepositoryTestImpl implements WinningNumbersRepository{
    private final Map<LocalDateTime, WinningNumbers> db = new ConcurrentHashMap<>();


    @Override
    public Optional<WinningNumbers> findNumbersByDrawDate(final LocalDateTime drawDate) {
        return Optional.ofNullable(db.get(drawDate));
    }

    @Override
    public boolean existsByDate(final LocalDateTime drawDate) {
        db.get(drawDate);
        return true;
    }

    @Override
    public WinningNumbers save(final WinningNumbers winningNumbers) {
        return db.put(winningNumbers.drawDate(), winningNumbers);
    }
}
