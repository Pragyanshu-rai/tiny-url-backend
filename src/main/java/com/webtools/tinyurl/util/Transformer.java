package com.webtools.tinyurl.util;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.security.MessageDigest;

public class Transformer {

    private Transformer() {

    }

    public static String generateHash(final String target, boolean needsSalt, boolean shouldBeShort) throws NoSuchAlgorithmException {
        byte[] salt = new byte[16];
        StringBuilder code = new StringBuilder();
        MessageDigest hashAlgo = MessageDigest.getInstance("SHA-256");

        if(needsSalt) {
            new Random().nextBytes(salt);
            hashAlgo.update(salt);
        }
        byte[] encodedBytes = hashAlgo.digest(target.getBytes(StandardCharsets.UTF_8));

        for(int current = 0; current < encodedBytes.length; current++) {
            code.append(Integer.toString(encodedBytes[current] & 0xff + 0x100, 16).substring(1));
        }
        return shortenHash(code.toString(), (shouldBeShort ? 12 : code.length()));
    }

    private static String shortenHash(String target, int length) {
        return target.substring(0, length);
    }
}
