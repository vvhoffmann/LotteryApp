package pl.vvhoffmann.lotteryapp.infrastructure.numbersgenerator.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.RandomNumbersGenerable;

import java.time.Duration;

@Configuration
public class RandomNumbersGeneratorClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler,
                                     @Value("${lottery.number-generator.http.client.config.connectionTimeout}") int connectionTimeout,
                                     @Value("${lottery.number-generator.http.client.config.readTimeout}") int readTimeout) {

        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    public RandomNumbersGenerable remoteRandomNumberGenerableClient(RestTemplate restTemplate,
                                                                    @Value("${lottery.number-generator.http.client.config.uri}") String uri,
                                                                    @Value("${lottery.number-generator.http.client.config.port}") int port) {

        return new RandomNumbersGeneratorRestTemplate(restTemplate, uri, port);
    }
}
