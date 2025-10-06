package ru.lomov.flashbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flashbackend.dto.UpdatePostTypeDto;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.repositories.PostRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(String postId) throws PostNotFoundException {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @Override
    public Post updatePost(String postId, Post post) throws PostNotFoundException {
        Post existingPost = getPostById(postId);
        // Update fields as necessary
        existingPost.setContent(post.getContent());
        existingPost.setMediaUrls(post.getMediaUrls());
        existingPost.setHashtags(post.getHashtags());
        existingPost.setVisibility(post.getVisibility());

        return postRepository.save(existingPost);
    }

    @Override
    public Post updatePostType(String postId, UpdatePostTypeDto updatePostTypeDto) throws PostNotFoundException {
        Post existingPost = getPostById(postId);
        if (updatePostTypeDto.getType() != null) {
            existingPost.setType(updatePostTypeDto.getType());
        }
        if (updatePostTypeDto.getVine() != null) {
            existingPost.setVine(updatePostTypeDto.getVine());
        }
        if (updatePostTypeDto.getMeme() != null) {
            existingPost.setMeme(updatePostTypeDto.getMeme());
        }
        // Add other fields update logic as needed

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setDeleted(true);
        postRepository.save(post);
    }

    @Override
    public void archivePost(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setArchived(true);
        postRepository.save(post);
    }

    @Override
    public void restorePost(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setDeleted(false);
        post.setArchived(false);
        postRepository.save(post);
    }

    @Override
    public Page<Post> getUserPosts(String userId, Pageable pageable) {
        return postRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<Post> getUserPublicPosts(String userId, Pageable pageable) {
        return postRepository.findByUserIdAndIsDeletedFalse(userId, pageable);
    }

    @Override
    public Page<Post> getUserDraftPosts(String userId, Pageable pageable) {
        return postRepository.findByUserIdAndStatus(userId, "DRAFT", pageable);
    }

    @Override
    public Page<Post> getUserArchivedPosts(String userId, Pageable pageable) {
        return postRepository.findByUserIdAndIsArchivedTrue(userId, pageable);
    }

    @Override
    public Page<Post> getFeedPosts(String userId, Pageable pageable) {
        // Logic to get feed posts
        return postRepository.findByVisibilityAndStatusAndIsDeletedFalse("PUBLIC", "ACTIVE", pageable);
    }

    @Override
    public Page<Post> getDiscoverPosts(Pageable pageable) {
        return postRepository.findByVisibilityAndStatusAndIsDeletedFalse("PUBLIC", "ACTIVE", pageable);
    }

    @Override
    public Page<Post> getTrendingPosts(Pageable pageable) {
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        return postRepository.findTrendingPosts(since, pageable);
    }

    @Override
    public Page<Post> getPopularPosts(Pageable pageable) {
        return postRepository.findPopularPosts(pageable);
    }

    @Override
    public Page<Post> searchPosts(String query, Pageable pageable) {
        return postRepository.searchByContent(query, pageable);
    }

    @Override
    public Page<Post> searchPostsByHashtag(String hashtag, Pageable pageable) {
        return postRepository.findByHashtag(hashtag, pageable);
    }

    @Override
    public Page<Post> searchPostsByLocation(Double latitude, Double longitude, Double radius, Pageable pageable) {
        return postRepository.findByLocationWithinRadius(latitude, longitude, radius, pageable);
    }

    @Override
    public Page<Post> searchPostsByMention(String mentionedUserId, Pageable pageable) {
        return postRepository.findByMentionedUser(mentionedUserId, pageable);
    }

    @Override
    public Page<Post> getPostsForModeration(Pageable pageable) {
        return postRepository.findByIsModeratedFalse(pageable);
    }

    @Override
    public Post moderatePost(String postId, String status, String reason, Double score) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setModerationStatus(status);
        post.setModerationReason(reason);
        post.setModerationScore(score);
        return postRepository.save(post);
    }

    @Override
    public Page<Post> getModeratedPosts(String status, Pageable pageable) {
        return postRepository.findByModerationStatus(status, pageable);
    }

    @Override
    public Map<String, Object> getUserPostStats(String userId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPosts", postRepository.countByUserId(userId));
        stats.put("totalLikes", postRepository.sumLikeCountByUserId(userId));
        stats.put("totalComments", postRepository.sumCommentCountByUserId(userId));
        stats.put("totalViews", postRepository.sumViewCountByUserId(userId));

        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
        stats.put("postsThisWeek", postRepository.countByUserIdAndDateRange(userId, weekAgo, LocalDateTime.now()));

        return stats;
    }

    @Override
    public Map<String, Object> getPostAnalytics(String postId) throws PostNotFoundException {
        Post post = getPostById(postId);
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("postId", post.getPostId());
        analytics.put("userId", post.getUserId());
        analytics.put("likeCount", post.getLikeCount());
        analytics.put("commentCount", post.getCommentCount());
        analytics.put("shareCount", post.getShareCount());
        analytics.put("viewCount", post.getViewCount());
        analytics.put("saveCount", post.getSaveCount());
        analytics.put("engagementRate", calculateEngagementRate(post));
        analytics.put("createdAt", post.getCreatedAt());
        analytics.put("visibility", post.getVisibility());
        analytics.put("status", post.getStatus());
        return analytics;
    }

    private Double calculateEngagementRate(Post post) {
        int totalEngagement = post.getLikeCount() + post.getCommentCount() + post.getShareCount() + post.getSaveCount();
        return post.getViewCount() > 0 ? (double) totalEngagement / post.getViewCount() * 100 : 0.0;
    }

    @Override
    public Map<String, Object> getTrendingHashtags(int limit) {
        // Logic to get trending hashtags
        return null;
    }

    @Override
    public Map<String, Object> getLocationInsights(Double latitude, Double longitude, Double radius) {
        // Logic to get location insights
        return null;
    }

    @Override
    public Post likePost(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setLikeCount(post.getLikeCount() + 1);
        return postRepository.save(post);
    }

    @Override
    public Post unlikePost(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setLikeCount(post.getLikeCount() - 1);
        return postRepository.save(post);
    }

    @Override
    public Post viewPost(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setViewCount(post.getViewCount() + 1);
        return postRepository.save(post);
    }

    @Override
    public Post savePost(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setSaveCount(post.getSaveCount() + 1);
        return postRepository.save(post);
    }

    @Override
    public Post unsavePost(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setSaveCount(post.getSaveCount() - 1);
        return postRepository.save(post);
    }

    @Override
    public Post sharePost(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setShareCount(post.getShareCount() + 1);
        return postRepository.save(post);
    }

    @Override
    public Post addComment(String postId, String userId, String comment) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setCommentCount(post.getCommentCount() + 1);
        return postRepository.save(post);
    }

    @Override
    public Post removeComment(String postId, String commentId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        post.setCommentCount(post.getCommentCount() - 1);
        return postRepository.save(post);
    }

    @Override
    public Post voteInPoll(String postId, String userId, int optionIndex) throws PostNotFoundException {
        Post post = getPostById(postId);
        if (!"POLL".equals(post.getType()) || post.getPollOptions().isEmpty()) {
            throw new IllegalArgumentException("Post is not a poll or has no options");
        }
        if (optionIndex < 0 || optionIndex >= post.getPollOptions().size()) {
            throw new IllegalArgumentException("Invalid poll option index");
        }

        List<Integer> pollVotes = new ArrayList<>(post.getPollVotes());
        if (optionIndex >= pollVotes.size()) {
            // Extend the list if necessary
            while (pollVotes.size() <= optionIndex) {
                pollVotes.add(0);
            }
        }
        pollVotes.set(optionIndex, pollVotes.get(optionIndex) + 1);
        post.setPollVotes(new HashSet<>(pollVotes));

        return postRepository.save(post);
    }

    @Override
    public Post createPoll(String userId, String question, List<String> options, LocalDateTime endsAt) {
        Post poll = new Post();
        poll.setUserId(userId);
        poll.setContent(question);
        poll.setPostType("POLL");
        poll.setPollOptions(new HashSet<>(options));
        poll.setPollVotes(new HashSet<>(Collections.nCopies(options.size(), 0)));
        poll.setPollEndsAt(endsAt);
        poll.setStatus("ACTIVE");
        poll.setVisibility("PUBLIC");

        return postRepository.save(poll);
    }

    @Override
    public Post closePoll(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        if (!userId.equals(post.getUserId())) {
            throw new IllegalArgumentException("Only poll creator can close the poll");
        }
        post.setPollEndsAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public Post startLiveStream(String userId, String title, String description) {
        Post liveStream = new Post();
        liveStream.setUserId(userId);
        liveStream.setContent(title);
        liveStream.setPostType("LIVE");
        liveStream.setIsLive(true);
        liveStream.setLiveViewers(0);
        liveStream.setLiveStartedAt(LocalDateTime.now());
        liveStream.setStatus("ACTIVE");
        liveStream.setVisibility("PUBLIC");

        return postRepository.save(liveStream);
    }

    @Override
    public Post endLiveStream(String postId, String userId) throws PostNotFoundException {
        Post post = getPostById(postId);
        if (!userId.equals(post.getUserId())) {
            throw new IllegalArgumentException("Only stream owner can end the live stream");
        }
        if (!"LIVE".equals(post.getPostType()) || !post.getIsLive()) {
            throw new IllegalArgumentException("Post is not an active live stream");
        }

        post.setIsLive(false);
        post.setLiveEndedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public Post updateLiveViewers(String postId, int viewerCount) throws PostNotFoundException {
        Post post = getPostById(postId);
        if (!"LIVE".equals(post.getPostType()) || !post.getIsLive()) {
            throw new IllegalArgumentException("Post is not an active live stream");
        }

        post.setLiveViewers(viewerCount);
        return postRepository.save(post);
    }

    @Override
    public Page<Post> getScheduledPosts(String userId, Pageable pageable) {
        // Logic to get scheduled posts
        return null;
    }

    @Override
    public Post schedulePost(Post post, LocalDateTime scheduleTime) {
        // Logic to schedule post
        return null;
    }

    @Override
    public Post publishScheduledPost(String postId) throws PostNotFoundException {
        // Logic to publish scheduled post
        return null;
    }

    @Override
    public void cancelScheduledPost(String postId, String userId) throws PostNotFoundException {
        // Logic to cancel scheduled post
    }

    @Override
    public Page<Post> getSponsoredPosts(Pageable pageable) {
        // Logic to get sponsored posts
        return null;
    }

    @Override
    public Post createSponsoredPost(Post post, String sponsorId, String campaignId) {
        // Logic to create sponsored post
        return null;
    }

    @Override
    public Post updateSponsorship(String postId, String sponsorId, String campaignId, Double cpmRate) throws PostNotFoundException {
        // Logic to update sponsorship
        return null;
    }

    @Override
    public List<Post> bulkCreatePosts(List<Post> posts) {
        // Logic to bulk create posts
        return null;
    }

    @Override
    public void bulkDeletePosts(List<String> postIds, String userId) {
        // Logic to bulk delete posts
    }

    @Override
    public void bulkArchivePosts(List<String> postIds, String userId) {
        // Logic to bulk archive posts
    }

    @Override
    public void bulkPublishPosts(List<String> postIds) {
        // Logic to bulk publish posts
    }

    @Override
    public String exportUserPosts(String userId, String format) {
        // Logic to export user posts
        return null;
    }

    @Override
    public void backupPosts(LocalDateTime beforeDate) {
        // Logic to backup posts
    }

    @Override
    public void restorePostsFromBackup(String backupId) {
        // Logic to restore posts from backup
    }

    @Override
    public Page<Post> getRecommendedPosts(String userId, Pageable pageable) {
        // Logic to get recommended posts
        return null;
    }

    @Override
    public Page<Post> getSimilarPosts(String postId, Pageable pageable) {
        // Logic to get similar posts
        return null;
    }

    @Override
    public Page<Post> getPostsYouMayLike(String userId, Pageable pageable) {
        // Logic to get posts you may like
        return null;
    }

    @Override
    public Page<Post> getPostsFromFollowing(String userId, Pageable pageable) {
        // Logic to get posts from following
        return null;
    }

    @Override
    public Page<Post> getPostsLikedByUser(String userId, Pageable pageable) {
        // Logic to get posts liked by user
        return null;
    }

    @Override
    public Page<Post> getPostsSavedByUser(String userId, Pageable pageable) {
        // Logic to get posts saved by user
        return null;
    }

    @Override
    public Page<Post> getPostsSharedByUser(String userId, Pageable pageable) {
        // Logic to get posts shared by user
        return null;
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> getDeletedPosts(Pageable pageable) {
        return postRepository.findByIsDeletedTrue(pageable);
    }

    @Override
    public void permanentlyDeletePost(String postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public void purgeOldPosts(LocalDateTime threshold) {
        // Logic to purge old posts
    }

    @Override
    public void processScheduledPosts() {
        // Logic to process scheduled posts
    }

    @Override
    public void cleanupExpiredPolls() {
        // Logic to cleanup expired polls
    }

    @Override
    public void updatePostMetrics() {
        // Logic to update post metrics
    }

    @Override
    public void generateDailyReports() {
        // Logic to generate daily reports
    }

    @Override
    public boolean validatePostContent(Post post) {
        if (post == null) {
            return false;
        }

        // Check content length
        if (post.getContent() != null && post.getContent().length() > 5000) {
            return false;
        }

        // Check for inappropriate content (basic check)
        if (post.getContent() != null && containsInappropriateContent(post.getContent())) {
            return false;
        }

        // Check media URLs
        if (post.getMediaUrls() != null && post.getMediaUrls().size() > 10) {
            return false;
        }

        return true;
    }

    private boolean containsInappropriateContent(String content) {
        // Basic check for inappropriate words (this should be expanded with a proper content moderation service)
        String[] inappropriateWords = {"spam", "inappropriate", "offensive"};
        String lowerContent = content.toLowerCase();
        for (String word : inappropriateWords) {
            if (lowerContent.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkPostPermissions(String postId, String userId) {
        try {
            Post post = getPostById(postId);
            // Check if user owns the post or has admin privileges
            return userId.equals(post.getUserId()) || "ADMIN".equals(userId);
        } catch (PostNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean isPostVisibleToUser(String postId, String userId) {
        try {
            Post post = getPostById(postId);

            // Check if post is deleted
            if (post.isDeleted()) {
                return false;
            }

            // Check visibility
            if ("PUBLIC".equals(post.getVisibility())) {
                return true;
            } else if ("PRIVATE".equals(post.getVisibility())) {
                return userId.equals(post.getUserId());
            } else if ("FOLLOWERS".equals(post.getVisibility())) {
                // This would need to check if userId follows the post owner
                // For now, assume it's visible if not private
                return true;
            }

            return false;
        } catch (PostNotFoundException e) {
            return false;
        }
    }

    @Override
    public Set<String> extractHashtags(String content) {
        if (content == null || content.isEmpty()) {
            return new HashSet<>();
        }

        Set<String> hashtags = new HashSet<>();
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            hashtags.add(matcher.group(1).toLowerCase());
        }

        return hashtags;
    }

    @Override
    public Set<String> extractMentions(String content) {
        if (content == null || content.isEmpty()) {
            return new HashSet<>();
        }

        Set<String> mentions = new HashSet<>();
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            mentions.add(matcher.group(1));
        }

        return mentions;
    }

    @Override
    public void evictPostCache(String postId) {
        // Logic to evict post cache
    }

    @Override
    public void evictUserPostsCache(String userId) {
        // Logic to evict user posts cache
    }

    @Override
    public void evictTrendingPostsCache() {
        // Logic to evict trending posts cache
    }

    @Override
    public void publishPostCreatedEvent(Post post) {
        // Logic to publish post created event
    }

    @Override
    public void publishPostUpdatedEvent(Post post) {
        // Logic to publish post updated event
    }

    @Override
    public void publishPostDeletedEvent(String postId) {
        // Logic to publish post deleted event
    }

    @Override
    public void publishPostEngagementEvent(String postId, String eventType, String userId) {
        // Logic to publish post engagement event
    }

    @Override
    public Map<String, Object> getServiceHealth() {
        // Logic to get service health
        return null;
    }

    @Override
    public Map<String, Object> getPerformanceMetrics() {
        // Logic to get performance metrics
        return null;
    }

    @Override
    public Map<String, Object> getUsageStatistics() {
        // Logic to get usage statistics
        return null;
    }

    @Override
    public void migratePostsFromLegacySystem() {
        // Logic to migrate posts from legacy system
    }

    @Override
    public void synchronizeWithExternalSystems() {
        // Logic to synchronize with external systems
    }

    @Override
    public void validateDataConsistency() {
        // Logic to validate data consistency
    }
}
