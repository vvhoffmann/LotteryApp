package pl.vvhoffmann.lotteryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.WinningNumbersGeneratorFacadeConfigurationProperties;
import pl.vvhoffmann.lotteryapp.infrastructure.numbersgenerator.client.RandomNumberGeneratorRestTemplateConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({WinningNumbersGeneratorFacadeConfigurationProperties.class,
                                RandomNumberGeneratorRestTemplateConfigurationProperties.class
})
public class LotteryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotteryAppApplication.class, args);
    }

}
