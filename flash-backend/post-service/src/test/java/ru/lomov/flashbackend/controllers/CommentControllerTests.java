package ru.lomov.flashbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.lomov.flashbackend.dto.CommentDto;
import ru.lomov.flashbackend.services.CommentService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTests {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private CommentDto testCommentDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        testCommentDto = CommentDto.builder()
                .id(1L)
                .postId(1L)
                .userId(1L)
                .content("Test comment content")
                .parentCommentId(null)
                .likeCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createComment_ShouldReturnCreatedComment() throws Exception {
        when(commentService.createComment(any(CommentDto.class))).thenReturn(testCommentDto);

        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCommentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("Test comment content"));

        verify(commentService, times(1)).createComment(any(CommentDto.class));
    }

    @Test
    void getCommentById_ShouldReturnComment() throws Exception {
        when(commentService.getCommentById(1L)).thenReturn(testCommentDto);

        mockMvc.perform(get("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("Test comment content"));

        verify(commentService, times(1)).getCommentById(1L);
    }

    @Test
    void getCommentsByPostId_ShouldReturnCommentsList() throws Exception {
        List<CommentDto> comments = Arrays.asList(testCommentDto);
        when(commentService.getCommentsByPostId(1L)).thenReturn(comments);

        mockMvc.perform(get("/api/comments/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].content").value("Test comment content"));

        verify(commentService, times(1)).getCommentsByPostId(1L);
    }

    @Test
    void updateComment_ShouldReturnUpdatedComment() throws Exception {
        when(commentService.updateComment(eq(1L), any(CommentDto.class))).thenReturn(testCommentDto);

        mockMvc.perform(put("/api/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCommentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(commentService, times(1)).updateComment(eq(1L), any(CommentDto.class));
    }

    @Test
    void deleteComment_ShouldReturnNoContent() throws Exception {
        doNothing().when(commentService).deleteComment(1L);

        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).deleteComment(1L);
    }

    @Test
    void getCommentCountByPostId_ShouldReturnCount() throws Exception {
        when(commentService.getCommentCountByPostId(1L)).thenReturn(5L);

        mockMvc.perform(get("/api/comments/post/1/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(commentService, times(1)).getCommentCountByPostId(1L);
    }

    @Test
    void likeComment_ShouldReturnUpdatedComment() throws Exception {
        CommentDto likedComment = CommentDto.builder()
                .id(1L)
                .likeCount(1)
                .build();
        
        when(commentService.likeComment(1L, 1L)).thenReturn(likedComment);

        mockMvc.perform(post("/api/comments/1/like/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likeCount").value(1));

        verify(commentService, times(1)).likeComment(1L, 1L);
    }

    @Test
    void unlikeComment_ShouldReturnUpdatedComment() throws Exception {
        CommentDto unlikedComment = CommentDto.builder()
                .id(1L)
                .likeCount(0)
                .build();
        
        when(commentService.unlikeComment(1L, 1L)).thenReturn(unlikedComment);

        mockMvc.perform(post("/api/comments/1/unlike/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likeCount").value(0));

        verify(commentService, times(1)).unlikeComment(1L, 1L);
    }
}
