package io.codebetter.mufonhub.dto;

import jakarta.persistence.Cacheable;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    public String username;
    public String firstName;
    public String lastName;
    public String address;
    public String phone;
    public String avatarUrl;
    public LocalDate birthDate;
    public LocalDate createdDate;
    public Set<String> roles;
}
