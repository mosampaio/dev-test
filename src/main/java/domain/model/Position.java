package domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

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
        return fieldValues()
                .map(s -> format("\"%s\"", s))
                .collect(joining(","));
    }

    Stream<String> fieldValues() {
        return Stream.concat(asList(id.toString(), name, type).stream(), geoPosition.fieldValues());
    }

    @JsonCreator
    public static Position jsonCreator(
            @JsonProperty("_id") final Long id,
            @JsonProperty("name") final String name,
            @JsonProperty("type") final String type,
            @JsonProperty("geo_position") final GeoPosition geoPosition) {
        return new Position(id, name, type, geoPosition);
    }

    @Override
    public boolean equals(final Object o) {
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
