package infrastructure.guice;

import application.service.ExportPositions;
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
import static java.lang.System.getenv;
import static java.util.Optional.ofNullable;

public class GoEuroModule extends AbstractModule {

    private static final String HOST = "host";
    private static final String OUTPUT_DIRECTORY = "outputDirectory";

    private static final String DEFAULT_HOST = "http://api.goeuro.com";
    private static final String DEFAULT_OUTPUT_DIR = System.getProperty("java.io.tmpdir");

    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(named(HOST))
                .toInstance(ofNullable(getenv("GO_EURO_HOST")).orElse(DEFAULT_HOST));

        bind(Path.class)
                .annotatedWith(named(OUTPUT_DIRECTORY))
                .toInstance(Paths.get(ofNullable(getenv("OUTPUT_DIRECTORY")).orElse(DEFAULT_OUTPUT_DIR)));
    }

    @Provides
    public ExportPositions exportPositions(
            @Named(OUTPUT_DIRECTORY) final Path outputDirectory,
            final PositionRepository positionRepository) {
        return new ExportPositions(outputDirectory, positionRepository);
    }

    @Provides
    public PositionRepository positionRepository(@Named(HOST) final String host) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(GoEuroRestClient.class, host);
    }

}
