package io.codebetter.mufonhub.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Cacheable;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Cacheable
@MongoEntity(collection = "users")
public class User extends ReactivePanacheMongoEntity {

    public String username;
    public String firstName;
    public String lastName;
    public String password;
    public String address;
    public String phone;
    public String avatarUrl;
    public LocalDate birthDate;
    public LocalDate createdDate;
    public String token;
    public Set<String> roles;

}
