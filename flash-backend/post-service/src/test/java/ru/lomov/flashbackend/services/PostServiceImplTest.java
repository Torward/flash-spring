package ru.lomov.flashbackend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.repositories.PostRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post testPost;
    private String testUserId;
    private String testPostId;

    @BeforeEach
    void setUp() {
        testUserId = "user123";
        testPostId = "post123";

        testPost = new Post();
        testPost.setPostId(testPostId);
        testPost.setUserId(testUserId);
        testPost.setContent("This is a test post with #hashtag and @mention");
        testPost.setVisibility("PUBLIC");
        testPost.setDeleted(false);
        testPost.setLikeCount(5);
        testPost.setCommentCount(3);
        testPost.setShareCount(2);
        testPost.setViewCount(100);
        testPost.setSaveCount(1);
        testPost.setMediaUrls(new HashSet<>(Arrays.asList("url1", "url2")));
        testPost.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void extractHashtags_WithValidContent_ShouldReturnHashtags() {
        String content = "This is a post with #java #spring and #testing";
        Set<String> hashtags = postService.extractHashtags(content);
        assertNotNull(hashtags);
        assertEquals(3, hashtags.size());
        assertTrue(hashtags.contains("java"));
        assertTrue(hashtags.contains("spring"));
        assertTrue(hashtags.contains("testing"));
    }

    @Test
    void extractHashtags_WithNoHashtags_ShouldReturnEmptySet() {
        String content = "This is a post without hashtags";
        Set<String> hashtags = postService.extractHashtags(content);
        assertNotNull(hashtags);
        assertTrue(hashtags.isEmpty());
    }

    @Test
    void extractHashtags_WithNullContent_ShouldReturnEmptySet() {
        Set<String> hashtags = postService.extractHashtags(null);
        assertNotNull(hashtags);
        assertTrue(hashtags.isEmpty());
    }

    @Test
    void extractMentions_WithValidContent_ShouldReturnMentions() {
        String content = "Hello @user1 and @user2, check this out!";
        Set<String> mentions = postService.extractMentions(content);
        assertNotNull(mentions);
        assertEquals(2, mentions.size());
        assertTrue(mentions.contains("user1"));
        assertTrue(mentions.contains("user2"));
    }

    @Test
    void extractMentions_WithNoMentions_ShouldReturnEmptySet() {
        String content = "This is a post without mentions";
        Set<String> mentions = postService.extractMentions(content);
        assertNotNull(mentions);
        assertTrue(mentions.isEmpty());
    }

    @Test
    void validatePostContent_WithValidPost_ShouldReturnTrue() {
        Post validPost = new Post();
        validPost.setContent("This is a valid post content");
        validPost.setMediaUrls(new HashSet<>(Arrays.asList("url1", "url2")));
        boolean result = postService.validatePostContent(validPost);
        assertTrue(result);
    }

    @Test
    void validatePostContent_WithNullPost_ShouldReturnFalse() {
        boolean result = postService.validatePostContent(null);
        assertFalse(result);
    }

    @Test
    void validatePostContent_WithTooLongContent_ShouldReturnFalse() {
        Post invalidPost = new Post();
        invalidPost.setContent("a".repeat(5001));
        boolean result = postService.validatePostContent(invalidPost);
        assertFalse(result);
    }

    @Test
    void validatePostContent_WithInappropriateContent_ShouldReturnFalse() {
        Post invalidPost = new Post();
        invalidPost.setContent("This post contains spam content");
        boolean result = postService.validatePostContent(invalidPost);
        assertFalse(result);
    }

    @Test
    void checkPostPermissions_WithPostOwner_ShouldReturnTrue() {
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));
        boolean result = postService.checkPostPermissions(testPostId, testUserId);
        assertTrue(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void checkPostPermissions_WithAdminUser_ShouldReturnTrue() {
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));
        boolean result = postService.checkPostPermissions(testPostId, "ADMIN");
        assertTrue(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void checkPostPermissions_WithNonOwnerUser_ShouldReturnFalse() {
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));
        boolean result = postService.checkPostPermissions(testPostId, "otherUser");
        assertFalse(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void checkPostPermissions_WithNonExistentPost_ShouldReturnFalse() {
        when(postRepository.findById(testPostId)).thenReturn(Optional.empty());
        boolean result = postService.checkPostPermissions(testPostId, testUserId);
        assertFalse(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void isPostVisibleToUser_WithPublicPost_ShouldReturnTrue() {
        testPost.setVisibility("PUBLIC");
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));
        boolean result = postService.isPostVisibleToUser(testPostId, "anyUser");
        assertTrue(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void isPostVisibleToUser_WithPrivatePostAndOwner_ShouldReturnTrue() {
        testPost.setVisibility("PRIVATE");
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));
        boolean result = postService.isPostVisibleToUser(testPostId, testUserId);
        assertTrue(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void isPostVisibleToUser_WithPrivatePostAndNonOwner_ShouldReturnFalse() {
        testPost.setVisibility("PRIVATE");
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));
        boolean result = postService.isPostVisibleToUser(testPostId, "otherUser");
        assertFalse(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void isPostVisibleToUser_WithFollowersVisibility_ShouldReturnTrue() {
        testPost.setVisibility("FOLLOWERS");
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));
        boolean result = postService.isPostVisibleToUser(testPostId, "followerUser");
        assertTrue(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void isPostVisibleToUser_WithDeletedPost_ShouldReturnFalse() {
        testPost.setDeleted(true);
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));
        boolean result = postService.isPostVisibleToUser(testPostId, testUserId);
        assertFalse(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void isPostVisibleToUser_WithNonExistentPost_ShouldReturnFalse() {
        when(postRepository.findById(testPostId)).thenReturn(Optional.empty());
        boolean result = postService.isPostVisibleToUser(testPostId, testUserId);
        assertFalse(result);
        verify(postRepository).findById(testPostId);
    }

    @Test
    void isPostVisibleToUser_WithInvalidVisibility_ShouldReturnFalse() {
        testPost.setVisibility("INVALID");
        when(postRepository.findById(testPostId)).thenReturn(Optional.of(testPost));
        boolean result = postService.isPostVisibleToUser(testPostId, testUserId);
        assertFalse(result);
        verify(postRepository).findById(testPostId);
    }
}
