package application;

import application.service.ExportPositions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import infrastructure.guice.GoEuroModule;
import org.slf4j.Logger;

import java.nio.file.Path;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

public final class ConsoleApp {

    private static final Logger LOG = getLogger(ConsoleApp.class);

    public static void main(final String[] args) {
        new ConsoleApp().run(args);
    }

    public void run(final String[] args) {
        if (args.length == 0) {
            LOG.info("Error: An argument must be passed.");
            System.exit(-1);
        }

        Injector injector = Guice.createInjector(new GoEuroModule());
        ExportPositions exportPositions = injector.getInstance(ExportPositions.class);

        String cityName = args[0];
        LOG.info(format("Searching by %s", cityName));
        try {
            Path result = exportPositions.search(cityName);
            LOG.info(format("Search executed successfully. Check it out at %s", result));
        } catch (Exception e) {
            LOG.warn(format("Error occurred: %s", e.getMessage()));
            LOG.error(e.getMessage(), e);
            System.exit(-1);
        }

    }

}
