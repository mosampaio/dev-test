package infrastructure.repositories;

import domain.model.Position;
import domain.repository.PositionRepository;

import java.util.List;

public class PositionRestClient implements PositionRepository {
    @Override
    public List<Position> search(String cityName) {
        return null;
    }
}
