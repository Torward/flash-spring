package ru.lomov.flashbackend.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import ru.lomov.flashbackend.adminservice.entity.Admin;
import java.util.UUID;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {
    private String adminId;
    private String userId;
    private String username;
    private String email;
    private Admin.AdminRole role;
    private Admin.AdminStatus status;
    private Set<Admin.AdminPermission> permissions;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Boolean canManageUsers;
    private Boolean canManageContent;
    private Boolean canViewReports;
    private Boolean canManageAdmins;
    private Boolean hasSystemConfig;
    private Boolean hasFinancialAccess;
    private Boolean canDeleteContent;
    private Boolean canBanUsers;
    private Boolean canViewAnalytics;
}
