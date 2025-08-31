package ru.lomov.flashbackend.media;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MediaFileController {

    private final MediaFileService mediaFileService;

    public MediaFileController(MediaFileService mediaFileService) {
        this.mediaFileService = mediaFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<MediaFile> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws IOException {
        
        MediaFile savedFile = mediaFileService.uploadFile(file, userId);
        return ResponseEntity.ok(savedFile);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<MediaFile> getFile(
            @PathVariable Long fileId) {
        
        MediaFile file = mediaFileService.getFile(fileId);
        return ResponseEntity.ok(file);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MediaFile>> getUserFiles(
            @PathVariable Long userId) {
        
        List<MediaFile> files = mediaFileService.getUserFiles(userId);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long fileId,
            @RequestParam("userId") Long userId) throws IOException {
        
        byte[] fileData = mediaFileService.downloadFile(fileId, userId);
        
        MediaFile file = mediaFileService.getFile(fileId);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + file.getOriginalFileName() + "\"")
                .body(new org.springframework.core.io.ByteArrayResource(fileData));
    }

    @GetMapping("/url/{fileId}")
    public ResponseEntity<String> getFileUrl(
            @PathVariable Long fileId,
            @RequestParam("userId") Long userId) {
        
        String url = mediaFileService.getFileUrl(fileId, userId);
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long fileId,
            @RequestParam("userId") Long userId) throws IOException {
        
        mediaFileService.deleteFile(fileId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<MediaFile>> searchFiles(
            @RequestParam String searchTerm,
            @RequestParam("userId") Long userId) {
        
        List<MediaFile> files = mediaFileService.searchUserFiles(userId, searchTerm);
        return ResponseEntity.ok(files);
    }
}
