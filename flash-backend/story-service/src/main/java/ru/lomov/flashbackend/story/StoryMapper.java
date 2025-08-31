package ru.lomov.flashbackend.story;

import ru.lomov.flashbackend.story.dto.CreateStoryRequest;
import ru.lomov.flashbackend.story.dto.StoryResponse;

public class StoryMapper {

    public static Story toEntity(CreateStoryRequest request, String userId) {
        Story story = new Story();
        story.setUserId(userId);
        story.setType(request.getType());
        story.setImage(request.getImage());
        story.setVideo(request.getVideo());
        story.setText(request.getText());
        story.setLocation(request.getLocation());
        story.setPrivacy(request.getPrivacy());
        story.setBackground(request.getBackground());
        story.setFont(request.getFont());
        story.setColor(request.getColor());
        story.setHashtags(request.getHashtags());
        story.setMentions(request.getMentions());
        story.setLink(request.getLink());
        story.setDuration(request.getDuration());
        story.setAspectRatio(request.getAspectRatio());
        story.setFilter(request.getFilter());
        story.setMusic(request.getMusic());
        story.setProductTag(request.getProductTag());
        story.setLocationId(request.getLocationId());
        story.setPollQuestion(request.getPollQuestion());
        story.setPollOptions(request.getPollOptions());
        story.setQuizQuestion(request.getQuizQuestion());
        story.setQuizAnswer(request.getQuizAnswer());
        story.setEmojiSlider(request.getEmojiSlider());
        story.setQuestion(request.getQuestion());
        story.setCountdownEnd(request.getCountdownEnd());
        return story;
    }

    public static StoryResponse toResponse(Story story) {
        StoryResponse response = new StoryResponse();
        response.setId(story.getId());
        response.setUserId(story.getUserId());
        response.setType(story.getType());
        response.setImage(story.getImage());
        response.setVideo(story.getVideo());
        response.setTimeStart(story.getTimeStart());
        response.setTimeEnd(story.getTimeEnd());
        response.setViews(story.getViews());
        response.setText(story.getText());
        response.setLocation(story.getLocation());
        response.setPrivacy(story.getPrivacy());
        response.setBackground(story.getBackground());
        response.setFont(story.getFont());
        response.setColor(story.getColor());
        
        // Additional Firebase fields
        response.setHashtags(story.getHashtags());
        response.setMentions(story.getMentions());
        response.setLink(story.getLink());
        response.setDuration(story.getDuration());
        response.setAspectRatio(story.getAspectRatio());
        response.setFilter(story.getFilter());
        response.setMusic(story.getMusic());
        response.setProductTag(story.getProductTag());
        response.setLocationId(story.getLocationId());
        response.setPollQuestion(story.getPollQuestion());
        response.setPollOptions(story.getPollOptions());
        response.setQuizQuestion(story.getQuizQuestion());
        response.setQuizAnswer(story.getQuizAnswer());
        response.setEmojiSlider(story.getEmojiSlider());
        response.setQuestion(story.getQuestion());
        response.setCountdownEnd(story.getCountdownEnd());
        
        // Firebase statistics
        response.setLikeCount(story.getLikeCount());
        response.setCommentCount(story.getCommentCount());
        response.setShareCount(story.getShareCount());
        response.setIsArchived(story.getIsArchived());
        response.setIsHighlight(story.getIsHighlight());
        
        // Timestamps
        response.setCreatedAt(story.getCreatedAt());
        response.setUpdatedAt(story.getUpdatedAt());
        
        return response;
    }
}
