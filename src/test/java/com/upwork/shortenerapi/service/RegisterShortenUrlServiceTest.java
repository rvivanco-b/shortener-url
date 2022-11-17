package com.upwork.shortenerapi.service;

import com.upwork.shortenerapi.domain.CounterRange;
import com.upwork.shortenerapi.repository.UrlRepository;
import com.upwork.shortenerapi.request.CreateShortenUrlRequest;
import com.upwork.shortenerapi.response.CreateShortenUrlResponse;
import com.upwork.shortenerapi.utils.Base62Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterShortenUrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    private CounterRange counterRange;

    private String lifeTimeSeconds;

    private RegisterShortenUrlService registerShortenUrlService;

    @BeforeEach
    public void setup() {
        counterRange = CounterRange.builder()
                .start(BigInteger.valueOf(1000000000L))
                .current(BigInteger.valueOf(1000000000L))
                .end(BigInteger.valueOf(2000000000L))
                .build();
        lifeTimeSeconds = "10";
        registerShortenUrlService = new RegisterShortenUrlService(urlRepository, counterRange, lifeTimeSeconds);
    }

    @Test
    void should_registerShortenUrl() {
        CreateShortenUrlRequest request = CreateShortenUrlRequest.builder()
                .url("https://www.google.com")
                .build();
        String slug = Base62Encoder.encode(counterRange.increaseAndGetCurrent());
        CreateShortenUrlResponse createShortenUrlResponse = registerShortenUrlService.registerUrl(request);
        assertEquals(request.getUrl(), createShortenUrlResponse.getLongUrl());
        assertEquals("15FTGh", slug);
        verify(urlRepository, times(1)).save(any());
    }
}