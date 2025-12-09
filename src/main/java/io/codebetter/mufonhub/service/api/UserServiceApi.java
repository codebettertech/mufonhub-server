package io.codebetter.mufonhub.service.api;

import io.codebetter.mufonhub.dto.AuthenticationLoginDto;
import io.codebetter.mufonhub.entity.User;
import io.smallrye.mutiny.Uni;

public interface UserServiceApi extends AbstractService<User> {

    Uni<User> findByUsernameAndPassword(AuthenticationLoginDto authenticationLoginDto);

}
