package http.numbergenerator;

import org.springframework.web.client.RestTemplate;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.RandomNumbersGenerable;
import pl.vvhoffmann.lotteryapp.infrastructure.numbersgenerator.client.RandomNumbersGeneratorClientConfig;


public class RandomNumbersGeneratorRestTemplateIntegrationTestConfig extends RandomNumbersGeneratorClientConfig {

    public RandomNumbersGenerable remoteNumberGeneratorClient(int port, int connectionTimeout, int readTimeout) {
        final RestTemplate restTemplate = restTemplate(restTemplateResponseErrorHandler(), connectionTimeout, readTimeout);
        return remoteRandomNumberGenerableClient(restTemplate, "http://localhost", port);
    }
}