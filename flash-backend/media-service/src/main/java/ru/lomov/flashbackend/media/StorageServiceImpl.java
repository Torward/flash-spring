package ru.lomov.flashbackend.media;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${storage.location:./uploads/media}")
    private String storageLocation;

    @Override
    public String store(MultipartFile file, String filename) throws IOException {
        // Create user directory if it doesn't exist
        Path userDir = Paths.get(storageLocation);
        if (!Files.exists(userDir)) {
            Files.createDirectories(userDir);
        }
        
        // Generate unique filename to prevent conflicts
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        Path storagePath = Paths.get(storageLocation, uniqueFilename);
        
        // Copy file to storage location
        Files.copy(file.getInputStream(), storagePath);
        return uniqueFilename;
    }

    @Override
    public byte[] downloadFile(Long fileId, Long userId) throws IOException {
        // For now, using filename as identifier since we don't have file entity yet
        // This will be updated when we implement proper file metadata storage
        Path filePath = Paths.get(storageLocation, String.valueOf(fileId));
        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            throw new IOException("File not found: " + filePath.toString());
        }
    }

    @Override
    public String getFileUrl(Long fileId, Long userId) {
        // Generate URL for accessing the file
        // This will be handled by a MediaController that serves files
        return "/api/media/files/" + fileId;
    }

    @Override
    public void delete(String filePath) throws IOException {
        Path path = Paths.get(storageLocation, filePath);
        Files.deleteIfExists(path);
    }
    
    /**
     * Helper method to get the full file path
     */
    public Path getFilePath(String filename) {
        return Paths.get(storageLocation, filename);
    }
}
