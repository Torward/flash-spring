package ru.lomov.flashbackend.reportuserservice.dto;

import lombok.Data;

@Data
public class ApiResponse {
    private boolean success;
    private String message;
}