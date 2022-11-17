package com.upwork.shortenerapi.utils;

import java.math.BigInteger;

public class Base62Encoder {

    public static String encode(BigInteger n) {
        int base = 62;
        String charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder encode = new StringBuilder(1);
         while (n.compareTo(BigInteger.ZERO) > 0) {
             encode.insert(0, charset.charAt(n.remainder(BigInteger.valueOf(base)).intValue()));
             n = n.divide(BigInteger.valueOf(base));
         }
         return encode.toString();
    }

}
