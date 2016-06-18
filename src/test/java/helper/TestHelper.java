package helper;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import infrastructure.repositories.GoEuroRestClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;

public class TestHelper {

    public static Path pathFromResource(String resource) {
        try {
            return Paths.get(
                    TestHelper.class.getResource(resource).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String content(Path path) {
        try {
            return new String(readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GoEuroRestClient buildGoEuroRestClient(int port) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(GoEuroRestClient.class, format("http://localhost:%s", port));
    }

}
