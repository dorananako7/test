package com.npbpredict.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "google_sub_id", unique = true, nullable = false)
    private String googleSubId;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(name = "favorite_team")
    private String favoriteTeam;

    @Column(name = "total_points", nullable = false)
    private Integer totalPoints = 0;
}
