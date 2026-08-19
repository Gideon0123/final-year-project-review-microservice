package com.example.REVIEW_SERVICE.utils;

import java.util.UUID;

public class TraceIdUtil {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}

//.\minio.exe server C:\minio\data --console-address ":9001"
//$env:MINIO_ROOT_USER="minioadmin"
//$env:MINIO_ROOT_PASSWORD="minioadmin123"