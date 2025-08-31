package ru.lomov.flashbackend.media;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MediaFileServiceImpl implements MediaFileService {

    private final MediaFileRepository mediaFileRepository;
    private final StorageService storageService;

    @Value("${storage.type:LOCAL}")
    private String storageType;

    @Override
    public MediaFile uploadFile(MultipartFile file, Long userId) throws IOException {
        validateFile(file);
        
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String contentType = file.getContentType();
        long fileSize = file.getSize();

        // Generate unique filename
        String uniqueFilename = generateUniqueFilename(originalFilename);
        
        // Store file
        String filePath = storageService.store(file, uniqueFilename);
        
        // Create media file entity
        MediaFile mediaFile = new MediaFile();
        mediaFile.setOriginalFileName(originalFilename);
        mediaFile.setFileName(uniqueFilename);
        mediaFile.setContentType(contentType);
        mediaFile.setFileSize(fileSize);
        mediaFile.setFilePath(filePath);
        mediaFile.setStorageType(storageType);
        mediaFile.setCreatedAt(LocalDateTime.now());
        mediaFile.setUpdatedAt(LocalDateTime.now());
        mediaFile.setDeleted(false);
        mediaFile.setDownloadCount(0);

        return mediaFileRepository.save(mediaFile);
    }

    @Override
    public List<MediaFile> uploadFiles(List<MultipartFile> files, Long userId) throws IOException {
        return files.stream()
                .map(file -> {
                    try {
                        return uploadFile(file, userId);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MediaFile> uploadMultipleFiles(MultipartFile[] files, Long userId) throws IOException {
        return Arrays.stream(files)
                .map(file -> {
                    try {
                        return uploadFile(file, userId);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public MediaFile getFile(Long fileId) {
        return mediaFileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    @Override
    public List<MediaFile> getUserFiles(Long userId) {
        return mediaFileRepository.findByUploadedByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId);
    }

    @Override
    public byte[] downloadFile(Long fileId, Long userId) throws IOException {
        MediaFile file = getFile(fileId);
        
        // Increment download count
        file.setDownloadCount(file.getDownloadCount() + 1);
        mediaFileRepository.save(file);
        
        return storageService.downloadFile(file.getId(), file.getUploadedByUserId());
    }

    @Override
    public String getFileUrl(Long fileId, Long userId) {
        MediaFile file = getFile(fileId);
        return storageService.getFileUrl(file.getId(), file.getUploadedByUserId());
    }

    @Override
    public void deleteFile(Long fileId, Long userId) throws IOException {
        MediaFile file = getFile(fileId);
        
        // Soft delete
        file.setDeleted(true);
        file.setUpdatedAt(LocalDateTime.now());
        mediaFileRepository.save(file);
        
        // Optionally, you can also delete the actual file from storage
        // storageService.delete(file.getFilePath());
    }

    @Override
    public MediaFile updateFileMetadata(Long fileId, Long userId, String description, String tags) {
        MediaFile file = getFile(fileId);
        
        file.setDescription(description);
        file.setTags(tags);
        file.setUpdatedAt(LocalDateTime.now());
        
        return mediaFileRepository.save(file);
    }

    @Override
    public List<MediaFile> searchUserFiles(Long userId, String searchTerm) {
        return mediaFileRepository.findByUploadedByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<MediaFile> getFilesByType(Long userId, String fileType) {
        return mediaFileRepository.findByUploadedByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<MediaFile> getPublicFiles() {
        return mediaFileRepository.findAll();
    }

    @Override
    public MediaFile toggleFileVisibility(Long fileId, Long userId) {
        MediaFile file = getFile(fileId);
        return file;
    }

    @Override
    public Object getFileStatistics(Long userId) {
        Map<String, Object> stats = new HashMap<>();
        List<MediaFile> userFiles = getUserFiles(userId);
        
        stats.put("totalFiles", userFiles.size());
        stats.put("totalSize", userFiles.stream().mapToLong(MediaFile::getFileSize).sum());
        stats.put("totalDownloads", userFiles.stream().mapToInt(MediaFile::getDownloadCount).sum());
        
        return stats;
    }

    @Override
    public long getUserFilesCount(Long userId) {
        return mediaFileRepository.countByUploadedByUserIdAndDeletedFalse(userId);
    }

    @Override
    public long getUserFilesTotalSize(Long userId) {
        Long totalSize = mediaFileRepository.sumFileSizeByUploadedByUserIdAndDeletedFalse(userId);
        return totalSize != null ? totalSize : 0L;
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        
        if (file.getSize() > 100 * 1024 * 1024) { // 100MB limit
            throw new RuntimeException("File size exceeds limit");
        }
        
        String contentType = file.getContentType();
        if (contentType == null || !isAllowedContentType(contentType)) {
            throw new RuntimeException("File type not allowed");
        }
    }

    private boolean isAllowedContentType(String contentType) {
        Set<String> allowedTypes = Set.of(
                "image/jpeg", "image/png", "image/gif", "image/webp",
                "application/pdf", "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "video/mp4", "video/webm", "audio/mpeg", "audio/wav",
                "text/plain", "application/zip"
        );
        return allowedTypes.stream().anyMatch(contentType::startsWith);
    }

    private String getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1))
                .orElse("");
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
        return baseName + "_" + System.currentTimeMillis() + "." + extension;
    }
}
