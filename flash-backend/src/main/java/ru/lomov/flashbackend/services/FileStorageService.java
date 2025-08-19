package ru.lomov.flashbackend.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String save(MultipartFile file) throws IOException;
}
