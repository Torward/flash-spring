package ru.lomov.flash.tokens.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.tokens.entities.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    Optional<Token> findByUserId(String userId);

    List<Token> findByUserIdAndIsActiveTrue(String userId);

    List<Token> findByDeviceId(String deviceId);

    @Query("SELECT t FROM Token t WHERE t.isActive = true")
    List<Token> findAllActiveTokens();

    @Modifying
    @Query("UPDATE Token t SET t.isActive = false WHERE t.userId = :userId AND t.deviceId = :deviceId")
    int deactivateTokenByUserAndDevice(@Param("userId") String userId, @Param("deviceId") String deviceId);

    @Modifying
    @Query("UPDATE Token t SET t.isActive = false WHERE t.userId = :userId")
    int deactivateAllTokensByUser(@Param("userId") String userId);

    @Query("SELECT COUNT(t) FROM Token t WHERE t.userId = :userId AND t.isActive = true")
    long countActiveTokensByUser(@Param("userId") String userId);
}
