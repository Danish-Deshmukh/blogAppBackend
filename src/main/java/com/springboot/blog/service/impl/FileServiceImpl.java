package com.springboot.blog.service.impl;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileServiceImpl implements FileService{
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get name of the file
        return null;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        return null;
    }
}
