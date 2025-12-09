package io.codebetter.mufonhub.resource;

import io.codebetter.mufonhub.dto.AuthenticationLoginDto;
import io.codebetter.mufonhub.entity.User;
import io.codebetter.mufonhub.service.UserService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.net.URI;
import java.util.List;

@Path(UserResource.RESOURCE_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    static final String RESOURCE_PATH = "/users";
    static final String FIND_ALL_PATH = "/find-all";
    static final String CREATE_PATH = "/create";
    static final String INSERT_PATH = "/insert";
    static final String COUNT_PATH = "/count";
    static final String USERNAME_PASSWORD_PATH = "/username-password";


    @Inject
    UserService userService;

    @GET
    @Path(UserResource.FIND_ALL_PATH)
    public Uni<List<User>> listAll(){
        return userService.findAll();
    }

    @POST
    @Path(UserResource.CREATE_PATH)
    public Uni<User> addUser(User user) {
        return userService.create(user);
    }

    @POST
    @Path(UserResource.USERNAME_PASSWORD_PATH)
    public Uni<User> findByUsernameAndPassword(AuthenticationLoginDto  authenticationLoginDto) {
        return userService.findByUsernameAndPassword(authenticationLoginDto);
    }

    @GET
    @Path("/{id}")
    public Uni<User> get(String id) {
        return userService.getById(id);
    }
}
