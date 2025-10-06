package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.SavedReel;
import java.util.List;

public interface SavedReelService {
    SavedReel saveReel(String userId, String reelId);
    void unsaveReel(String userId, String reelId);
    boolean isReelSavedByUser(String userId, String reelId);
    List<SavedReel> getUserSavedReels(String userId);
    List<SavedReel> getReelSavers(String reelId);
    long getReelSaveCount(String reelId);
    long getUserSaveCount(String userId);
    SavedReel getSavedReel(String userId, String reelId);
}
