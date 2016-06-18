package application;

import application.service.SearchService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import infrastructure.guice.GoEuroModule;

import java.nio.file.Path;

import static java.lang.String.format;

public class ConsoleApp {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SearchService.class);

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: An argument must be passed.");
            System.exit(-1);
        }

        Injector injector = Guice.createInjector(new GoEuroModule());
        SearchService searchService = injector.getInstance(SearchService.class);
        Path result = searchService.search(args[0]);

        System.out.println(format("Search executed successfully. Check it out at %s", result));
    }

}
