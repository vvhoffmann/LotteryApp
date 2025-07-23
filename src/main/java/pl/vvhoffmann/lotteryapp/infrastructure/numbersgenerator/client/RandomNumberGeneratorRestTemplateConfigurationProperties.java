package pl.vvhoffmann.lotteryapp.infrastructure.numbersgenerator.client;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix= "lottery.number-generator.http.client.config")
public record RandomNumberGeneratorRestTemplateConfigurationProperties(
        String uri,
        int port,
        int connectionTimeout,
        int readTimeout) {
}