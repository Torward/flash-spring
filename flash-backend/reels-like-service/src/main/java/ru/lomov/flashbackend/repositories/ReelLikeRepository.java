package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.ReelLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReelLikeRepository extends JpaRepository<ReelLike, String> {

    // Check if user has liked a specific reel
    boolean existsByReelIdAndUserId(String reelId, String userId);

    // Find like by reel and user
    Optional<ReelLike> findByReelIdAndUserId(String reelId, String userId);

    // Get all likes for a specific reel
    List<ReelLike> findByReelId(String reelId);

    // Get all likes by a specific user
    List<ReelLike> findByUserId(String userId);

    // Count likes for a specific reel
    long countByReelId(String reelId);

    // Delete like by reel and user
    void deleteByReelIdAndUserId(String reelId, String userId);
}
