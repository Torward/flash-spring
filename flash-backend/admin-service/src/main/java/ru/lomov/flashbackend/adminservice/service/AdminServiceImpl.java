package ru.lomov.flashbackend.adminservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flashbackend.adminservice.dto.AdminDto;
import ru.lomov.flashbackend.adminservice.dto.CreateAdminDto;
import ru.lomov.flashbackend.adminservice.dto.UpdateAdminDto;
import ru.lomov.flashbackend.adminservice.entity.Admin;
import ru.lomov.flashbackend.adminservice.exception.AdminNotFoundException;
import ru.lomov.flashbackend.adminservice.exception.DuplicateAdminException;
import ru.lomov.flashbackend.adminservice.repository.AdminRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public AdminDto createAdmin(CreateAdminDto createAdminDto) {
        if (adminRepository.existsByUserId(createAdminDto.getUserId())) {
            throw new DuplicateAdminException("Admin with userId " + createAdminDto.getUserId() + " already exists");
        }
        if (adminRepository.existsByEmail(createAdminDto.getEmail())) {
            throw new DuplicateAdminException("Admin with email " + createAdminDto.getEmail() + " already exists");
        }

        Admin admin = Admin.builder()
                .userId(createAdminDto.getUserId())
                .username(createAdminDto.getUsername())
                .email(createAdminDto.getEmail())
                .role(createAdminDto.getRole())
                .status(createAdminDto.getStatus())
                .permissions(createAdminDto.getPermissions())
                .notes(createAdminDto.getNotes())
                .build();

        Admin savedAdmin = adminRepository.save(admin);
        return mapToDto(savedAdmin);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDto getAdminById(String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + adminId));
        return mapToDto(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDto getAdminByUserId(String userId) {
        Admin admin = adminRepository.findByUserId(userId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with userId: " + userId));
        return mapToDto(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDto getAdminByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with email: " + email));
        return mapToDto(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDto> getAdminsByRole(Admin.AdminRole role) {
        return adminRepository.findByRole(role).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDto> getAdminsByStatus(Admin.AdminStatus status) {
        return adminRepository.findByStatus(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDto> getAdminsByRoleAndStatus(Admin.AdminRole role, Admin.AdminStatus status) {
        return adminRepository.findByRoleAndStatus(role, status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDto updateAdmin(String adminId, UpdateAdminDto updateAdminDto) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + adminId));

        if (updateAdminDto.getUsername() != null) {
            admin.setUsername(updateAdminDto.getUsername());
        }
        if (updateAdminDto.getEmail() != null) {
            admin.setEmail(updateAdminDto.getEmail());
        }
        if (updateAdminDto.getRole() != null) {
            admin.setRole(updateAdminDto.getRole());
        }
        if (updateAdminDto.getStatus() != null) {
            admin.setStatus(updateAdminDto.getStatus());
        }
        if (updateAdminDto.getPermissions() != null) {
            admin.setPermissions(updateAdminDto.getPermissions());
        }
        if (updateAdminDto.getNotes() != null) {
            admin.setNotes(updateAdminDto.getNotes());
        }

        Admin updatedAdmin = adminRepository.save(admin);
        return mapToDto(updatedAdmin);
    }

    @Override
    public void deleteAdmin(String adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new AdminNotFoundException("Admin not found with id: " + adminId);
        }
        adminRepository.deleteById(adminId);
    }

    @Override
    public void suspendAdmin(String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + adminId));
        admin.setStatus(Admin.AdminStatus.SUSPENDED);
        adminRepository.save(admin);
    }

    @Override
    public void activateAdmin(String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + adminId));
        admin.setStatus(Admin.AdminStatus.ACTIVE);
        adminRepository.save(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserId(String userId) {
        return adminRepository.existsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public long getActiveAdminCount() {
        return adminRepository.countActiveAdmins();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDto> getAdminsByPermission(String permission) {
        return adminRepository.findByPermission(permission).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasPermission(String adminId, Admin.AdminPermission permission) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + adminId));
        return admin.getPermissions().contains(permission);
    }

    @Override
    public void updateAdminPermissions(String adminId, List<Admin.AdminPermission> permissions) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + adminId));
        admin.setPermissions(Set.copyOf(permissions));
        adminRepository.save(admin);
    }

    private AdminDto mapToDto(Admin admin) {
        return AdminDto.builder()
                .adminId(admin.getAdminId())
                .userId(admin.getUserId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .role(admin.getRole())
                .status(admin.getStatus())
                .permissions(admin.getPermissions())
                .notes(admin.getNotes())
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .isActive(admin.getStatus() == Admin.AdminStatus.ACTIVE)
                .canManageUsers(admin.getPermissions().contains(Admin.AdminPermission.MANAGE_USERS))
                .canManageContent(admin.getPermissions().contains(Admin.AdminPermission.MANAGE_CONTENT))
                .canViewReports(admin.getPermissions().contains(Admin.AdminPermission.VIEW_REPORTS))
                .canManageAdmins(admin.getPermissions().contains(Admin.AdminPermission.MANAGE_ADMINS))
                .hasSystemConfig(admin.getPermissions().contains(Admin.AdminPermission.SYSTEM_CONFIG))
                .hasFinancialAccess(admin.getPermissions().contains(Admin.AdminPermission.FINANCIAL_ACCESS))
                .canDeleteContent(admin.getPermissions().contains(Admin.AdminPermission.DELETE_CONTENT))
                .canBanUsers(admin.getPermissions().contains(Admin.AdminPermission.BAN_USERS))
                .canViewAnalytics(admin.getPermissions().contains(Admin.AdminPermission.VIEW_ANALYTICS))
                .build();
    }
}
