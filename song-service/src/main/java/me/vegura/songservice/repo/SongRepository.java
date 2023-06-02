package me.vegura.songservice.repo;

import me.vegura.songservice.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM song WHERE resource_id = ?1")
    Optional<Song> findSongByResourceId(String resourceId);
}
