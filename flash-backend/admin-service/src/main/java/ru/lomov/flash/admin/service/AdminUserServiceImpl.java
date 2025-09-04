package ru.lomov.flash.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.admin.entity.AdminUser;
import ru.lomov.flash.admin.repository.AdminUserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository adminUserRepository;

    @Override
    public AdminUser createAdminUser(AdminUser adminUser) {
        if (adminUser.getCreatedAt() == null) {
            adminUser.setCreatedAt(LocalDateTime.now());
        }
        return adminUserRepository.save(adminUser);
    }

    @Override
    public Optional<AdminUser> getAdminUserById(String id) {
        return adminUserRepository.findById(id);
    }

    @Override
    public Optional<AdminUser> getAdminUserByUserId(String userId) {
        return adminUserRepository.findByUserId(userId);
    }

    @Override
    public Optional<AdminUser> getAdminUserByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }

    @Override
    public Optional<AdminUser> getAdminUserByUsername(String username) {
        return adminUserRepository.findByUsername(username);
    }

    @Override
    public List<AdminUser> getAllAdminUsers() {
        return adminUserRepository.findAll();
    }

    @Override
    public List<AdminUser> getAdminUsersByRole(AdminUser.AdminRole role) {
        return adminUserRepository.findByRole(role);
    }

    @Override
    public List<AdminUser> getActiveAdminUsers() {
        return adminUserRepository.findByIsActiveTrue();
    }

    @Override
    public AdminUser updateAdminUser(String id, AdminUser adminUser) {
        Optional<AdminUser> existingUser = adminUserRepository.findById(id);
        if (existingUser.isPresent()) {
            AdminUser updatedUser = existingUser.get();
            updatedUser.setUsername(adminUser.getUsername());
            updatedUser.setEmail(adminUser.getEmail());
            updatedUser.setRole(adminUser.getRole());
            updatedUser.setPermissions(adminUser.getPermissions());
            return adminUserRepository.save(updatedUser);
        }
        throw new RuntimeException("Admin user not found with id: " + id);
    }

    @Override
    public void deleteAdminUser(String id) {
        adminUserRepository.deleteById(id);
    }

    @Override
    public boolean isUserAdmin(String userId) {
        return adminUserRepository.findByUserId(userId).isPresent();
    }

    @Override
    public boolean hasPermission(String userId, String permission) {
        Optional<AdminUser> adminUser = adminUserRepository.findByUserId(userId);
        if (adminUser.isPresent()) {
            String permissions = adminUser.get().getPermissions();
            return permissions != null && permissions.contains(permission);
        }
        return false;
    }

    @Override
    public void updateLastLogin(String userId) {
        Optional<AdminUser> adminUser = adminUserRepository.findByUserId(userId);
        if (adminUser.isPresent()) {
            AdminUser user = adminUser.get();
            user.setLastLogin(LocalDateTime.now());
            adminUserRepository.save(user);
        }
    }

    @Override
    public void deactivateAdminUser(String id) {
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        if (adminUser.isPresent()) {
            AdminUser user = adminUser.get();
            user.setIsActive(false);
            adminUserRepository.save(user);
        }
    }

    @Override
    public void activateAdminUser(String id) {
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        if (adminUser.isPresent()) {
            AdminUser user = adminUser.get();
            user.setIsActive(true);
            adminUserRepository.save(user);
        }
    }
}
