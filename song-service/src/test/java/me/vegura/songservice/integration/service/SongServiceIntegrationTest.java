package me.vegura.songservice.integration.service;

import me.vegura.songservice.domain.Song;
import me.vegura.songservice.dto.SongRequest;
import me.vegura.songservice.dto.SongSaveResponse;
import me.vegura.songservice.repo.SongRepository;
import me.vegura.songservice.service.SongService;
import me.vegura.songservice.service.SongServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SongServiceIntegrationTest {

    @Autowired
    private SongRepository songRepository;

    private SongService songService;

    private static final Long SONG_DB_ID = 1L;
    private static final String SONG_NAME = "Say My Name";
    private static final String SONG_ARTIST = "Dj Protein";
    private static final Integer SONG_YEAR = 2018;
    private static final UUID SONG_RESOURCE_ID = UUID.randomUUID();
    private static final String SONG_ALBUM = "Live g House";
    private static final Long SONG_LENGTH = 297L;

    @BeforeAll
    public void setUp() {
        songService = new SongServiceImpl(songRepository);
    }

    @Test
    public void testSavingSongMetadata() {
        var songRequest = new SongRequest()
                .setName(SONG_NAME)
                .setArtist(SONG_ARTIST)
                .setYear(SONG_YEAR.toString())
                .setResourceId(SONG_RESOURCE_ID.toString())
                .setAlbum(SONG_ALBUM)
                .setLength(SONG_LENGTH);

        var song = Song.builder()
                .name(SONG_NAME)
                .artist(SONG_ARTIST)
                .year(SONG_YEAR.toString())
                .resourceId(SONG_RESOURCE_ID.toString())
                .album(SONG_ALBUM)
                .length(SONG_LENGTH)
                .build();

        SongSaveResponse songActualSavingResponse = songService.createSong(songRequest);
        Song savedSong = songRepository.findById(songActualSavingResponse.getId()).get();

        assertEquals(song.getName(), savedSong.getName());
        assertEquals(song.getArtist(), savedSong.getArtist());
        assertEquals(song.getAlbum(), savedSong.getAlbum());
        assertEquals(song.getYear(), savedSong.getYear());
        assertEquals(song.getLength(), savedSong.getLength());
        assertEquals(song.getResourceId(), savedSong.getResourceId());

        songRepository.deleteById(savedSong.getId());
    }
}
