package ru.schoolbolt.msbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jaxrs.Jaxrs2TypesModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import ru.schoolbolt.msbackend.data.Database;
import ru.schoolbolt.msbackend.server.CorsFilter;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(final String[] args) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jaxrs2TypesModule());
        mapper.registerModule(new JavaTimeModule());

        final JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);

        final ResourceConfig config = new ResourceConfig();
        config.packages("ru.schoolbolt.msbackend.endpoint");
        config.register(provider);
        config.register(MultiPartFeature.class);
        config.register(CorsFilter.class);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new Database()).to(Database.class);
            }
        });

        final ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create("http://0.0.0.0:5000"),
                config,
                locator
        );

        try {
            server.start();

            Thread.currentThread().join();
        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
