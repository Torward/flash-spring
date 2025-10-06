package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.ReelView;
import java.util.List;

public interface ReelViewService {
    ReelView trackReelView(String reelId, String userId, Integer viewDuration, String deviceInfo);
    boolean hasUserViewedReel(String reelId, String userId);
    long getReelViewCount(String reelId);
    List<ReelView> getReelViews(String reelId);
    List<ReelView> getUserViews(String userId);
    ReelView getReelView(String reelId, String userId);
    Double getAverageViewDuration(String reelId);
    Long getTotalViewTime(String reelId);
}
