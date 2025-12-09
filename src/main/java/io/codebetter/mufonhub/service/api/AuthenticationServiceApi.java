package io.codebetter.mufonhub.service.api;

import io.codebetter.mufonhub.dto.AuthenticationLoginDto;
import io.codebetter.mufonhub.dto.AuthenticationResponse;
import jakarta.ws.rs.core.Response;

public interface AuthenticationServiceApi {

    AuthenticationResponse login(AuthenticationLoginDto authenticationLoginDto);
}
