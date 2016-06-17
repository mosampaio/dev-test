package application.service;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import infrastructure.repositories.PositionRestClient;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.nio.file.Files.readAllLines;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SearchServiceTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    @Ignore("WIP")
    public void when_I_search_by_berlin_then_it_generates_a_csv_file()
            throws Exception {
        //given
        String expectedResponseBody = readAllLines(
                Paths.get(getClass().getResource("/berlin_search.json").toURI())
        ).stream().collect(Collectors.joining());

        stubFor(get(urlEqualTo("/api/v2/position/suggest/en/Berlin"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(expectedResponseBody)));

        SearchService searchService = new SearchService(
                com.google.common.io.Files.createTempDir(),
                new PositionRestClient());

        //when
        File generatedFile = searchService.search("berlin");

        //then
        assertThat(generatedFile, is(notNullValue()));
        String generatedResponseBody = readAllLines(
                Paths.get(generatedFile.toURI())
        ).stream().collect(Collectors.joining());

        assertThat(generatedResponseBody, is(expectedResponseBody));
    }

}
