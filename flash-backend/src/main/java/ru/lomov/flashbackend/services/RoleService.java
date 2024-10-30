package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(Long id);
    Role save(Role role);
    void delete(Role role);
    Role findByName(String name);
    Role update(Role role);
    List<Role> findRolesByUser(AppUser user);
    void assignRoleToUser(AppUser  user, Role role);
    void removeRoleFromUser(AppUser  user, Role role);
}
