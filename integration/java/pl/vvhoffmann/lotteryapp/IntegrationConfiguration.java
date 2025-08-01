package pl.vvhoffmann.lotteryapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import pl.vvhoffmann.lotteryapp.domain.AdjustableClock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Configuration
@Profile("integration")
public class IntegrationConfiguration {

    @Bean
    @Primary
    AdjustableClock clock()
    {
        return AdjustableClock.ofLocalDateAndLocalTime(
                LocalDate. of(2024,10,10),
                LocalTime.of(11,0),
                ZoneId.systemDefault());
    }
}