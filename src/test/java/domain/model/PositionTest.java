package domain.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PositionTest {

    @Test
    public void shouldFormatAsCSV() {
        Position position = new Position(376217L, "Berlin", "location", new GeoPosition(52.52437, 13.41053));
        String csvLine = position.toCSV();

        assertThat(csvLine, is("\"376217\",\"Berlin\",\"location\",\"52.52437\",\"13.41053\""));
    }
}