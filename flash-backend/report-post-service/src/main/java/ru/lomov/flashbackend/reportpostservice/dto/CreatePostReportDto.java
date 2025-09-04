package ru.lomov.flashbackend.reportpostservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostReportDto {
    private String postId;
    private String reportedByUserId;
    private String reason;
}
