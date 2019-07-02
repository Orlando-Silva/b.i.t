package com.app.bit.Helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelpers {

    public static byte[] Generate(String phrase,String algorithm)
    {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(phrase.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
