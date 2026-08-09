package com.example.REVIEW_SERVICE.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

@Getter
@Builder
public class AttachmentDownload {

    private InputStream inputStream;

    private String filename;

    private String contentType;

    private Long fileSize;
}
