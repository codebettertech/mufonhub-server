package io.codebetter.mufonhub;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;

@QuarkusMain
public class MufonHubApplication {
    public static void main(String... args) {
        Quarkus.run(MufonHubRunnerApplication.class, (exitCode, exception) -> {}, args);
    }

    public static class MufonHubRunnerApplication implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            System.out.println("Do startup logic here");
            Quarkus.waitForExit();
            return 0;
        }
    }
}
