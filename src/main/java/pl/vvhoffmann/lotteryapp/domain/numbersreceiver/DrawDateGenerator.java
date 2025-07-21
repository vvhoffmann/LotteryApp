package pl.vvhoffmann.lotteryapp.domain.numbersreceiver;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

class DrawDateGenerator {

    private static final LocalTime DRAW_TIME = LocalTime.of(12, 0, 0);
    private static final TemporalAdjuster NEXT_DRAW_DAY = TemporalAdjusters.next(DayOfWeek.SATURDAY);
    private final Clock clock;


    DrawDateGenerator(final Clock clock) {
        this.clock = clock;
    }

    public LocalDateTime getNextDrawDate() {
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        if(isSaturdayAndBeforeNoon(currentDateTime))
            return LocalDateTime.of(currentDateTime.toLocalDate(), DRAW_TIME);
        LocalDateTime drawDateTime = currentDateTime.with(NEXT_DRAW_DAY);
        return LocalDateTime.of(drawDateTime.toLocalDate(),DRAW_TIME);
    }

    private boolean isSaturdayAndBeforeNoon(final LocalDateTime currentDateTime) {
        return currentDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) &&
                currentDateTime.toLocalTime().isBefore(DRAW_TIME);
    }
}