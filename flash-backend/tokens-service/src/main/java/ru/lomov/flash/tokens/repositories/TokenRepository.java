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
public interface TokenRepository extends JpaRepository<Token, Long> {
    
    List<Token> findByUserId(String userId);
    
    Optional<Token> findByToken(String token);
    
    Optional<Token> findByUserIdAndToken(String userId, String token);
    
    Optional<Token> findByUserIdAndDeviceId(String userId, String deviceId);
    
    @Modifying
    @Query("DELETE FROM Token t WHERE t.userId = :userId AND t.token = :token")
    void deleteByUserIdAndToken(@Param("userId") String userId, @Param("token") String token);
    
    @Modifying
    @Query("DELETE FROM Token t WHERE t.userId = :userId")
    void deleteAllByUserId(@Param("userId") String userId);
    
    boolean existsByUserIdAndToken(String userId, String token);
}
