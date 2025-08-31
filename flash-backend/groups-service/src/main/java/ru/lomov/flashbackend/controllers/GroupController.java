package ru.lomov.flashbackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.entities.Group;
import ru.lomov.flashbackend.services.GroupService;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@Tag(name = "Groups", description = "API for managing groups")
public class GroupController {
    
    private final GroupService groupService;
    
    @PostMapping
    @Operation(summary = "Create a new group")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = groupService.createGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get group by ID")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }
    
    @GetMapping
    @Operation(summary = "Get all groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update group")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group group) {
        Group updatedGroup = groupService.updateGroup(id, group);
        return ResponseEntity.ok(updatedGroup);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete group")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/creator/{creatorId}")
    @Operation(summary = "Get groups by creator ID")
    public ResponseEntity<List<Group>> getGroupsByCreatorId(@PathVariable Long creatorId) {
        List<Group> groups = groupService.getGroupsByCreatorId(creatorId);
        return ResponseEntity.ok(groups);
    }
    
    @GetMapping("/participant/{userId}")
    @Operation(summary = "Get groups by participant ID")
    public ResponseEntity<List<Group>> getGroupsByParticipant(@PathVariable Long userId) {
        List<Group> groups = groupService.getGroupsByParticipant(userId);
        return ResponseEntity.ok(groups);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search groups by name")
    public ResponseEntity<List<Group>> searchGroupsByName(@RequestParam String name) {
        List<Group> groups = groupService.searchGroupsByName(name);
        return ResponseEntity.ok(groups);
    }
    
    @PostMapping("/{groupId}/participants/{userId}")
    @Operation(summary = "Add participant to group")
    public ResponseEntity<Group> addParticipant(@PathVariable Long groupId, @PathVariable Long userId) {
        Group group = groupService.getGroupById(groupId);
        group.getParticipants().add(userId);
        Group updatedGroup = groupService.updateGroup(groupId, group);
        return ResponseEntity.ok(updatedGroup);
    }
    
    @DeleteMapping("/{groupId}/participants/{userId}")
    @Operation(summary = "Remove participant from group")
    public ResponseEntity<Group> removeParticipant(@PathVariable Long groupId, @PathVariable Long userId) {
        Group group = groupService.getGroupById(groupId);
        group.getParticipants().remove(userId);
        Group updatedGroup = groupService.updateGroup(groupId, group);
        return ResponseEntity.ok(updatedGroup);
    }
}
