package ru.lomov.flashbackend.media;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String store(MultipartFile file, String filename) throws IOException;
    byte[] downloadFile(Long fileId, Long userId) throws IOException;
    String getFileUrl(Long fileId, Long userId);
    void delete(String filePath) throws IOException;
}
