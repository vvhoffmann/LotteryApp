package pl.vvhoffmann.lotteryapp.infrastructure.numbersgenerator.client;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.RandomNumbersGenerable;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.SixRandomNumbersDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class RandomNumbersGeneratorRestTemplate implements RandomNumbersGenerable {

    private final RestTemplate restTemplate;

    private final String uri;
    private final int port;

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        String urlForService = getUrlForService("/api/v1.0/random");
        String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                        .queryParam("min", LOWER_LIMIT)
                        .queryParam("max", UPPER_LIMIT)
                        .queryParam("count", RANDOM_BUMBERS_QUANTITY)
                        .toUriString();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        List<Integer> numbers = response.getBody();
        System.out.println(numbers);
        return SixRandomNumbersDto.builder()
                .numbers(numbers.stream().collect(Collectors.toSet()))
                .build();
    }

    private String getUrlForService(final String service) {
        return uri + ":" + port + service;
    }
}
