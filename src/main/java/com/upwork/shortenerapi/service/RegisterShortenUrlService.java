package com.upwork.shortenerapi.service;

import com.upwork.shortenerapi.domain.CounterRange;
import com.upwork.shortenerapi.document.Url;
import com.upwork.shortenerapi.repository.UrlRepository;
import com.upwork.shortenerapi.request.CreateShortenUrlRequest;
import com.upwork.shortenerapi.response.CreateShortenUrlResponse;
import com.upwork.shortenerapi.utils.Base62Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Service
@Slf4j
public class RegisterShortenUrlService {
    private final UrlRepository urlRepository;

    private final CounterRange counterRange;

    private final String lifeTimeSeconds;

    public RegisterShortenUrlService(UrlRepository urlRepository,
                                     CounterRange counterRange,
                                     @Value("${shortener.urls.lifeTimeSeconds}") String lifeTimeSeconds) {
        this.urlRepository = urlRepository;
        this.counterRange = counterRange;
        this.lifeTimeSeconds = lifeTimeSeconds;
    }

    public CreateShortenUrlResponse registerUrl(CreateShortenUrlRequest request) {
        BigInteger counter = counterRange.increaseAndGetCurrent();
        String counterEncoded = Base62Encoder.encode(counter);
        long now = Instant.now(Clock.system(ZoneId.of("UTC"))).getEpochSecond();
        long expiredAt = now + Long.parseLong(lifeTimeSeconds);
        Url shortenUrl = Url.builder()
                .longUrl(request.getUrl())
                .slug(counterEncoded)
                .expireAt(expiredAt)
                .build();
        log.info("ShortenUrlService#registerUrl - Shorten url to save. slug={}", counterEncoded);
        urlRepository.save(shortenUrl);
        return CreateShortenUrlResponse.builder()
                .longUrl(request.getUrl())
                .slug(counterEncoded)
                .expireAt(expiredAt)
                .build();
    }

}
