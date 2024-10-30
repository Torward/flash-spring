package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    @Query("select r from Role r where r.appUser.userId=:userId")
    List<Role> findByAppUser_userId(@Param("userId")Long userId);
//    @Query("select r from Role r join user_roles ur on r.id = ur.role_id join app_user u on ur.user_id = u.id where u.id = :userId")
    List<Role> findRolesByAppUser(AppUser appUser);
    Optional<Role> findById(Long id);
    List<Role> findAll();
}
