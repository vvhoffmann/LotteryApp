package http.numbergenerator;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.vvhoffmann.lotteryapp.domain.numbersgenerator.RandomNumbersGenerable;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class RandomNumbersGeneratorRestTemplateErrorsIntegrationTest {

    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String APPLICATION_JSON_CONTENT_TYPE_VALUE = "application/json";

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

     RandomNumbersGenerable randomNumbersGenerable = new RandomNumbersGeneratorRestTemplateIntegrationTestConfig().remoteNumberGeneratorClient(
            wireMockServer.getPort(),
            1000,
            1000);

     @Test
     public void should_return_null_numbers_when_fault_empty_response()
     {
         //given
         wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                 .willReturn(WireMock.aResponse()
                         .withStatus(200)
                         .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                         .withFault(Fault.EMPTY_RESPONSE)));

         //when
         final Throwable throwable = catchThrowable(() -> randomNumbersGenerable.generateSixRandomNumbers(25, 1, 99));
         // then
         assertThat(throwable).isInstanceOf(ResponseStatusException.class);
         assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
     }

     @Test
     public void should_return_null_numbers_when_fault_connection_reset_by_peer()
     {
         //given
         wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                 .willReturn(WireMock.aResponse()
                         .withStatus(200)
                         .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                         .withFault(Fault.CONNECTION_RESET_BY_PEER)));

         //when
         final Throwable throwable = catchThrowable(() -> randomNumbersGenerable.generateSixRandomNumbers(25, 1, 99));
         // then
         assertThat(throwable).isInstanceOf(ResponseStatusException.class);
         assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
     }

     @Test
     public void should_return_null_numbers_when_fault_malformed_response_chunk()
     {
         //given
         wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                 .willReturn(WireMock.aResponse()
                         .withStatus(200)
                         .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                         .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

         //when
         final Throwable throwable = catchThrowable(() -> randomNumbersGenerable.generateSixRandomNumbers(25, 1, 99));
         // then
         assertThat(throwable).isInstanceOf(ResponseStatusException.class);
         assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
     }

     @Test
     public void should_return_null_numbers_when_fault_random_data_then_close()
     {
         //given
         wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                 .willReturn(WireMock.aResponse()
                         .withStatus(200)
                         .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                         .withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

         //when
         final Throwable throwable = catchThrowable(() -> randomNumbersGenerable.generateSixRandomNumbers(25, 1, 99));
         // then
         assertThat(throwable).isInstanceOf(ResponseStatusException.class);
         assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
     }





}
