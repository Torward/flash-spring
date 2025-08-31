package ru.lomov.flash.live.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.live.entity.LiveStream;
import ru.lomov.flash.live.repository.LiveStreamRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LiveServiceImpl implements LiveService {

    private final LiveStreamRepository liveStreamRepository;

    @Override
    public LiveStream startLive(LiveStream liveStream) {
        liveStream.setRoomId(generateRoomId());
        liveStream.setStartTime(LocalDateTime.now());
        liveStream.setIsActive(true);
        liveStream.setViewerCount(0);
        return liveStreamRepository.save(liveStream);
    }

    @Override
    public Optional<LiveStream> getLiveByRoomId(String roomId) {
        return liveStreamRepository.findByRoomId(roomId);
    }

    @Override
    public List<LiveStream> getUserLiveStreams(String userId) {
        return liveStreamRepository.findByUserId(userId);
    }

    @Override
    public List<LiveStream> getActiveLiveStreams() {
        return liveStreamRepository.findByIsActiveTrue();
    }

    @Override
    public void endLive(String roomId) {
        liveStreamRepository.findByRoomId(roomId).ifPresent(liveStream -> {
            liveStream.setIsActive(false);
            liveStream.setEndTime(LocalDateTime.now());
            liveStreamRepository.save(liveStream);
        });
    }

    @Override
    public LiveStream addParticipant(String roomId, String userId) {
        return liveStreamRepository.findByRoomId(roomId).map(liveStream -> {
            if (!liveStream.getParticipants().contains(userId)) {
                liveStream.getParticipants().add(userId);
                return liveStreamRepository.save(liveStream);
            }
            return liveStream;
        }).orElse(null);
    }

    @Override
    public LiveStream removeParticipant(String roomId, String userId) {
        return liveStreamRepository.findByRoomId(roomId).map(liveStream -> {
            liveStream.getParticipants().remove(userId);
            return liveStreamRepository.save(liveStream);
        }).orElse(null);
    }

    @Override
    public LiveStream addChatMessage(String roomId, String message) {
        return liveStreamRepository.findByRoomId(roomId).map(liveStream -> {
            liveStream.getChats().add(message);
            return liveStreamRepository.save(liveStream);
        }).orElse(null);
    }

    @Override
    public LiveStream incrementViewerCount(String roomId) {
        return liveStreamRepository.findByRoomId(roomId).map(liveStream -> {
            liveStream.setViewerCount(liveStream.getViewerCount() + 1);
            return liveStreamRepository.save(liveStream);
        }).orElse(null);
    }

    private String generateRoomId() {
        String roomId;
        do {
            roomId = "live_" + UUID.randomUUID().toString().substring(0, 8);
        } while (liveStreamRepository.existsByRoomId(roomId));
        return roomId;
    }
}
