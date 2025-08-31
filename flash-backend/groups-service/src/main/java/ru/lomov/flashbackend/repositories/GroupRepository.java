package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByCreatorId(Long creatorId);
    List<Group> findByParticipantsContaining(Long userId);
    List<Group> findByNameContainingIgnoreCase(String name);
}
