package ru.lomov.flash.reels.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.reels.entities.Reel;
import ru.lomov.flash.reels.repositories.ReelRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReelServiceImpl implements ReelService {

    private final ReelRepository reelRepository;

    @Override
    public Reel createReel(Reel reel) {
        return reelRepository.save(reel);
    }

    @Override
    public Reel getReelById(String id) {
        return reelRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Reel not found with id: " + id));
    }

    @Override
    public Reel getReelByIdAndUserId(String id, String userId) {
        return reelRepository.findByIdAndUserIdAndIsActiveTrue(id, userId)
                .orElseThrow(() -> new RuntimeException("Reel not found with id: " + id + " for user: " + userId));
    }

    @Override
    public List<Reel> getUserReels(String userId) {
        return reelRepository.findActiveReelsByUser(userId);
    }

    @Override
    public List<Reel> getPublicReels() {
        return reelRepository.findPublicActiveReels();
    }

    @Override
    public List<Reel> getReelsByUserIds(List<String> userIds) {
        return reelRepository.findReelsByUsers(userIds);
    }

    @Override
    public List<Reel> searchReels(String query) {
        return reelRepository.searchReels(query);
    }

    @Override
    public Reel updateReel(String id, Reel reel) {
        Reel existingReel = getReelById(id);
        
        if (reel.getTitle() != null) {
            existingReel.setTitle(reel.getTitle());
        }
        if (reel.getDescription() != null) {
            existingReel.setDescription(reel.getDescription());
        }
        if (reel.getHashtags() != null) {
            existingReel.setHashtags(reel.getHashtags());
        }
        if (reel.getIsPublic() != null) {
            existingReel.setIsPublic(reel.getIsPublic());
        }
        
        return reelRepository.save(existingReel);
    }

    @Override
    public void deleteReel(String id) {
        Reel reel = getReelById(id);
        reel.setIsActive(false);
        reelRepository.save(reel);
    }

    @Override
    public void deleteReel(String id, String userId) {
        Reel reel = getReelByIdAndUserId(id, userId);
        reel.setIsActive(false);
        reelRepository.save(reel);
    }

    @Override
    public Reel likeReel(String id) {
        Reel reel = getReelById(id);
        reel.setLikesCount(reel.getLikesCount() + 1);
        return reelRepository.save(reel);
    }

    @Override
    public Reel unlikeReel(String id) {
        Reel reel = getReelById(id);
        if (reel.getLikesCount() > 0) {
            reel.setLikesCount(reel.getLikesCount() - 1);
        }
        return reelRepository.save(reel);
    }

    @Override
    public Reel incrementViews(String id) {
        Reel reel = getReelById(id);
        reel.setViewsCount(reel.getViewsCount() + 1);
        return reelRepository.save(reel);
    }

    @Override
    public Reel incrementShares(String id) {
        Reel reel = getReelById(id);
        reel.setSharesCount(reel.getSharesCount() + 1);
        return reelRepository.save(reel);
    }
}
