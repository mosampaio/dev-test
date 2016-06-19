package application.service;

import domain.model.Position;
import domain.repository.PositionRepository;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static java.lang.System.nanoTime;
import static java.nio.file.Files.write;
import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

public class SearchService {

    private static final Logger LOG = getLogger(SearchService.class);

    private final Path outputDirectory;
    private final PositionRepository positionRepository;

    public SearchService(final Path outputDirectory, final PositionRepository positionRepository) {
        this.outputDirectory = outputDirectory;
        this.positionRepository = positionRepository;
    }

    public Path search(final String cityName) {
        try {
            final String file = format("%s%s%s-%s.csv", outputDirectory, File.separator, cityName, nanoTime());

            final String content = this.positionRepository.search(cityName)
                    .stream()
                    .map(Position::toCSV)
                    .collect(joining(lineSeparator()));

            return write(Paths.get(file), content.getBytes());
        } catch (IOException e) {
            LOG.error("Error on file generation: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
