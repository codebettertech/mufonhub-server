package io.codebetter.mufonhub;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.persistence.Persistence;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.reactive.mutiny.Mutiny;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AppLifeCycleBean {

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    @ConfigProperty(name = "io.codebetter.mufonhub.persistence.context.name") public String mufonhubPU;

    Mutiny.SessionFactory sessionFactory() {
        return Persistence
                .createEntityManagerFactory(mufonhubPU)
                .unwrap(Mutiny.SessionFactory.class);
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        LOGGER.info("Create Session Factory Mutiny...");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}
