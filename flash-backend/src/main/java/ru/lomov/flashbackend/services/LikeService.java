package ru.lomov.flashbackend.services;



import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;

import java.util.List;

public interface LikeService {
    AppLike likePost(Long postId, AppUser user) throws UserNotFoundException, PostNotFoundException;
    List<AppLike> getAllLikes(Long postId) throws PostNotFoundException;
    List<Post> getLikedPostsByUser(AppUser user);

}
