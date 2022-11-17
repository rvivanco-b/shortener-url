package com.upwork.shortenerapi.service;

import com.upwork.shortenerapi.document.Url;
import com.upwork.shortenerapi.exception.ShortenUrlDoesntExistsException;
import com.upwork.shortenerapi.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindShortenUrlServiceTest {
    @Mock
    private UrlRepository urlRepository;
    private FindShortenUrlService findShortenUrlService;

    @BeforeEach
    public void setup() {
        findShortenUrlService = new FindShortenUrlService(urlRepository);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6ty786, https://www.google.com",
    })
    void should_find_shorten_url(String urlShorten, String expected) {

        Url url = Url.builder()
                .longUrl("https://www.google.com")
                .slug("6ty786")
                .expireAt(Instant.now(Clock.system(ZoneId.of("UTC"))).getEpochSecond())
                .build();

        when(urlRepository.findBySlug(urlShorten)).thenReturn(Optional.of(url));

        Url result = findShortenUrlService.findShortenUrl(urlShorten);

        assertEquals(expected, result.getLongUrl());

    }

    @ParameterizedTest
    @CsvSource(value = {
            "6ty786, https://www.google.com",
    })
    void should_thrown_exception_when_shorten_url_doesnt_exists(String slug) throws ShortenUrlDoesntExistsException {
        when(urlRepository.findBySlug(slug)).thenReturn(Optional.empty());
        assertThrows(ShortenUrlDoesntExistsException.class, () -> findShortenUrlService.findShortenUrl(slug));
    }

}