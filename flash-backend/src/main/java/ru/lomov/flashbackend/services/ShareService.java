package ru.lomov.flashbackend.services;


import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Share;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;

import java.util.List;

public interface ShareService {
    Share sharePost(Long postId, AppUser user) throws UserNotFoundException, PostNotFoundException;
    List<Share> getAllShares(Long postId) throws PostNotFoundException;
}
