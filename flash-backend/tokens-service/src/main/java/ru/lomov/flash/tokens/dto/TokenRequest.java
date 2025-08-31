package ru.lomov.flash.tokens.dto;

import lombok.Data;

@Data
public class TokenRequest {
    private String token;
    private String deviceId;
}
