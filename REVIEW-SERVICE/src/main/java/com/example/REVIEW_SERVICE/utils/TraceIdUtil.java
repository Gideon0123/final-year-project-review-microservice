package com.example.REVIEW_SERVICE.utils;

import java.util.UUID;

public class TraceIdUtil {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}