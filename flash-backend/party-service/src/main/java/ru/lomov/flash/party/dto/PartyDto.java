package ru.lomov.flash.party.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyDto {
    private String id;
    private String name;
    private String description;
    private List<String> users;
    private List<String> chats;
    private String video;
    private String privacy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
