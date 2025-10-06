package ru.lomov.flashbackend.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, String> {
    List<MediaFile> findByUploadedByUserIdAndDeletedFalseOrderByCreatedAtDesc(Long userId);
    long countByUploadedByUserIdAndDeletedFalse(Long userId);
    Long sumFileSizeByUploadedByUserIdAndDeletedFalse(Long userId);
}
