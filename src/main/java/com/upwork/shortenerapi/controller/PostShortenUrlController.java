package com.upwork.shortenerapi.controller;

import com.upwork.shortenerapi.request.CreateShortenUrlRequest;
import com.upwork.shortenerapi.response.CreateShortenUrlResponse;
import com.upwork.shortenerapi.service.RegisterShortenUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/shorten")
public class PostShortenUrlController {

    private final RegisterShortenUrlService registerShortenUrlService;

    public PostShortenUrlController(RegisterShortenUrlService registerShortenUrlService) {
        this.registerShortenUrlService = registerShortenUrlService;
    }

    @PostMapping("")
    public ResponseEntity<CreateShortenUrlResponse> shortenUrl(@RequestBody CreateShortenUrlRequest request) {
        return ResponseEntity.ok(registerShortenUrlService.registerUrl(request));
    }

}
