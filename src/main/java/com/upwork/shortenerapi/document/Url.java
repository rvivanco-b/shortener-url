package com.upwork.shortenerapi.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document("urls")
public class Url {

    @Id
    private String id;

    private String slug;

    private String longUrl;

    private Long expireAt;

}
