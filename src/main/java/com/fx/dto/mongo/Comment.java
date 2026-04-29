package com.fx.dto.mongo;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("comments")
public class Comment {

    @Id
    private String id;

    @Field
    private String commentAuthor;

    @Field
    private String message;
}
