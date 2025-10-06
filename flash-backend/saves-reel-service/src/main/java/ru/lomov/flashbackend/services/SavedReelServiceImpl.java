package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.SavedReel;
import ru.lomov.flashbackend.repositories.SavedReelRepository;
import ru.lomov.flashbackend.exceptions.SavedReelNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SavedReelServiceImpl implements SavedReelService {

    private final SavedReelRepository savedReelRepository;

    @Override
    public SavedReel saveReel(String userId, String reelId) {
        if (savedReelRepository.existsByUserIdAndReelId(userId, reelId)) {
            throw new IllegalArgumentException("Reel already saved by this user");
        }

        SavedReel savedReel = SavedReel.builder()
                .userId(userId)
                .reelId(reelId)
                .build();

        return savedReelRepository.save(savedReel);
    }

    @Override
    public void unsaveReel(String userId, String reelId) {
        if (!savedReelRepository.existsByUserIdAndReelId(userId, reelId)) {
            throw new SavedReelNotFoundException("Saved reel not found for userId: " + userId + " and reelId: " + reelId);
        }
        savedReelRepository.deleteByUserIdAndReelId(userId, reelId);
    }

    @Override
    public boolean isReelSavedByUser(String userId, String reelId) {
        return savedReelRepository.existsByUserIdAndReelId(userId, reelId);
    }

    @Override
    public List<SavedReel> getUserSavedReels(String userId) {
        return savedReelRepository.findByUserId(userId);
    }

    @Override
    public List<SavedReel> getReelSavers(String reelId) {
        return savedReelRepository.findByReelId(reelId);
    }

    @Override
    public long getReelSaveCount(String reelId) {
        return savedReelRepository.countByReelId(reelId);
    }

    @Override
    public long getUserSaveCount(String userId) {
        return savedReelRepository.countByUserId(userId);
    }

    @Override
    public SavedReel getSavedReel(String userId, String reelId) {
        Optional<SavedReel> savedReel = savedReelRepository.findByUserIdAndReelId(userId, reelId);
        return savedReel.orElseThrow(() ->
            new SavedReelNotFoundException("Saved reel not found for userId: " + userId + " and reelId: " + reelId));
    }
}
