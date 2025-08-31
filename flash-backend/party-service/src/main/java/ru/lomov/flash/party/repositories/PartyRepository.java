package ru.lomov.flash.party.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.party.entities.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    // Custom query methods if needed
}
