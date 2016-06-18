package application.service;

import domain.model.Position;
import domain.repository.PositionRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static java.lang.String.format;
import static java.lang.System.nanoTime;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.joining;

public class SearchService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SearchService.class);

    private final Path outputDirectory;
    private final PositionRepository positionRepository;

    public SearchService(Path outputDirectory, PositionRepository positionRepository) {
        this.outputDirectory = outputDirectory;
        this.positionRepository = positionRepository;
    }

    public Path search(final String cityName) {
        try {
            final String file = format("%s%s%s-%s.csv", outputDirectory, File.separator, cityName, nanoTime());

            final String content = this.positionRepository.search(cityName)
                    .stream()
                    .map(Position::toCSV)
                    .collect(joining("\n"));

            return write(
                    get(file),
                    content.getBytes());
        } catch (IOException e) {
            log.error("Error on file generation: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
