package com.upwork.shortenerapi.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateShortenUrlResponse {

    private final String slug;

    private final String longUrl;

    private final Long expireAt;

}
