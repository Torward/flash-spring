package ru.lomov.flash.podcast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.podcast.entity.Podcast;

import java.util.List;
import java.util.Optional;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, String> {
    Optional<Podcast> findByRoomId(String roomId);
    List<Podcast> findByUserId(String userId);
    List<Podcast> findByIsActiveTrue();
    List<Podcast> findByUserIdAndIsActiveTrue(String userId);
    boolean existsByRoomId(String roomId);
    List<Podcast> findByCategory(String category);
}
