package io.codebetter.mufonhub.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.persistence.Cacheable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Cacheable
@MongoEntity(collection = "posts")
public class Post {

    public String title;
    public String content;
    public String author;
    public LocalDateTime creationDate;
    public List<Comment> comments;

}
