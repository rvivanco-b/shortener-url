package com.upwork.shortenerapi.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateShortenUrlRequest {

    private String url;

}
