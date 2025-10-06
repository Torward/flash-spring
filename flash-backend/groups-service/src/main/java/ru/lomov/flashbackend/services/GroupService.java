package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.Group;

import java.util.List;

public interface GroupService {
    Group createGroup(Group group);
    Group getGroupById(String id);
    List<Group> getAllGroups();
    Group updateGroup(String id, Group group);
    void deleteGroup(String id);
    List<Group> getGroupsByCreatorId(String creatorId);
    List<Group> getGroupsByParticipant(String userId);
    List<Group> searchGroupsByName(String name);
}
