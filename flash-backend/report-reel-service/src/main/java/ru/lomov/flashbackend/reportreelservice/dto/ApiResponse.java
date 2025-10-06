package ru.lomov.flashbackend.reportreelservice.dto;

import lombok.Data;

@Data
public class ApiResponse {
    private boolean success;
    private String message;
}