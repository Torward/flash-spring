package ru.lomov.flashbackend.adminservice.service;

import ru.lomov.flashbackend.adminservice.dto.AdminDto;
import ru.lomov.flashbackend.adminservice.dto.CreateAdminDto;
import ru.lomov.flashbackend.adminservice.dto.UpdateAdminDto;
import ru.lomov.flashbackend.adminservice.entity.Admin;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    AdminDto createAdmin(CreateAdminDto createAdminDto);

    AdminDto getAdminById(String adminId);

    AdminDto getAdminByUserId(String userId);

    AdminDto getAdminByEmail(String email);

    List<AdminDto> getAllAdmins();

    List<AdminDto> getAdminsByRole(Admin.AdminRole role);

    List<AdminDto> getAdminsByStatus(Admin.AdminStatus status);

    List<AdminDto> getAdminsByRoleAndStatus(Admin.AdminRole role, Admin.AdminStatus status);

    AdminDto updateAdmin(String adminId, UpdateAdminDto updateAdminDto);

    void deleteAdmin(String adminId);

    void suspendAdmin(String adminId);

    void activateAdmin(String adminId);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    long getActiveAdminCount();

    List<AdminDto> getAdminsByPermission(String permission);

    boolean hasPermission(String adminId, Admin.AdminPermission permission);

    void updateAdminPermissions(String adminId, List<Admin.AdminPermission> permissions);
}
