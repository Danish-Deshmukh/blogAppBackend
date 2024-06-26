package com.springboot.blog.controller;

import com.springboot.blog.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException {
        String uploadedFileName = fileService.uploadFile(path, file);
        return ResponseEntity.ok("File uploaded : " + uploadedFileName);
    }

//    @GetMapping(value = "/{fileName}")
//    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        InputStream resourceFile = fileService.getResourceFile(path, fileName);
//        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//        StreamUtils.copy(resourceFile, response.getOutputStream());
//    }

    @GetMapping(value = "/image/{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resourceFile = fileService.getResourceFile(path, fileName);

        // Determine the file's extension and set the appropriate content type
        String fileExtension = getFileExtension(fileName);
        switch (fileExtension.toLowerCase()) {
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
            case "jpeg":
            case "jpg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }

        StreamUtils.copy(resourceFile, response.getOutputStream());
    }

    // Helper method to get the file extension
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
