package application.service;

import domain.model.Position;
import domain.repository.PositionRepository;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static java.lang.System.nanoTime;
import static java.nio.file.Files.write;
import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

public class ExportPositions {

    private static final Logger LOG = getLogger(ExportPositions.class);

    private final Path outputDirectory;
    private final PositionRepository positionRepository;

    public ExportPositions(final Path outputDirectory, final PositionRepository positionRepository) {
        this.outputDirectory = outputDirectory;
        this.positionRepository = positionRepository;
    }

    public Path search(final String cityName) {
        try {
            final String fileName = format("%s%s%s-%s.csv", outputDirectory, separator, cityName, nanoTime());

            final String content = this.positionRepository.search(cityName)
                    .stream()
                    .map(Position::toCSV)
                    .collect(joining(lineSeparator()));

            return write(Paths.get(fileName), content.getBytes());
        } catch (IOException e) {
            LOG.error("Error on file generation: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
