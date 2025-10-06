package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
    List<Group> findByCreatorId(String creatorId);
    List<Group> findByParticipantsContaining(String userId);
    List<Group> findByNameContainingIgnoreCase(String name);
}
