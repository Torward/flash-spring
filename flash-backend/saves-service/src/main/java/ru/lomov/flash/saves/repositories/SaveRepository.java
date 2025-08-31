package ru.lomov.flash.saves.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lomov.flash.saves.entities.Save;

import java.util.List;
import java.util.Optional;

public interface SaveRepository extends JpaRepository<Save, String> {
    
    Optional<Save> findByPostIdAndUserId(String postId, String userId);
    
    List<Save> findByPostId(String postId);
    
    List<Save> findByUserId(String userId);
    
    long countByPostId(String postId);
    
    long countByUserId(String userId);
    
    boolean existsByPostIdAndUserId(String postId, String userId);
    
    @Query("SELECT s FROM Save s WHERE s.postId = :postId AND s.userId = :userId AND s.isActive = true")
    Optional<Save> findActiveSave(@Param("postId") String postId, @Param("userId") String userId);
    
    @Query("SELECT COUNT(s) FROM Save s WHERE s.postId = :postId AND s.isActive = true")
    long countActiveSavesByPostId(@Param("postId") String postId);
    
    @Query("SELECT COUNT(s) FROM Save s WHERE s.userId = :userId AND s.isActive = true")
    long countActiveSavesByUserId(@Param("userId") String userId);
    
    @Query("SELECT s FROM Save s WHERE s.postId = :postId AND s.isActive = true")
    List<Save> findActiveSavesByPostId(@Param("postId") String postId);
    
    @Query("SELECT s FROM Save s WHERE s.userId = :userId AND s.isActive = true")
    List<Save> findActiveSavesByUserId(@Param("userId") String userId);
    
    @Query("SELECT s FROM Save s WHERE s.postId = :postId AND s.userId = :userId AND s.isActive = true")
    boolean isPostSaved(@Param("postId") String postId, @Param("userId") String userId);
}
