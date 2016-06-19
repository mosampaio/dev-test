package infrastructure.guice;

import application.service.SearchService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import domain.repository.PositionRepository;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import infrastructure.repositories.GoEuroRestClient;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.inject.name.Names.named;

public class GoEuroModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(named("host"))
                .toInstance("http://api.goeuro.com");

        bind(Path.class).annotatedWith(named("outputDirectory"))
                .toInstance(Paths.get(System.getProperty("java.io.tmpdir")));
    }

    @Provides
    public SearchService searchService(
            @Named("outputDirectory") final Path outputDirectory,
            final PositionRepository positionRepository) {
        return new SearchService(outputDirectory, positionRepository);
    }

    @Provides
    public PositionRepository positionRepository(@Named("host") final String host) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(GoEuroRestClient.class, host);
    }

}
