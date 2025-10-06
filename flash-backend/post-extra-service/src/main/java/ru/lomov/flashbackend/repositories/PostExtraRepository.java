package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.PostExtra;

@Repository
public interface PostExtraRepository extends JpaRepository<PostExtra, String> {
    // Custom query methods if needed
}
