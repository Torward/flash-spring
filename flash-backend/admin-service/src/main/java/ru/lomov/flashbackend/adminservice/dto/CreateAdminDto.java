package ru.lomov.flashbackend.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import ru.lomov.flashbackend.adminservice.entity.Admin;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdminDto {
    private String userId;
    private String username;
    private String email;
    private Admin.AdminRole role;
    private Admin.AdminStatus status;
    private java.util.Set<Admin.AdminPermission> permissions;
    private String notes;
}
