package infrastructure.repositories;

import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import domain.model.GeoPosition;
import domain.model.Position;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.Rule;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GoEuroRestClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(
            wireMockConfig().port(8089).notifier(new Slf4jNotifier(false)));

    @Test
    public void itShouldReturnListOfPositionsWhenISearch()
            throws Exception {
        //given
        String expectedResponseBody = readAllLines(
                Paths.get(getClass().getResource("/berlin_search.json").toURI())
        ).stream().collect(Collectors.joining());

        stubFor(get(urlEqualTo("/api/v2/position/suggest/en/berlin"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(expectedResponseBody)));

        GoEuroRestClient goEuroRestClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(GoEuroRestClient.class, "http://localhost:8089");


        List<Position> positions = goEuroRestClient.search("berlin");

        assertThat(positions, is(asList(
                new Position(376217L, "Berlin", "location", new GeoPosition(52.52437, 13.41053)),
                new Position(448103L, "Berlingo", "location", new GeoPosition(45.50298, 10.04366)),
                new Position(425332L, "Berlingerode", "location", new GeoPosition(51.45775, 10.2384)),
                new Position(425326L, "Bernau bei Berlin", "location", new GeoPosition(52.67982, 13.58708)),
                new Position(314826L, "Berlin Tegel", "airport", new GeoPosition(52.5548, 13.28903)),
                new Position(314827L, "Berlin Schönefeld", "airport", new GeoPosition(52.3887261, 13.5180874)),
                new Position(334196L, "Berlin Hbf", "station", new GeoPosition(52.525589, 13.369548)),
                new Position(333977L, "Berlin Ostbahnhof", "station", new GeoPosition(52.510972, 13.434567))
        )));
    }
}