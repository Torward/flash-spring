package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.ReelView;

import java.util.List;

@Repository
public interface ReelViewRepository extends JpaRepository<ReelView, String> {

    // Check if user has viewed a specific reel
    boolean existsByReelIdAndUserId(String reelId, String userId);

    // Find view by reel and user
    ReelView findByReelIdAndUserId(String reelId, String userId);

    // Get all views for a specific reel
    List<ReelView> findByReelId(String reelId);

    // Get all views by a specific user
    List<ReelView> findByUserId(String userId);

    // Count views for a specific reel
    long countByReelId(String reelId);

    // Get average view duration for a reel
    @Query("SELECT AVG(rv.viewDuration) FROM ReelView rv WHERE rv.reelId = :reelId AND rv.viewDuration IS NOT NULL")
    Double getAverageViewDurationByReelId(@Param("reelId") String reelId);

    // Get total view time for a reel
    @Query("SELECT SUM(rv.viewDuration) FROM ReelView rv WHERE rv.reelId = :reelId AND rv.viewDuration IS NOT NULL")
    Long getTotalViewTimeByReelId(@Param("reelId") String reelId);
}
