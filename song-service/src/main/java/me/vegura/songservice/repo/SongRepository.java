package me.vegura.songservice.repo;

import me.vegura.songservice.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SongRepository extends JpaRepository<Song, Long> {
}
