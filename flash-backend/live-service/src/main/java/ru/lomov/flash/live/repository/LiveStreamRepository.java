package ru.lomov.flash.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.live.entity.LiveStream;

import java.util.List;
import java.util.Optional;

@Repository
public interface LiveStreamRepository extends JpaRepository<LiveStream, String> {
    Optional<LiveStream> findByRoomId(String roomId);
    List<LiveStream> findByUserId(String userId);
    List<LiveStream> findByIsActiveTrue();
    List<LiveStream> findByUserIdAndIsActiveTrue(String userId);
    boolean existsByRoomId(String roomId);
}
