package io.codebetter.mufonhub.service;

import io.codebetter.mufonhub.dto.AuthenticationLoginDto;
import io.codebetter.mufonhub.dto.UserDto;
import io.codebetter.mufonhub.entity.User;
import io.codebetter.mufonhub.repository.UserRepository;
import io.codebetter.mufonhub.security.PBKDF2Encoder;
import io.codebetter.mufonhub.service.api.UserServiceApi;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.List;

@ApplicationScoped
public class UserService implements UserServiceApi {

    @Inject
    UserRepository userRepository;

    @Inject
    PBKDF2Encoder passwordEncoder;


    @Override
    public Uni<List<User>> findAll() {
        return userRepository.getAll();
    }

    @Override
    public Uni<User> create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.persist(user).map(v -> v);
    }

    @Override
    public Uni<User> update(String id, Object dto) {
        return Panache.withTransaction(() -> userRepository.updateUser(id,(UserDto) dto));
    }

    @Override
    public Uni<Void> delete(String id) {
        Uni<User> userUni = getById(String.valueOf(new ObjectId(id)));
        return userUni.onItem().transform(u -> u.delete()).replaceWithVoid();
    }

    @Override
    public Uni<User> getById(String id) {
        return userRepository.findById(new ObjectId(id));
    }

    @Override
    public Uni<Long> count() {
        return userRepository.count();
    }

    @Override
    public Uni<User> findByUsernameAndPassword(AuthenticationLoginDto authenticationLoginDto) {
        return userRepository.findByUsernameAndPassword(authenticationLoginDto.getUsername(), authenticationLoginDto.getPassword());
    }
}
