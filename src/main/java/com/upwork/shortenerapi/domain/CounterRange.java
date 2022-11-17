package com.upwork.shortenerapi.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@Builder
public final class CounterRange {

    private final BigInteger start;

    private BigInteger current;

    private final BigInteger end;

    public BigInteger increaseAndGetCurrent() {
        current = current.add(BigInteger.ONE);
        return current;
    }

}
