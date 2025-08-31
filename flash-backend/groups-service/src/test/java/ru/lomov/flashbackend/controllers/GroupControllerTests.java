package ru.lomov.flashbackend.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ru.lomov.flashbackend.entities.Group;
import ru.lomov.flashbackend.services.GroupService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupController.class)
class GroupControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @Test
    void createGroup_ShouldReturnCreated() throws Exception {
        Group group = new Group(1L, "Test Group", "Description", 1L, null, "public", LocalDateTime.now());
        when(groupService.createGroup(any(Group.class))).thenReturn(group);

        mockMvc.perform(post("/api/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Group\", \"description\": \"Description\", \"creatorId\": 1, \"privacy\": \"public\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Group"));
    }

    @Test
    void getGroupById_ShouldReturnGroup() throws Exception {
        Group group = new Group(1L, "Test Group", "Description", 1L, null, "public", LocalDateTime.now());
        when(groupService.getGroupById(1L)).thenReturn(group);

        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Group"));
    }

    @Test
    void getAllGroups_ShouldReturnListOfGroups() throws Exception {
        Group group1 = new Group(1L, "Test Group 1", "Description 1", 1L, null, "public", LocalDateTime.now());
        Group group2 = new Group(2L, "Test Group 2", "Description 2", 2L, null, "private", LocalDateTime.now());
        List<Group> groups = Arrays.asList(group1, group2);
        when(groupService.getAllGroups()).thenReturn(groups);

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Group 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Test Group 2"));
    }

    @Test
    void updateGroup_ShouldReturnUpdatedGroup() throws Exception {
        Group existingGroup = new Group(1L, "Test Group", "Description", 1L, null, "public", LocalDateTime.now());
        Group updatedGroup = new Group(1L, "Updated Group", "Updated Description", 1L, null, "private", LocalDateTime.now());
        when(groupService.updateGroup(1L, updatedGroup)).thenReturn(updatedGroup);

        mockMvc.perform(put("/api/groups/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Group\", \"description\": \"Updated Description\", \"creatorId\": 1, \"privacy\": \"private\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Group"));
    }

    @Test
    void deleteGroup_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/groups/1"))
                .andExpect(status().isNoContent());
    }
}
