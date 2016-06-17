package domain.model;

public final class Position {
    private final String id;
    private final String name;
    private final String type;
    private final GeoPosition geoPosition;

    public Position(String id, String name, String type, GeoPosition geoPosition) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.geoPosition = geoPosition;
    }

    public String getId() {
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
}
