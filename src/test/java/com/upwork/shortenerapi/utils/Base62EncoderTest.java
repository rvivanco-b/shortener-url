package com.upwork.shortenerapi.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class Base62EncoderTest {

    @ParameterizedTest
    @CsvSource(value = {
            "1000000000, 15FTGg"
    })
    void should_encode(String number, String expected) {
        String result = Base62Encoder.encode(new BigInteger(number));
        assertEquals(expected, result);
        assertEquals(6, result.length());
    }
}