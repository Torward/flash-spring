package ru.lomov.flashbackend.story;

import java.util.List;

public interface StoryService {
    
    Story createStory(Story story);
    
    Story getStoryById(String storyId);
    
    List<Story> getUserStories(String userId);
    
    List<Story> getActiveUserStories(String userId);
    
    List<Story> getActiveStoriesByUserIds(List<String> userIds);
    
    List<Story> getPopularStories();
    
    Story updateStory(String storyId, Story story);
    
    void deleteStory(String storyId, String userId);
    
    void deleteExpiredStories();
    
    Story incrementViews(String storyId);
    
    long getUserStoriesCount(String userId);
    
    // New Firebase-compatible methods
    List<Story> getUserHighlightStories(String userId);
    
    List<Story> getUserArchivedStories(String userId);
    
    Story addToHighlight(String storyId, String userId);
    
    Story removeFromHighlight(String storyId, String userId);
    
    Story archiveStory(String storyId, String userId);
    
    Story unarchiveStory(String storyId, String userId);
    
    Story likeStory(String storyId, String userId);
    
    Story unlikeStory(String storyId, String userId);
    
    Story addComment(String storyId, String userId);
    
    Story removeComment(String storyId, String userId);
    
    Story shareStory(String storyId, String userId);
    
    List<Story> getStoriesByType(String userId, String type);
    
    List<Story> getStoriesWithLocation();
    
    List<Story> getStoriesByHashtag(String hashtag);
    
    List<Story> getStoriesByMention(String mention);
    
    List<Story> getStoriesByPoll();
    
    List<Story> getStoriesByQuiz();
    
    List<Story> getStoriesByQuestion();
    
    List<Story> getStoriesByCountdown();
    
    List<Story> getStoriesByEmojiSlider();
}
