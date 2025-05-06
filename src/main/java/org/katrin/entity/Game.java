package org.katrin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "game")
public class Game {
    @Id
    @Column(name = "game_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(name = "release_date", nullable = false)
    private Date releaseDate;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private double cost;

    @Column
    private String description;

    @Column(name = "creation_date")
    @ColumnDefault("CURRENT_DATE")
    @CreationTimestamp
    private Date creationDate;

    @Override
    public String toString() {
        return "id - " + id + ", name - " + name + ", type - " + type + ", release date - " + releaseDate +
                ", rating - " + rating + ", cost - " + cost + ", description - " + description + ", creation date - "
                + creationDate;
    }
}
