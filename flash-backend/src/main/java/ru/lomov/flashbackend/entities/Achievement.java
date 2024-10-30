package ru.lomov.flashbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate dateAchieved;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String video;

    @Column(nullable = false)
    private boolean isCompleted;
}
