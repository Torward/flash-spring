package ru.lomov.flash.balance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.balance.entities.Balance;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, String> {

    Optional<Balance> findByUserId(String userId);

    @Modifying
    @Query("UPDATE Balance b SET b.balance = b.balance + :amount WHERE b.userId = :userId")
    int addToBalance(@Param("userId") String userId, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("UPDATE Balance b SET b.balance = b.balance - :amount WHERE b.userId = :userId AND b.balance >= :amount")
    int subtractFromBalance(@Param("userId") String userId, @Param("amount") BigDecimal amount);

    @Query("SELECT b.balance FROM Balance b WHERE b.userId = :userId")
    Optional<BigDecimal> findBalanceByUserId(@Param("userId") String userId);
}
