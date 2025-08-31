package ru.lomov.flash.party.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.party.entities.Party;
import ru.lomov.flash.party.repositories.PartyRepository;
import ru.lomov.flash.party.exceptions.PartyNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;

    @Override
    public Party createParty(Party party) {
        return partyRepository.save(party);
    }

    @Override
    public Optional<Party> getPartyById(String id) {
        return partyRepository.findById(id);
    }

    @Override
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    @Override
    public Party updateParty(String id, Party party) {
        Party existingParty = partyRepository.findById(id)
            .orElseThrow(() -> new PartyNotFoundException("Party not found with id: " + id));

        // Update fields
        existingParty.setName(party.getName());
        existingParty.setDescription(party.getDescription());
        existingParty.setUsers(party.getUsers());
        existingParty.setChats(party.getChats());
        existingParty.setVideo(party.getVideo());
        existingParty.setPrivacy(party.getPrivacy());

        return partyRepository.save(existingParty);
    }

    @Override
    public void deleteParty(String id) {
        Party party = partyRepository.findById(id)
            .orElseThrow(() -> new PartyNotFoundException("Party not found with id: " + id));

        partyRepository.delete(party);
    }
}
