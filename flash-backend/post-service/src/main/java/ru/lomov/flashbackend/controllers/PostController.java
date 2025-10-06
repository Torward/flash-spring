package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.services.PostService;
import ru.lomov.flashbackend.dto.UpdatePostTypeDto;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable String postId) {
        try {
            Post post = postService.getPostById(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable String postId,
            @RequestBody Post post,
            @RequestHeader("X-User-Id") String userId) {
        try {
            // Check if user has permission to update this post
            if (!postService.checkPostPermissions(postId, userId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Post updatedPost = postService.updatePost(postId, post);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable String postId,
            @RequestHeader("X-User-Id") String userId) {
        try {
            // Check if user has permission to delete this post
            if (!postService.checkPostPermissions(postId, userId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            postService.deletePost(postId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Post>> getUserPosts(@PathVariable String userId, Pageable pageable) {
        Page<Post> posts = postService.getUserPosts(userId, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/public/user/{userId}")
    public ResponseEntity<Page<Post>> getUserPublicPosts(@PathVariable String userId, Pageable pageable) {
        Page<Post> posts = postService.getUserPublicPosts(userId, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<Page<Post>> getFeedPosts(@PathVariable String userId, Pageable pageable) {
        Page<Post> posts = postService.getFeedPosts(userId, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/discover")
    public ResponseEntity<Page<Post>> getDiscoverPosts(Pageable pageable) {
        Page<Post> posts = postService.getDiscoverPosts(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/trending")
    public ResponseEntity<Page<Post>> getTrendingPosts(Pageable pageable) {
        Page<Post> posts = postService.getTrendingPosts(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<Page<Post>> getPopularPosts(Pageable pageable) {
        Page<Post> posts = postService.getPopularPosts(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Post>> searchPosts(@RequestParam String query, Pageable pageable) {
        Page<Post> posts = postService.searchPosts(query, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Like endpoints
    @PostMapping("/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable String postId, @RequestParam String userId) {
        try {
            Post post = postService.likePost(postId, userId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Post> unlikePost(@PathVariable String postId, @RequestParam String userId) {
        try {
            Post post = postService.unlikePost(postId, userId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Post type management endpoint
    @PatchMapping("/{postId}/type")
    public ResponseEntity<Post> updatePostType(
            @PathVariable String postId,
            @RequestBody UpdatePostTypeDto updatePostTypeDto,
            @RequestHeader("X-User-Id") String userId) {
        try {
            // Check if user has permission to update this post
            if (!postService.checkPostPermissions(postId, userId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Post updatedPost = postService.updatePostType(postId, updatePostTypeDto);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
