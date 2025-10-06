package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.SavedReel;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedReelRepository extends JpaRepository<SavedReel, String> {

    // Check if user has saved a specific reel
    boolean existsByUserIdAndReelId(String userId, String reelId);

    // Find saved reel by user and reel
    Optional<SavedReel> findByUserIdAndReelId(String userId, String reelId);

    // Get all saved reels for a specific user
    List<SavedReel> findByUserId(String userId);

    // Get all users who saved a specific reel
    List<SavedReel> findByReelId(String reelId);

    // Count saves for a specific reel
    long countByReelId(String reelId);

    // Count saves by a specific user
    long countByUserId(String userId);

    // Delete saved reel by user and reel
    void deleteByUserIdAndReelId(String userId, String reelId);
}
