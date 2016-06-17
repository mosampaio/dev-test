package application.service;

import domain.repository.PositionRepository;

import java.io.File;

public class SearchService {

    private final File directory;
    private final PositionRepository positionRepository;

    public SearchService(File directory, PositionRepository positionRepository) {
        this.directory = directory;
        this.positionRepository = positionRepository;
    }

    public File search(String cityName) {
        return null;
    }
}
