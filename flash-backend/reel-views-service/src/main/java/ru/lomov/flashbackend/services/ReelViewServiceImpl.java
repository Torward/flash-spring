package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.ReelView;
import ru.lomov.flashbackend.repositories.ReelViewRepository;
import ru.lomov.flashbackend.exceptions.ReelViewNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReelViewServiceImpl implements ReelViewService {

    private final ReelViewRepository reelViewRepository;

    @Override
    public ReelView trackReelView(String reelId, String userId, Integer viewDuration, String deviceInfo) {
        ReelView existingView = reelViewRepository.findByReelIdAndUserId(reelId, userId);
        if (existingView != null) {
            existingView.setViewDuration(viewDuration);
            existingView.setDeviceInfo(deviceInfo);
            return reelViewRepository.save(existingView);
        } else {
            ReelView newView = ReelView.builder()
                    .reelId(reelId)
                    .userId(userId)
                    .viewDuration(viewDuration)
                    .deviceInfo(deviceInfo)
                    .build();
            return reelViewRepository.save(newView);
        }
    }

    @Override
    public boolean hasUserViewedReel(String reelId, String userId) {
        return reelViewRepository.existsByReelIdAndUserId(reelId, userId);
    }

    @Override
    public long getReelViewCount(String reelId) {
        return reelViewRepository.countByReelId(reelId);
    }

    @Override
    public List<ReelView> getReelViews(String reelId) {
        return reelViewRepository.findByReelId(reelId);
    }

    @Override
    public List<ReelView> getUserViews(String userId) {
        return reelViewRepository.findByUserId(userId);
    }

    @Override
    public ReelView getReelView(String reelId, String userId) {
        ReelView view = reelViewRepository.findByReelIdAndUserId(reelId, userId);
        if (view == null) {
            throw new ReelViewNotFoundException("Reel view not found for reelId: " + reelId + " and userId: " + userId);
        }
        return view;
    }

    @Override
    public Double getAverageViewDuration(String reelId) {
        return reelViewRepository.getAverageViewDurationByReelId(reelId);
    }

    @Override
    public Long getTotalViewTime(String reelId) {
        return reelViewRepository.getTotalViewTimeByReelId(reelId);
    }
}
