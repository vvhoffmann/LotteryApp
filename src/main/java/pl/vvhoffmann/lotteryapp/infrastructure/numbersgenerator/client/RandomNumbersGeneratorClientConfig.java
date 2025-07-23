package pl.vvhoffmann.lotteryapp.infrastructure.numbersgenerator.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.RandomNumbersGenerable;

@Configuration
class RandomNumbersGeneratorClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler()
    {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler,
                                     @Value("$lottery.number-generator.facade.connectionTimeout:5000") int connectionTimeout,
                                     @Value("$lottery.number-generator.facade.readTimeout:5000") int readTimeout){
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);

        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .build();

    }

    @Bean
    public RandomNumbersGenerable remoteRandomNumberGenerable(RestTemplate restTemplate,
                                                              @Value("${RandomNumberGeneratorRestTemplateConfigurationProperties}") String uri,
                                                              @Value("${RandomNumberGeneratorRestTemplateConfigurationProperties}") int port) {

        return new RandomNumbersGeneratorRestTemplate(restTemplate, uri, port);
    }
}
