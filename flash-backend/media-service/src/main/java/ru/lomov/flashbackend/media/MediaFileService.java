package ru.lomov.flashbackend.media;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MediaFileService {
    MediaFile uploadFile(MultipartFile file, Long userId) throws IOException;
    List<MediaFile> uploadFiles(List<MultipartFile> files, Long userId) throws IOException;
    List<MediaFile> uploadMultipleFiles(MultipartFile[] files, Long userId) throws IOException;
    MediaFile getFile(Long fileId);
    List<MediaFile> getUserFiles(Long userId);
    byte[] downloadFile(Long fileId, Long userId) throws IOException;
    String getFileUrl(Long fileId, Long userId);
    void deleteFile(Long fileId, Long userId) throws IOException;
    MediaFile updateFileMetadata(Long fileId, Long userId, String description, String tags);
    List<MediaFile> searchUserFiles(Long userId, String searchTerm);
    List<MediaFile> getFilesByType(Long userId, String fileType);
    List<MediaFile> getPublicFiles();
    MediaFile toggleFileVisibility(Long fileId, Long userId);
    Object getFileStatistics(Long userId);
    long getUserFilesCount(Long userId);
    long getUserFilesTotalSize(Long userId);
}
