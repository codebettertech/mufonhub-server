package io.codebetter.mufonhub.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Cacheable;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Cacheable
@MongoEntity(collection = "comments")
public class Comment {

    public String title;
    public String content;
    public LocalDateTime creationDate;
    @JsonbTransient
    public String postId;
}
