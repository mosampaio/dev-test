package domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.stream.Stream;

import static java.util.Arrays.asList;

public final class GeoPosition {
    private final Double latitude;
    private final Double longitude;

    public GeoPosition(final Double latitude, final Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @JsonCreator
    public static GeoPosition jsonCreator(
            @JsonProperty("latitude") final Double latitude,
            @JsonProperty("longitude") final Double longitude) {
        return new GeoPosition(latitude, longitude);
    }

    Stream<String> fieldValues() {
        return asList(latitude.toString(), longitude.toString()).stream();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeoPosition that = (GeoPosition) o;

        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null)
            return false;
        return longitude != null ? longitude.equals(that.longitude) : that.longitude == null;

    }

    @Override
    public int hashCode() {
        int result = latitude != null ? latitude.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GeoPosition{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
