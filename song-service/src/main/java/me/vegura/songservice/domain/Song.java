package me.vegura.songservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder @Data @AllArgsConstructor @NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "song_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String artist;

    private String album;

    @Column(name = "song_length", nullable = false)
    private Long length;

    @Column(nullable = false)
    private String resourceId;

    @Column(name = "song_year")
    private String year;
}
