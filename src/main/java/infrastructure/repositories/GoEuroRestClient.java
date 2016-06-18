package infrastructure.repositories;

import domain.model.Position;
import domain.repository.PositionRepository;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface GoEuroRestClient extends PositionRepository {

    @RequestLine("GET /api/v2/position/suggest/en/{cityName}")
    List<Position> search(@Param("cityName") String cityName);
}
