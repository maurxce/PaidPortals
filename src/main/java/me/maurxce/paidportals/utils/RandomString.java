package me.maurxce.paidportals.utils;

import org.apache.commons.lang.RandomStringUtils;

public class RandomString {

    public static String generate() {
        return RandomStringUtils.random(32, true, true);
    }
}
