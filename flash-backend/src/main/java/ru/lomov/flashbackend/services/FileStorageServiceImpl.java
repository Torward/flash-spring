package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService{

    private final Path root = Paths.get("/uploads");
    @Override
    public String save(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.root.resolve(fileName));
        return fileName;
    }
}
