package ru.lomov.flash.podcast.service;

import ru.lomov.flash.podcast.entity.Podcast;

import java.util.List;
import java.util.Optional;

public interface PodcastService {
    Podcast startPodcast(Podcast podcast);
    Optional<Podcast> getPodcastByRoomId(String roomId);
    List<Podcast> getUserPodcasts(String userId);
    List<Podcast> getActivePodcasts();
    void endPodcast(String roomId);
    Podcast addParticipant(String roomId, String userId);
    Podcast removeParticipant(String roomId, String userId);
    Podcast addChatMessage(String roomId, String message);
    Podcast incrementListenerCount(String roomId);
    List<Podcast> getPodcastsByCategory(String category);
}
