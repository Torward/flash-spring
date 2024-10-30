package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.entities.Share;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.repositories.PostRepository;
import ru.lomov.flashbackend.repositories.ShareRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService{
    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final ru.lomov.flashbackend.services.PostService postService;

    @Override
    public Share sharePost(Long postId, AppUser user) throws UserNotFoundException, PostNotFoundException {
        Share isShareExist = shareRepository.isShareExist(user.getUserId(), postId);
        if (isShareExist !=null){
            shareRepository.deleteById(isShareExist.getId());
            return isShareExist;
        }
        Post post = new Post();
        Share share = new Share();
        share.setPost(post);
        share.setAppUser(user);
        Share savedShare = shareRepository.save(share);
        post.getShares().add(savedShare);
        postRepository.save(post);
        return savedShare;
    }

    @Override
    public List<Share> getAllShares(Long postId) throws PostNotFoundException {
        Post post = postService.findById(postId);
        List<Share> shares = shareRepository.findAllByPostId(postId);
        return shares;
    }
}
