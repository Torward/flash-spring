package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<ru.lomov.flashbackend.entities.Location, String> {
    // Custom query methods if needed
}
