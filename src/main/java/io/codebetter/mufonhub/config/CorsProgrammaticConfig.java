package io.codebetter.mufonhub.config;

import io.quarkus.vertx.http.security.CORS;
import io.quarkus.vertx.http.security.HttpSecurity;
import jakarta.enterprise.event.Observes;

public class CorsProgrammaticConfig {
    void configure(@Observes HttpSecurity httpSecurity) {
        httpSecurity.cors(CORS.builder()
                .origin("http://localhost:5173")
                .build());
    }
}
