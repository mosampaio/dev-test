package domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public final class Position {
    private final Long id;
    private final String name;
    private final String type;
    private final GeoPosition geoPosition;

    public Position(final Long id,
                    final String name,
                    final String type,
                    final GeoPosition geoPosition) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.geoPosition = geoPosition;
    }

    public String toCSV() {
        return csvFieldValues().stream()
                .map(s -> format("\"%s\"", s))
                .collect(Collectors.joining(","));
    }

    private List<String> csvFieldValues() {
        return asList(id.toString(), name, type,
                geoPosition.getLatitude().toString(), geoPosition.getLongitude().toString());
    }

    @JsonCreator
    public static Position jsonCreator(
            @JsonProperty("_id") final Long id,
            @JsonProperty("name") final String name,
            @JsonProperty("type") final String type,
            @JsonProperty("geo_position") final GeoPosition geoPosition) {
        return new Position(id, name, type, geoPosition);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (id != null ? !id.equals(position.id) : position.id != null)
            return false;
        if (name != null ? !name.equals(position.name) : position.name != null)
            return false;
        if (type != null ? !type.equals(position.type) : position.type != null)
            return false;
        return geoPosition != null ? geoPosition.equals(position.geoPosition) : position.geoPosition == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (geoPosition != null ? geoPosition.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", geoPosition=" + geoPosition +
                '}';
    }

}
