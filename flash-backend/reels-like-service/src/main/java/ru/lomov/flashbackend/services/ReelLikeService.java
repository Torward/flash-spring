package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.ReelLike;
import java.util.List;
import java.util.Optional;

public interface ReelLikeService {
    ReelLike likeReel(String reelId, String userId);
    void unlikeReel(String reelId, String userId);
    boolean hasUserLikedReel(String reelId, String userId);
    long getReelLikeCount(String reelId);
    List<ReelLike> getReelLikes(String reelId);
    List<ReelLike> getUserLikes(String userId);
    Optional<ReelLike> getReelLike(String reelId, String userId);
}
