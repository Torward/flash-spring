package ru.lomov.flash.podcast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.podcast.entity.Podcast;
import ru.lomov.flash.podcast.repository.PodcastRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PodcastServiceImpl implements PodcastService {

    private final PodcastRepository podcastRepository;

    @Override
    public Podcast startPodcast(Podcast podcast) {
        podcast.setRoomId(generateRoomId());
        podcast.setStartTime(LocalDateTime.now());
        podcast.setIsActive(true);
        podcast.setListenerCount(0);
        return podcastRepository.save(podcast);
    }

    @Override
    public Optional<Podcast> getPodcastByRoomId(String roomId) {
        return podcastRepository.findByRoomId(roomId);
    }

    @Override
    public List<Podcast> getUserPodcasts(String userId) {
        return podcastRepository.findByUserId(userId);
    }

    @Override
    public List<Podcast> getActivePodcasts() {
        return podcastRepository.findByIsActiveTrue();
    }

    @Override
    public void endPodcast(String roomId) {
        podcastRepository.findByRoomId(roomId).ifPresent(podcast -> {
            podcast.setIsActive(false);
            podcast.setEndTime(LocalDateTime.now());
            podcastRepository.save(podcast);
        });
    }

    @Override
    public Podcast addParticipant(String roomId, String userId) {
        return podcastRepository.findByRoomId(roomId).map(podcast -> {
            if (!podcast.getParticipants().contains(userId)) {
                podcast.getParticipants().add(userId);
                return podcastRepository.save(podcast);
            }
            return podcast;
        }).orElse(null);
    }

    @Override
    public Podcast removeParticipant(String roomId, String userId) {
        return podcastRepository.findByRoomId(roomId).map(podcast -> {
            podcast.getParticipants().remove(userId);
            return podcastRepository.save(podcast);
        }).orElse(null);
    }

    @Override
    public Podcast addChatMessage(String roomId, String message) {
        return podcastRepository.findByRoomId(roomId).map(podcast -> {
            podcast.getChats().add(message);
            return podcastRepository.save(podcast);
        }).orElse(null);
    }

    @Override
    public Podcast incrementListenerCount(String roomId) {
        return podcastRepository.findByRoomId(roomId).map(podcast -> {
            podcast.setListenerCount(podcast.getListenerCount() + 1);
            return podcastRepository.save(podcast);
        }).orElse(null);
    }

    @Override
    public List<Podcast> getPodcastsByCategory(String category) {
        return podcastRepository.findByCategory(category);
    }

    private String generateRoomId() {
        String roomId;
        do {
            roomId = "podcast_" + UUID.randomUUID().toString().substring(0, 8);
        } while (podcastRepository.existsByRoomId(roomId));
        return roomId;
    }
}
