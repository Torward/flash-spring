
package ru.lomov.flash.party.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.party.entities.Party;
import ru.lomov.flash.party.services.PartyService;
import ru.lomov.flash.party.dto.PartyDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @PostMapping
    public ResponseEntity<PartyDto> createParty(@RequestBody PartyDto partyDto) {
        Party party = mapToEntity(partyDto);
        Party createdParty = partyService.createParty(party);
        PartyDto response = mapToDto(createdParty);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartyDto> getPartyById(@PathVariable String id) {
        Optional<Party> party = partyService.getPartyById(id);
        if (party.isPresent()) {
            PartyDto response = mapToDto(party.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PartyDto>> getAllParties() {
        List<Party> parties = partyService.getAllParties();
        List<PartyDto> responses = parties.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartyDto> updateParty(@PathVariable String id, @RequestBody PartyDto partyDto) {
        Party party = mapToEntity(partyDto);
        Party updatedParty = partyService.updateParty(id, party);
        PartyDto response = mapToDto(updatedParty);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable String id) {
        partyService.deleteParty(id);
        return ResponseEntity.noContent().build();
    }

    private Party mapToEntity(PartyDto dto) {
        Party party = new Party();
        party.setId(dto.getId());
        party.setName(dto.getName());
        party.setDescription(dto.getDescription());
        party.setUsers(dto.getUsers());
        party.setChats(dto.getChats());
        party.setVideo(dto.getVideo());
        party.setPrivacy(dto.getPrivacy());
        party.setCreatedAt(dto.getCreatedAt());
        party.setUpdatedAt(dto.getUpdatedAt());
        return party;
    }

    private PartyDto mapToDto(Party party) {
        PartyDto dto = new PartyDto();
        dto.setId(party.getId());
        dto.setName(party.getName());
        dto.setDescription(party.getDescription());
        dto.setUsers(party.getUsers());
        dto.setChats(party.getChats());
        dto.setVideo(party.getVideo());
        dto.setPrivacy(party.getPrivacy());
        dto.setCreatedAt(party.getCreatedAt());
        dto.setUpdatedAt(party.getUpdatedAt());
        return dto;
    }
}
