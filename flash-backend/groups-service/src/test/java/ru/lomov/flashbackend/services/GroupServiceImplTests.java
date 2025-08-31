package ru.lomov.flashbackend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lomov.flashbackend.entities.Group;
import ru.lomov.flashbackend.exceptions.GroupNotFoundException;
import ru.lomov.flashbackend.repositories.GroupRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTests {
    
    @Mock
    private GroupRepository groupRepository;
    
    @InjectMocks
    private GroupServiceImpl groupService;
    
    @Test
    void createGroup_ShouldSaveAndReturnGroup() {
        Group group = new Group(null, "Test Group", "Description", 1L, null, "public", null);
        Group savedGroup = new Group(1L, "Test Group", "Description", 1L, null, "public", null);
        
        when(groupRepository.save(any(Group.class))).thenReturn(savedGroup);
        
        Group result = groupService.createGroup(group);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Group", result.getName());
        
        verify(groupRepository).save(group);
    }
    
    @Test
    void getGroupById_ShouldReturnGroup() {
        Group group = new Group(1L, "Test Group", "Description", 1L, null, "public", null);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        
        Group result = groupService.getGroupById(1L);
        
        assertNotNull(result);
        assertEquals("Test Group", result.getName());
    }
    
    @Test
    void getGroupById_ShouldThrowException_WhenGroupNotFound() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(GroupNotFoundException.class, () -> groupService.getGroupById(1L));
    }
    
    @Test
    void updateGroup_ShouldUpdateAndReturnGroup() {
        Group existingGroup = new Group(1L, "Test Group", "Description", 1L, null, "public", null);
        Group updatedGroup = new Group(1L, "Updated Group", "Updated Description", 1L, null, "private", null);
        
        when(groupRepository.findById(1L)).thenReturn(Optional.of(existingGroup));
        when(groupRepository.save(any(Group.class))).thenReturn(updatedGroup);
        
        Group result = groupService.updateGroup(1L, updatedGroup);
        
        assertNotNull(result);
        assertEquals("Updated Group", result.getName());
        assertEquals("Updated Description", result.getDescription());
    }
    
    @Test
    void deleteGroup_ShouldDeleteGroup() {
        Group group = new Group(1L, "Test Group", "Description", 1L, null, "public", null);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        
        groupService.deleteGroup(1L);
        
        verify(groupRepository).delete(group);
    }
}
