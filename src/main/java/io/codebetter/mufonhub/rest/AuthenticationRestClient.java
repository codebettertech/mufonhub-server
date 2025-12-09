package io.codebetter.mufonhub.rest;

import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path(AuthenticationRestClient.RESOURCE_PATH)
@RegisterRestClient(configKey = "auth-api")
public interface AuthenticationRestClient {

    String RESOURCE_PATH = "/rest/auth";
}
