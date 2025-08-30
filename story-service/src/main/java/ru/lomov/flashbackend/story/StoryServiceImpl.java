package ru.lomov.flashbackend.story;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    @Override
    public Story createStory(Story story) {
        return storyRepository.save(story);
    }

    @Override
    public Story getStoryById(String storyId) {
        return storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found with id: " + storyId));
    }

    @Override
    public List<Story> getUserStories(String userId) {
        return storyRepository.findByUserId(userId);
    }

    @Override
    public List<Story> getActiveUserStories(String userId) {
        return storyRepository.findByUserIdAndTimeEndAfter(userId, LocalDateTime.now());
    }

    @Override
    public List<Story> getActiveStoriesByUserIds(List<String> userIds) {
        return storyRepository.findActiveStoriesByUserIds(userIds, LocalDateTime.now());
    }

    @Override
    public List<Story> getPopularStories() {
        return storyRepository.findPopularStories(LocalDateTime.now());
    }

    @Override
    public Story updateStory(String storyId, Story story) {
        Story existingStory = getStoryById(storyId);
        
        // Обновляем только разрешенные поля
        if (story.getText() != null) {
            existingStory.setText(story.getText());
        }
        if (story.getLocation() != null) {
            existingStory.setLocation(story.getLocation());
        }
        if (story.getPrivacy() != null) {
            existingStory.setPrivacy(story.getPrivacy());
        }
        if (story.getBackground() != null) {
            existingStory.setBackground(story.getBackground());
        }
        if (story.getFont() != null) {
            existingStory.setFont(story.getFont());
        }
        if (story.getColor() != null) {
            existingStory.setColor(story.getColor());
        }
        if (story.getHashtags() != null) {
            existingStory.setHashtags(story.getHashtags());
        }
        if (story.getMentions() != null) {
            existingStory.setMentions(story.getMentions());
        }
        if (story.getLink() != null) {
            existingStory.setLink(story.getLink());
        }
        
        return storyRepository.save(existingStory);
    }

    @Override
    public void deleteStory(String storyId, String userId) {
        Story story = getStoryById(storyId);
        if (!story.getUserId().equals(userId)) {
            throw new RuntimeException("User is not authorized to delete this story");
        }
        storyRepository.deleteById(storyId);
    }

    @Override
    public void deleteExpiredStories() {
        storyRepository.deleteByTimeEndBefore(LocalDateTime.now());
    }

    @Override
    public Story incrementViews(String storyId) {
        Story story = getStoryById(storyId);
        story.setViews(story.getViews() + 1);
        return storyRepository.save(story);
    }

    @Override
    public long getUserStoriesCount(String userId) {
        return storyRepository.countByUserId(userId);
    }

    @Override
    public List<Story> getUserHighlightStories(String userId) {
        return storyRepository.findHighlightStoriesByUserId(userId);
    }

    @Override
    public List<Story> getUserArchivedStories(String userId) {
        return storyRepository.findByUserIdAndIsArchived(userId, true);
    }

    @Override
    public Story addToHighlight(String storyId, String userId) {
        Story story = getStoryById(storyId);
        if (!story.getUserId().equals(userId)) {
            throw new RuntimeException("User is not authorized to modify this story");
        }
        story.setIsHighlight(true);
        return storyRepository.save(story);
    }

    @Override
    public Story removeFromHighlight(String storyId, String userId) {
        Story story = getStoryById(storyId);
        if (!story.getUserId().equals(userId)) {
            throw new RuntimeException("User is not authorized to modify this story");
        }
        story.setIsHighlight(false);
        return storyRepository.save(story);
    }

    @Override
    public Story archiveStory(String storyId, String userId) {
        Story story = getStoryById(storyId);
        if (!story.getUserId().equals(userId)) {
            throw new RuntimeException("User is not authorized to modify this story");
        }
        story.setIsArchived(true);
        return storyRepository.save(story);
    }

    @Override
    public Story unarchiveStory(String storyId, String userId) {
        Story story = getStoryById(storyId);
        if (!story.getUserId().equals(userId)) {
            throw new RuntimeException("User is not authorized to modify this story");
        }
        story.setIsArchived(false);
        return storyRepository.save(story);
    }

    @Override
    public Story likeStory(String storyId, String userId) {
        Story story = getStoryById(storyId);
        story.setLikeCount(story.getLikeCount() + 1);
        return storyRepository.save(story);
    }

    @Override
    public Story unlikeStory(String storyId, String userId) {
        Story story = getStoryById(storyId);
        story.setLikeCount(Math.max(0, story.getLikeCount() - 1));
        return storyRepository.save(story);
    }

    @Override
    public Story addComment(String storyId, String userId) {
        Story story = getStoryById(storyId);
        story.setCommentCount(story.getCommentCount() + 1);
        return storyRepository.save(story);
    }

    @Override
    public Story removeComment(String storyId, String userId) {
        Story story = getStoryById(storyId);
        story.setCommentCount(Math.max(0, story.getCommentCount() - 1));
        return storyRepository.save(story);
    }

    @Override
    public Story shareStory(String storyId, String userId) {
        Story story = getStoryById(storyId);
        story.setShareCount(story.getShareCount() + 1);
        return storyRepository.save(story);
    }

    @Override
    public List<Story> getStoriesByType(String userId, String type) {
        return storyRepository.findByUserIdAndType(userId, type);
    }

    @Override
    public List<Story> getStoriesWithLocation() {
        return storyRepository.findStoriesWithLocation(LocalDateTime.now());
    }

    @Override
    public List<Story> getStoriesByHashtag(String hashtag) {
        return storyRepository.findStoriesByHashtag(hashtag, LocalDateTime.now());
    }

    @Override
    public List<Story> getStoriesByMention(String mention) {
        return storyRepository.findStoriesByMention(mention, LocalDateTime.now());
    }

    @Override
    public List<Story> getStoriesByPoll() {
        return storyRepository.findByUserIdAndType(null, "poll");
    }

    @Override
    public List<Story> getStoriesByQuiz() {
        return storyRepository.findByUserIdAndType(null, "quiz");
    }

    @Override
    public List<Story> getStoriesByQuestion() {
        return storyRepository.findByUserIdAndType(null, "question");
    }

    @Override
    public List<Story> getStoriesByCountdown() {
        return storyRepository.findByUserIdAndType(null, "countdown");
    }

    @Override
    public List<Story> getStoriesByEmojiSlider() {
        return storyRepository.findByUserIdAndType(null, "emoji_slider");
    }
}
