package domain.repository;

import domain.model.Position;

import java.util.List;

public interface PositionRepository {

    List<Position> search(String cityName);
}
