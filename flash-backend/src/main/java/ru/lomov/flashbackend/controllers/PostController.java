
package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.lomov.flashbackend.dto.PostDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.request.PostReplyRequest;
import ru.lomov.flashbackend.responses.ApiResponse;
import ru.lomov.flashbackend.services.FileStorageService;
import ru.lomov.flashbackend.services.LikeService;
import ru.lomov.flashbackend.services.PostService;
import ru.lomov.flashbackend.services.UserService;
import ru.lomov.flashbackend.utils.PostDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final LikeService likeService;
    private final FileStorageService fileStorageService;

    @PostMapping
    public PostDto createPost(@RequestBody Post req, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createPost(req, user);
        PostDto postDto = PostDtoMapper.postToDto(post, user);
        return postDto;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.save(file); // Ваш сервис для сохранения файла
            String fileUrl = "/uploads/" + fileName; // Или URL облачного хранилища
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка загрузки");
        }
    }

    @PostMapping("/reply")
    public PostDto createReply(@RequestBody PostReplyRequest req, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createReply(req, user);
        PostDto postDto = PostDtoMapper.postToDto(post, user);
        return postDto;
    }

    @PutMapping("/{postId}/repost")
    public PostDto repost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Post post = postService.repost(postId, user);
        PostDto postDto = PostDtoMapper.postToDto(post, user);
        return postDto;
    }
    @PutMapping("/{postId}/save")
    public ResponseEntity<Post> save(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Post post = postService.savePost(postId, user.getUserId());
        return ResponseEntity.ok(post);
    }
    @GetMapping("/{postId}")
    public PostDto findPostByPostId(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Post post = postService.findById(postId);
        PostDto postDto = PostDtoMapper.postToDto(post, user);
        return postDto;
    }

    @GetMapping
    public List<PostDto> findAllPosts(@RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        List<Post> posts = postService.findAllPosts();
        List<PostDto> postDtos = PostDtoMapper.postToDto(posts, user);
        return postDtos;
    }

    @GetMapping("/user/{userId}")
    public List<PostDto> findAllUsersPost(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        AppUser reqUser = userService.findUserById(userId);
        List<Post> posts = postService.getUserPosts(reqUser);
        List<PostDto> postDtos = PostDtoMapper.postToDto(posts, user);
        return postDtos;
    }

    @GetMapping("/user/{userId}/likes")
    public List<PostDto> findPostByLikesContainsUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        AppUser reqUser = userService.findUserById(userId);
        List<Post> posts = postService.findByLikesContainsUser(user);
        List<PostDto> postDtos = PostDtoMapper.postToDto(posts, user);
        return postDtos;
    }

    @GetMapping("/user/bookmarks")
    public List<PostDto> findPostByBookmarksContainsUser(@RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        List<Post> posts = user.getSavedPosts();
        List<PostDto> postDtos = PostDtoMapper.postToDto(posts, user);
        return postDtos;
    }

    @GetMapping("/user/{userId}/replies")
    public List<PostDto> findPostByRepliesContainsUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
//        AppUser reqUser = userService.findUserById(userId);
        List<Post> posts = postService.findByRepliesContainsUser(user);
        List<PostDto> postDtos = PostDtoMapper.postToDto(posts, user);
        return postDtos;
    }


    @DeleteMapping("/{postId}")
    public ApiResponse delete(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        postService.deletePostById(postId, user.getUserId());
        return new ApiResponse("Пост удалён!", true);
    }
//    @GetMapping("/user/{userId}")
//    public List<PostDto> getLikedPostsByUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
//        AppUser user = userService.findUserProfileByJwt(jwt);
//        AppUser reqUser = userService.findUserById(userId);
//        List<Post> likedPosts = likeService.getLikedPostsByUser(user);
//        // Преобразуем список постов в список DTO
//        return likedPosts.stream()
//                .map(post -> PostDtoMapper.postToDto(post, user))
//                .collect(Collectors.toList());
//    }
    

}