package me.vegura.songservice.service;

import me.vegura.songservice.domain.Song;
import me.vegura.songservice.dto.SongGetResponse;
import me.vegura.songservice.dto.SongRequest;
import me.vegura.songservice.dto.SongSaveResponse;
import me.vegura.songservice.repo.SongRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SongServiceTest {

    @Mock
    SongRepository songRepository = Mockito.mock(SongRepository.class);

    @InjectMocks
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
    public void testSavingSong() {
        var songRequest = new SongRequest()
                .setName(SONG_NAME)
                .setArtist(SONG_ARTIST)
                .setYear(SONG_YEAR.toString())
                .setResourceId(SONG_RESOURCE_ID.toString())
                .setAlbum(SONG_ALBUM)
                .setLength(SONG_LENGTH);

        var song = Song.builder()
                .id(SONG_DB_ID)
                .name(SONG_NAME)
                .artist(SONG_ARTIST)
                .year(SONG_YEAR.toString())
                .resourceId(SONG_RESOURCE_ID.toString())
                .album(SONG_ALBUM)
                .length(SONG_LENGTH)
                .build();

        var songSaveResponse = new SongSaveResponse()
                .setId(SONG_DB_ID);

        when(songRepository.save(any(Song.class))).thenReturn(song);

        SongSaveResponse songActualSavingResponse = songService.createSong(songRequest);

        assertEquals(songSaveResponse, songActualSavingResponse);
        verify(songRepository, times(1)).save(any(Song.class));
    }

    @Test
    public void testSaveSongById() {
        var song = Song.builder()
                .id(SONG_DB_ID)
                .name(SONG_NAME)
                .artist(SONG_ARTIST)
                .year(SONG_YEAR.toString())
                .resourceId(SONG_RESOURCE_ID.toString())
                .album(SONG_ALBUM)
                .length(SONG_LENGTH)
                .build();

        var songGetResponse = (SongGetResponse) new SongGetResponse()
                .setId(SONG_DB_ID)
                .setName(SONG_NAME)
                .setLength(SONG_LENGTH)
                .setAlbum(SONG_ALBUM)
                .setArtist(SONG_ARTIST)
                .setYear(SONG_YEAR.toString())
                .setResourceId(SONG_RESOURCE_ID.toString());

        when(songRepository.findById(SONG_DB_ID)).thenReturn(Optional.ofNullable(song));

        Optional<SongGetResponse> actualGetSongResponse = songService.getSongBy(SONG_DB_ID);

        assertEquals(actualGetSongResponse.get(), songGetResponse);
        verify(songRepository, times(1)).findById(SONG_DB_ID);
    }
}
