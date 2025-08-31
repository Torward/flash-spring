package ru.lomov.flashbackend.story;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.story.dto.CreateStoryRequest;
import ru.lomov.flashbackend.story.dto.StoryResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/story")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @PostMapping("/{userId}")
    public ResponseEntity<StoryResponse> createStory(@PathVariable String userId, @RequestBody CreateStoryRequest request) {
        Story story = StoryMapper.toEntity(request, userId);
        Story createdStory = storyService.createStory(story);
        StoryResponse response = StoryMapper.toResponse(createdStory);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<StoryResponse>> getUserStories(@PathVariable String userId) {
        List<Story> stories = storyService.getUserStories(userId);
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{userId}/active")
    public ResponseEntity<List<StoryResponse>> getActiveUserStories(@PathVariable String userId) {
        List<Story> stories = storyService.getActiveUserStories(userId);
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/active")
    public ResponseEntity<List<StoryResponse>> getActiveStoriesByUserIds(@RequestParam List<String> userIds) {
        List<Story> stories = storyService.getActiveStoriesByUserIds(userIds);
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<StoryResponse>> getPopularStories() {
        List<Story> stories = storyService.getPopularStories();
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{userId}/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable String userId, @PathVariable String storyId) {
        storyService.deleteStory(storyId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{storyId}/view")
    public ResponseEntity<StoryResponse> incrementViews(@PathVariable String storyId) {
        Story story = storyService.incrementViews(storyId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/count")
    public ResponseEntity<Long> getUserStoriesCount(@PathVariable String userId) {
        long count = storyService.getUserStoriesCount(userId);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/cleanup/expired")
    public ResponseEntity<Void> cleanupExpiredStories() {
        storyService.deleteExpiredStories();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Story Service is healthy");
    }

    // New Firebase-compatible endpoints
    @GetMapping("/{userId}/highlights")
    public ResponseEntity<List<StoryResponse>> getUserHighlightStories(@PathVariable String userId) {
        List<Story> stories = storyService.getUserHighlightStories(userId);
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{userId}/archived")
    public ResponseEntity<List<StoryResponse>> getUserArchivedStories(@PathVariable String userId) {
        List<Story> stories = storyService.getUserArchivedStories(userId);
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{userId}/{storyId}/highlight")
    public ResponseEntity<StoryResponse> addToHighlight(@PathVariable String userId, @PathVariable String storyId) {
        Story story = storyService.addToHighlight(storyId, userId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/{storyId}/highlight")
    public ResponseEntity<StoryResponse> removeFromHighlight(@PathVariable String userId, @PathVariable String storyId) {
        Story story = storyService.removeFromHighlight(storyId, userId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/{storyId}/archive")
    public ResponseEntity<StoryResponse> archiveStory(@PathVariable String userId, @PathVariable String storyId) {
        Story story = storyService.archiveStory(storyId, userId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/{storyId}/archive")
    public ResponseEntity<StoryResponse> unarchiveStory(@PathVariable String userId, @PathVariable String storyId) {
        Story story = storyService.unarchiveStory(storyId, userId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/{storyId}/like")
    public ResponseEntity<StoryResponse> likeStory(@PathVariable String userId, @PathVariable String storyId) {
        Story story = storyService.likeStory(storyId, userId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/{storyId}/like")
    public ResponseEntity<StoryResponse> unlikeStory(@PathVariable String userId, @PathVariable String storyId) {
        Story story = storyService.unlikeStory(storyId, userId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/{storyId}/comment")
    public ResponseEntity<StoryResponse> addComment(@PathVariable String userId, @PathVariable String storyId) {
        Story story = storyService.addComment(storyId, userId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/{storyId}/comment")
    public ResponseEntity<StoryResponse> removeComment(@PathVariable String userId, @PathVariable String storyId) {
        Story story = storyService.removeComment(storyId, userId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/{storyId}/share")
    public ResponseEntity<StoryResponse> shareStory(@PathVariable String userId, @PathVariable String storyId) {
        Story story = storyService.shareStory(storyId, userId);
        StoryResponse response = StoryMapper.toResponse(story);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/type/{type}")
    public ResponseEntity<List<StoryResponse>> getStoriesByType(@PathVariable String userId, @PathVariable String type) {
        List<Story> stories = storyService.getStoriesByType(userId, type);
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/location")
    public ResponseEntity<List<StoryResponse>> getStoriesWithLocation() {
        List<Story> stories = storyService.getStoriesWithLocation();
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/hashtag/{hashtag}")
    public ResponseEntity<List<StoryResponse>> getStoriesByHashtag(@PathVariable String hashtag) {
        List<Story> stories = storyService.getStoriesByHashtag(hashtag);
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/mention/{mention}")
    public ResponseEntity<List<StoryResponse>> getStoriesByMention(@PathVariable String mention) {
        List<Story> stories = storyService.getStoriesByMention(mention);
        List<StoryResponse> responses = stories.stream()
                .map(StoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
