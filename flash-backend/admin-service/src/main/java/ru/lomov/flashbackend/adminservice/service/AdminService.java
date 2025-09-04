package ru.lomov.flashbackend.adminservice.service;

import ru.lomov.flashbackend.adminservice.dto.AdminDto;
import ru.lomov.flashbackend.adminservice.dto.CreateAdminDto;
import ru.lomov.flashbackend.adminservice.dto.UpdateAdminDto;
import ru.lomov.flashbackend.adminservice.entity.Admin;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    AdminDto createAdmin(CreateAdminDto createAdminDto);

    AdminDto getAdminById(UUID adminId);

    AdminDto getAdminByUserId(String userId);

    AdminDto getAdminByEmail(String email);

    List<AdminDto> getAllAdmins();

    List<AdminDto> getAdminsByRole(Admin.AdminRole role);

    List<AdminDto> getAdminsByStatus(Admin.AdminStatus status);

    List<AdminDto> getAdminsByRoleAndStatus(Admin.AdminRole role, Admin.AdminStatus status);

    AdminDto updateAdmin(java.util.UUID adminId, UpdateAdminDto updateAdminDto);

    void deleteAdmin(java.util.UUID adminId);

    void suspendAdmin(java.util.UUID adminId);

    void activateAdmin(java.util.UUID adminId);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    long getActiveAdminCount();

    List<AdminDto> getAdminsByPermission(String permission);

    boolean hasPermission(java.util.UUID adminId, Admin.AdminPermission permission);

    void updateAdminPermissions(java.util.UUID adminId, List<Admin.AdminPermission> permissions);
}
