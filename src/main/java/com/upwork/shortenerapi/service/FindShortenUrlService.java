package com.upwork.shortenerapi.service;

import com.upwork.shortenerapi.document.Url;
import com.upwork.shortenerapi.exception.ShortenUrlDoesntExistsException;
import com.upwork.shortenerapi.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class FindShortenUrlService {

    private final UrlRepository urlRepository;

    public FindShortenUrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url findShortenUrl(String slug) {
        Optional<Url> urlOpt = urlRepository.findBySlug(slug);

        if (urlOpt.isEmpty()) {
            throw new ShortenUrlDoesntExistsException();
        }

        deleteShortenUrlIfExpires(urlOpt.get());
        return urlOpt.get();
    }

    private void deleteShortenUrlIfExpires(Url url) {
        long now = Instant.now(Clock.system(ZoneId.of("UTC"))).getEpochSecond();
        if (now >= url.getExpireAt()) {
            urlRepository.delete(url);
        }
    }

}
