package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.PostExtra;
import ru.lomov.flashbackend.repositories.PostExtraRepository;
import ru.lomov.flashbackend.exceptions.PostExtraNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostExtraServiceImpl implements PostExtraService {

    private final PostExtraRepository postExtraRepository;

    @Override
    public PostExtra createPostExtra(PostExtra postExtra) {
        return postExtraRepository.save(postExtra);
    }

    @Override
    public Optional<PostExtra> getPostExtraById(String postId) {
        return postExtraRepository.findById(postId);
    }

    @Override
    public PostExtra updatePostExtra(String postId, PostExtra postExtra) {
        PostExtra existingPostExtra = postExtraRepository.findById(postId)
            .orElseThrow(() -> new PostExtraNotFoundException("PostExtra not found with postId: " + postId));

        // Update fields
        existingPostExtra.setExtraData(postExtra.getExtraData());
        existingPostExtra.setMetadata(postExtra.getMetadata());

        return postExtraRepository.save(existingPostExtra);
    }

    @Override
    public void deletePostExtra(String postId) {
        PostExtra postExtra = postExtraRepository.findById(postId)
            .orElseThrow(() -> new PostExtraNotFoundException("PostExtra not found with postId: " + postId));

        postExtraRepository.delete(postExtra);
    }

    @Override
    public boolean existsByPostId(String postId) {
        return postExtraRepository.existsById(postId);
    }
}
