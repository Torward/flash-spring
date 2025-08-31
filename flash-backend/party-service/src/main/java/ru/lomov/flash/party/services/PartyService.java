package ru.lomov.flash.party.services;

import ru.lomov.flash.party.entities.Party;
import java.util.List;
import java.util.Optional;

public interface PartyService {
    Party createParty(Party party);
    Optional<Party> getPartyById(String id);
    List<Party> getAllParties();
    Party updateParty(String id, Party party);
    void deleteParty(String id);
}
