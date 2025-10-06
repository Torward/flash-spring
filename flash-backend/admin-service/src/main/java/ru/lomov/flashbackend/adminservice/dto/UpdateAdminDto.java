package ru.lomov.flashbackend.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import ru.lomov.flashbackend.adminservice.entity.Admin;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAdminDto {
    private String username;
    private String email;
    private Admin.AdminRole role;
    private Admin.AdminStatus status;
    private Set<Admin.AdminPermission> permissions;
    private String notes;
}
