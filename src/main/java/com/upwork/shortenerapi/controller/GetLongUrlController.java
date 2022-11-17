package com.upwork.shortenerapi.controller;


import com.upwork.shortenerapi.document.Url;
import com.upwork.shortenerapi.service.FindShortenUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/")
public class GetLongUrlController {

    private final FindShortenUrlService findShortenUrlService;

    public GetLongUrlController(FindShortenUrlService findShortenUrlService) {
        this.findShortenUrlService = findShortenUrlService;
    }

    @GetMapping("{identifier}")
    public ResponseEntity<Void> getLongUrl(@PathVariable("identifier") String identifier) {
        Url url = findShortenUrlService.findShortenUrl(identifier);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getLongUrl())).build();
    }

}
