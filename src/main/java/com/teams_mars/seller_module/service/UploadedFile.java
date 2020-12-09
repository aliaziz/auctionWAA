package com.teams_mars.seller_module.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class UploadedFile implements Serializable {
    private static final long serialVersionUID = 1L;
    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }
    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
