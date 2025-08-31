package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.Group;

import java.util.List;

public interface GroupService {
    Group createGroup(Group group);
    Group getGroupById(Long id);
    List<Group> getAllGroups();
    Group updateGroup(Long id, Group group);
    void deleteGroup(Long id);
    List<Group> getGroupsByCreatorId(Long creatorId);
    List<Group> getGroupsByParticipant(Long userId);
    List<Group> searchGroupsByName(String name);
}
