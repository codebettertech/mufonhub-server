package io.codebetter.mufonhub.repository;

import io.codebetter.mufonhub.dto.UserDto;
import io.codebetter.mufonhub.entity.User;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class UserRepository implements ReactivePanacheMongoRepository<User> {

    public Uni<List<User>> getAll() {
        return User.listAll();
    }

    public Uni<User> findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public Uni<User> findByUsernameAndPassword(String username, String password) {
        return  find("username = ?1 and password = ?2", username, password).firstResult();
    }



    public Uni<User> updateUser(String id, UserDto userDto) {
        Uni<User> userUni = User.findById(new ObjectId(id));
        return userUni
                .onItem().transform(u -> {
                    u.firstName = userDto.getFirstName();
                    u.lastName = userDto.getLastName();
                    u.address = userDto.getAddress();
                    u.avatarUrl = userDto.getAvatarUrl();
                    u.phone = userDto.getPhone();
                    u.birthDate = userDto.getBirthDate();
                    u.createdDate = LocalDate.now();
                    u.avatarUrl = userDto.getAvatarUrl();
                    u.roles = userDto.getRoles();
                    u.username = userDto.getUsername();
                    return u;
                }).call(userUpdated -> userUpdated.persistOrUpdate());
    }
}
