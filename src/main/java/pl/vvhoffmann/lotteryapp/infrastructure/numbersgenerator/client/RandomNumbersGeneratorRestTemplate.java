package pl.vvhoffmann.lotteryapp.infrastructure.numbersgenerator.client;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.RandomNumbersGenerable;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.dto.SixRandomNumbersDto;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log4j2
class RandomNumbersGeneratorRestTemplate implements RandomNumbersGenerable {

    private final RestTemplate restTemplate;

    private final String uri;
    private final int port;

    public SixRandomNumbersDto generateSixRandomNumbers(int count, int lowerLimit, int upperLimit) {
        try {
            final ResponseEntity<List<Integer>> response = makeGetRequest(count, lowerLimit, upperLimit);
            Set<Integer> sixDistinctnumbers = getSixDistinctNumbers(response);
            if (sixDistinctnumbers.size() != 6) {
                log.error("Set is less than 6 numbers. Have to make request one more time");
                return generateSixRandomNumbers(count, lowerLimit, upperLimit);
            }
            log.info("Prepared 6 random numbers: {}", sixDistinctnumbers);
            return SixRandomNumbersDto.builder()
                    .numbers(sixDistinctnumbers)
                    .build();
        } catch (ResourceAccessException e) {
            log.error("Error while fetching winning numbers using http client " + e.getMessage());
            return SixRandomNumbersDto.builder().build();
        }
    }

    private Set<Integer> getSixDistinctNumbers(final ResponseEntity<List<Integer>> response) {
        final List<Integer> numbers = response.getBody();
        if (numbers == null) {
            log.error("Response body is null and returning empty List");
            return Collections.emptySet();
        }
        log.info("Response body returned : " + numbers);
        Set<Integer> distinctNumbers = new HashSet<>(numbers);
        return distinctNumbers.stream().limit(6).collect(Collectors.toSet());
    }

    private ResponseEntity<List<Integer>> makeGetRequest(final int count, final int lowerLimit, final int upperLimit) {
        String urlForService = getUrlForService("/api/v1.0/random");
        String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .queryParam("min", lowerLimit)
                .queryParam("max", upperLimit)
                .queryParam("count", count)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return response;
    }

    private String getUrlForService(final String service) {
        return uri + ":" + port + service;
    }
}
