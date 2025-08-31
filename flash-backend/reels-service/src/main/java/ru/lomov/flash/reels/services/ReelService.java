package ru.lomov.flash.reels.services;

import java.util.List;

import ru.lomov.flash.reels.entities.Reel;

public interface ReelService {
    Reel createReel(Reel reel);
    Reel getReelById(String id);
    Reel getReelByIdAndUserId(String id, String userId);
    List<Reel> getUserReels(String userId);
    List<Reel> getPublicReels();
    List<Reel> getReelsByUserIds(List<String> userIds);
    List<Reel> searchReels(String query);
    Reel updateReel(String id, Reel reel);
    void deleteReel(String id);
    void deleteReel(String id, String userId);
    Reel likeReel(String id);
    Reel unlikeReel(String id);
    Reel incrementViews(String id);
    Reel incrementShares(String id);
}
