package ru.lomov.flashbackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.entities.PostExtra;
import ru.lomov.flashbackend.services.PostExtraService;

@RestController
@RequestMapping("/api/post-extra")
@RequiredArgsConstructor
@Tag(name = "PostExtra", description = "API for managing post extra metadata")
public class PostExtraController {

    private final PostExtraService postExtraService;

    @PostMapping
    @Operation(summary = "Create post extra metadata")
    public ResponseEntity<PostExtra> createPostExtra(@RequestBody PostExtra postExtra) {
        PostExtra createdPostExtra = postExtraService.createPostExtra(postExtra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPostExtra);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get post extra metadata by post ID")
    public ResponseEntity<PostExtra> getPostExtraById(@PathVariable String postId) {
        return postExtraService.getPostExtraById(postId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{postId}")
    @Operation(summary = "Update post extra metadata")
    public ResponseEntity<PostExtra> updatePostExtra(@PathVariable String postId, @RequestBody PostExtra postExtra) {
        PostExtra updatedPostExtra = postExtraService.updatePostExtra(postId, postExtra);
        return ResponseEntity.ok(updatedPostExtra);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "Delete post extra metadata")
    public ResponseEntity<Void> deletePostExtra(@PathVariable String postId) {
        postExtraService.deletePostExtra(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}/exists")
    @Operation(summary = "Check if post extra metadata exists")
    public ResponseEntity<Boolean> existsByPostId(@PathVariable String postId) {
        boolean exists = postExtraService.existsByPostId(postId);
        return ResponseEntity.ok(exists);
    }
}
