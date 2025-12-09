package io.codebetter.mufonhub.service;

import io.codebetter.mufonhub.dto.AuthenticationLoginDto;
import io.codebetter.mufonhub.dto.AuthenticationResponse;
import io.codebetter.mufonhub.entity.User;
import io.codebetter.mufonhub.model.Role;
import io.codebetter.mufonhub.repository.UserRepository;
import io.codebetter.mufonhub.security.PBKDF2Encoder;
import io.codebetter.mufonhub.service.api.AuthenticationServiceApi;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuthenticationService implements AuthenticationServiceApi {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class);

    public static final String[] SET_VALUES = new String[] {Role.USER.name()};
    public static final Set<String> ROLE_SET = new HashSet<>(Arrays.asList(SET_VALUES));

    @ConfigProperty(name = "io.codebetter.mufonhub.jwt.duration") public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer") public String issuer;

    @Inject
    UserRepository userRepository;

    @Inject
    PBKDF2Encoder passwordEncoder;

    @Override
    public AuthenticationResponse login(AuthenticationLoginDto authenticationLoginDto) {
        User user = userRepository.findByUsernameAndPassword(authenticationLoginDto.getUsername(), authenticationLoginDto.getPassword()).subscribe().asCompletionStage().join();
        LOGGER.info(user);
        if(user != null) {
            try {
                LOGGER.info("user is not null");
                return new AuthenticationResponse(TokenService.generateToken(user, user.roles, duration, issuer));
            } catch (Exception e) {
                throw new UnauthorizedException(e);
            }
        }else {
            throw new UnauthorizedException(Response.status(Response.Status.UNAUTHORIZED).toString());
        }
    }
}
