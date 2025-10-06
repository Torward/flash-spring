package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.PostExtra;
import java.util.Optional;

public interface PostExtraService {
    PostExtra createPostExtra(PostExtra postExtra);
    Optional<PostExtra> getPostExtraById(String postId);
    PostExtra updatePostExtra(String postId, PostExtra postExtra);
    void deletePostExtra(String postId);
    boolean existsByPostId(String postId);
}
