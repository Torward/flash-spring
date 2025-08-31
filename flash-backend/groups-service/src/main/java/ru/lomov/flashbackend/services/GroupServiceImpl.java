package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.Group;
import ru.lomov.flashbackend.repositories.GroupRepository;
import ru.lomov.flashbackend.exceptions.GroupNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    
    private final GroupRepository groupRepository;
    
    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }
    
    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
            .orElseThrow(() -> new GroupNotFoundException("Group not found with id: " + id));
    }
    
    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
    
    @Override
    public Group updateGroup(Long id, Group group) {
        Group existingGroup = groupRepository.findById(id)
            .orElseThrow(() -> new GroupNotFoundException("Group not found with id: " + id));
        
        existingGroup.setName(group.getName());
        existingGroup.setDescription(group.getDescription());
        existingGroup.setPrivacy(group.getPrivacy());
        existingGroup.setParticipants(group.getParticipants());
        
        return groupRepository.save(existingGroup);
    }
    
    @Override
    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
            .orElseThrow(() -> new GroupNotFoundException("Group not found with id: " + id));
        
        groupRepository.delete(group);
    }
    
    @Override
    public List<Group> getGroupsByCreatorId(Long creatorId) {
        return groupRepository.findByCreatorId(creatorId);
    }
    
    @Override
    public List<Group> getGroupsByParticipant(Long userId) {
        return groupRepository.findByParticipantsContaining(userId);
    }
    
    @Override
    public List<Group> searchGroupsByName(String name) {
        return groupRepository.findByNameContainingIgnoreCase(name);
    }
}
