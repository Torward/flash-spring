package ru.lomov.flashbackend.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.services.PostService;

public class PostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void testCreatePost() throws Exception {
        Post post = new Post();
        post.setPostId(1L);
        post.setContent("Test content");

        when(postService.createPost(any(Post.class))).thenReturn(post);

        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"Test content\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.postId").value(1));
    }

    @Test
    public void testGetPostById() throws Exception {
        Post post = new Post();
        post.setPostId(1L);
        post.setContent("Test content");

        when(postService.getPostById(1L)).thenReturn(post);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1));
    }

    @Test
    public void testUpdatePost() throws Exception {
        Post post = new Post();
        post.setPostId(1L);
        post.setContent("Updated content");

        when(postService.updatePost(eq(1L), any(Post.class))).thenReturn(post);

        mockMvc.perform(put("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"Updated content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Updated content"));
    }

    @Test
    public void testDeletePost() throws Exception {
        doNothing().when(postService).deletePost(1L, null);

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetUserPosts() throws Exception {
        mockMvc.perform(get("/api/posts/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserPublicPosts() throws Exception {
        mockMvc.perform(get("/api/posts/public/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFeedPosts() throws Exception {
        mockMvc.perform(get("/api/posts/feed/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDiscoverPosts() throws Exception {
        mockMvc.perform(get("/api/posts/discover"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTrendingPosts() throws Exception {
        mockMvc.perform(get("/api/posts/trending"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPopularPosts() throws Exception {
        mockMvc.perform(get("/api/posts/popular"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchPosts() throws Exception {
        mockMvc.perform(get("/api/posts/search?query=test"))
                .andExpect(status().isOk());
    }
}
