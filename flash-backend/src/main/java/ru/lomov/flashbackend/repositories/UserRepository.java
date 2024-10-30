package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.AppUser;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByEmail(String email);
    Optional<AppUser> findAppUserByFullName(String fullName);
    Optional<AppUser> findAppUserByUsername(String username);
    Optional<AppUser> findAppUserByEmailAndPassword(String email, String password);
    Optional<AppUser> findAppUserByUsernameAndPassword(String username, String password);
    Optional<AppUser> findAppUserByPhoneNumber(String phoneNumber);
    List<AppUser> findAppUserByFirstName(String firstName);
    List<AppUser> findAppUserByLastName(String lastName);

    @Query("SELECT u FROM AppUser u WHERE u.email LIKE %:query%")
    List<AppUser> searchUserByEmail(@Param("query") String query);

    @Query("SELECT u FROM AppUser u WHERE u.username LIKE %:query%")
    List<AppUser> searchUserByUsername(@Param("query") String query);

    @Query("SELECT u FROM AppUser u WHERE u.fullName LIKE %:query%")
    List<AppUser> searchUserByFullName(@Param("query") String query);





























    @Query("SELECT DISTINCT u FROM AppUser u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%")
    List<AppUser> searchUser(@Param("query") String query);
}
