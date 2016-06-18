package application.service;

import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import domain.repository.PositionRepository;
import org.junit.Rule;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static helper.TestHelper.buildGoEuroRestClient;
import static helper.TestHelper.content;
import static helper.TestHelper.pathFromResource;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SearchServiceTest {

    private static final int PORT = 8089;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(
            wireMockConfig().port(PORT).notifier(new Slf4jNotifier(false)));

    @Test
    public void when_I_search_by_berlin_then_it_generates_a_csv_file()
            throws Exception {
        //given
        String stubbedResponseBody = content(pathFromResource("/berlin_search.json"));

        stubFor(get(urlEqualTo("/api/v2/position/suggest/en/berlin"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(stubbedResponseBody)));

        PositionRepository positionRepository = buildGoEuroRestClient(PORT);

        SearchService searchService = new SearchService(
                Paths.get(System.getProperty("java.io.tmpdir")),
                positionRepository);

        //when
        Path generatedFile = searchService.search("berlin");

        //then
        assertThat(generatedFile, is(notNullValue()));

        String generatedContent = content(generatedFile);
        String expectedContent = content(pathFromResource("/berlin_search.csv"));

        assertThat(generatedContent, is(expectedContent));
    }

}
