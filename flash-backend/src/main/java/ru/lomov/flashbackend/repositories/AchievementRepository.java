package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.Achievement;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    // Custom query method to find achievements by category and date achieved
    List<Achievement> findByCategoryAndDateAchieved(String category, LocalDate dateAchieved);

    // Custom query method using JPQL to find achievements by points greater than a certain value
    @Query("SELECT a FROM Achievement a WHERE a.points > :points")
    List<Achievement> findByPointsGreaterThan(@Param("points") int points);

    @Query("SELECT a FROM Achievement a WHERE a.name = :name")
    Achievement findByName(@Param("name") String name);

    @Query("SELECT a FROM Achievement a WHERE a.description = :description")
    Achievement findByDescription(@Param("description") String description);

    @Query("SELECT a FROM Achievement a WHERE a.dateAchieved = :dateAchieved")
    Achievement findByDateAchieved(@Param("dateAchieved") LocalDate dateAchieved);

    @Query("SELECT a FROM Achievement a WHERE a.category = :category")
    Achievement findByCategory(@Param("category") String category);

    @Query("SELECT a FROM Achievement a WHERE a.points = :points")
    Achievement findByPoints(@Param("points") int points);

    @Query("SELECT a FROM Achievement a WHERE a.isCompleted = :completed")
    List<Achievement> findByCompleted(@Param("completed") boolean completed);

    @Query("SELECT a FROM Achievement a WHERE a.isCompleted = :completed AND a.name = :name")
    Achievement findByCompletedAndName(@Param("completed") boolean completed, @Param("name") String name);

    @Query("SELECT a FROM Achievement a WHERE a.isCompleted = :completed AND a.description = :description")
    Achievement findByCompletedAndDescription(@Param("completed") boolean completed, @Param("description") String description);

    @Query("SELECT a FROM Achievement a WHERE a.isCompleted = :completed AND a.dateAchieved = :dateAchieved")
    Achievement findByCompletedAndDateAchieved(@Param("completed") boolean completed, @Param("dateAchieved") LocalDate dateAchieved);

    @Query("SELECT a FROM Achievement a WHERE a.isCompleted = :completed AND a.category = :category")
    Achievement findByCompletedAndCategory(@Param("completed") boolean completed, @Param("category") String category);

    @Query("SELECT a FROM Achievement a WHERE a.isCompleted = :completed AND a.points = :points")
    Achievement findByCompletedAndPoints(@Param("completed") boolean completed, @Param("points") int points);

    @Query("SELECT a FROM Achievement a WHERE a.isCompleted = :completed AND a.name = :name AND a.description = :description AND a.dateAchieved = :dateAchieved AND a.category = :category AND a.points = :points")
    Achievement findByCompletedAndNameAndDescriptionAndDateAchievedAndCategoryAndPoints(@Param("completed") boolean completed, @Param("name") String name, @Param("description") String description, @Param("dateAchieved") LocalDate dateAchieved, @Param("category") String category, @Param("points") int points);

    @Modifying
    @Query("UPDATE Achievement a SET a.isCompleted = :completed WHERE a.id = :id")
    void updateCompleted(@Param("completed") boolean completed, @Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Achievement a WHERE a.id = :id")
    void deleteById(@Param("id") Long id);

}
