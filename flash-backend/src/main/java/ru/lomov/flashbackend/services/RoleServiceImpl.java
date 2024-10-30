package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Role;
import ru.lomov.flashbackend.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findRolesByUser(AppUser user) {
        return roleRepository.findRolesByAppUser(user);
    }

    @Override
    public void assignRoleToUser(AppUser user, Role role) {
        user.getRoles().add(role);
        roleRepository.save(role);
    }

    @Override
    public void removeRoleFromUser(AppUser user, Role role) {
        user.getRoles().remove(role);
        roleRepository.save(role);
    }
}