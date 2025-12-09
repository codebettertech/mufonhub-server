package io.codebetter.mufonhub.resource;

import io.codebetter.mufonhub.dto.AuthenticationLoginDto;
import io.codebetter.mufonhub.dto.AuthenticationResponse;
import io.codebetter.mufonhub.service.AuthenticationService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(AuthenticationResource.RESOURCE_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    static final String RESOURCE_PATH = "/auth";
    static final String LOGIN_PATH = "/login";
    static final String REGISTER_PATH = "/register";

    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path(AuthenticationResource.LOGIN_PATH)
    public AuthenticationResponse login(AuthenticationLoginDto authenticationLoginDto) {
        return authenticationService.login(authenticationLoginDto);
    }
}
