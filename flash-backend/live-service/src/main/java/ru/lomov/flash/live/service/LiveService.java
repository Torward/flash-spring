package ru.lomov.flash.live.service;

import ru.lomov.flash.live.entity.LiveStream;

import java.util.List;
import java.util.Optional;

public interface LiveService {
    LiveStream startLive(LiveStream liveStream);
    Optional<LiveStream> getLiveByRoomId(String roomId);
    List<LiveStream> getUserLiveStreams(String userId);
    List<LiveStream> getActiveLiveStreams();
    void endLive(String roomId);
    LiveStream addParticipant(String roomId, String userId);
    LiveStream removeParticipant(String roomId, String userId);
    LiveStream addChatMessage(String roomId, String message);
    LiveStream incrementViewerCount(String roomId);
}
