package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.ReelLike;
import ru.lomov.flashbackend.repositories.ReelLikeRepository;
import ru.lomov.flashbackend.exceptions.ReelLikeNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReelLikeServiceImpl implements ReelLikeService {

    private final ReelLikeRepository reelLikeRepository;

    @Override
    public ReelLike likeReel(String reelId, String userId) {
        // Check if user already liked this reel
        if (reelLikeRepository.existsByReelIdAndUserId(reelId, userId)) {
            throw new IllegalStateException("User has already liked this reel");
        }

        // Create new like
        ReelLike reelLike = ReelLike.builder()
            .reelId(reelId)
            .userId(userId)
            .build();

        return reelLikeRepository.save(reelLike);
    }

    @Override
    public void unlikeReel(String reelId, String userId) {
        ReelLike reelLike = reelLikeRepository.findByReelIdAndUserId(reelId, userId)
            .orElseThrow(() -> new ReelLikeNotFoundException("Like not found for reel: " + reelId + " and user: " + userId));

        reelLikeRepository.delete(reelLike);
    }

    @Override
    public boolean hasUserLikedReel(String reelId, String userId) {
        return reelLikeRepository.existsByReelIdAndUserId(reelId, userId);
    }

    @Override
    public long getReelLikeCount(String reelId) {
        return reelLikeRepository.countByReelId(reelId);
    }

    @Override
    public List<ReelLike> getReelLikes(String reelId) {
        return reelLikeRepository.findByReelId(reelId);
    }

    @Override
    public List<ReelLike> getUserLikes(String userId) {
        return reelLikeRepository.findByUserId(userId);
    }

    @Override
    public Optional<ReelLike> getReelLike(String reelId, String userId) {
        return reelLikeRepository.findByReelIdAndUserId(reelId, userId);
    }
}
