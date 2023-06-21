package me.vegura.songservice.service;

import lombok.RequiredArgsConstructor;
import me.vegura.songservice.domain.Song;
import me.vegura.songservice.dto.SongDeleteResponse;
import me.vegura.songservice.dto.SongGetResponse;
import me.vegura.songservice.dto.SongRequest;
import me.vegura.songservice.dto.SongSaveResponse;
import me.vegura.songservice.repo.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Override
    public SongSaveResponse createSong(SongRequest request) {
        Song savedSong = songRepository.save(this.fromRequest(request));
        return new SongSaveResponse().setId(savedSong.getId());
    }

    @Override
    public Optional<SongGetResponse> getSongBy(Long id) {
        return songRepository.findById(id).map(this::fromSongEntity);
    }

    @Override
    public Optional<SongGetResponse> getSongByResource(String resourceId) {
        return songRepository.findSongByResourceId(resourceId).map(this::fromSongEntity);
    }

    @Override
    public SongDeleteResponse deleteSongsBy(List<Long> ids) {
        SongDeleteResponse deleteResponse = new SongDeleteResponse();
        ids.stream()
                .filter(songRepository::existsById)
                .peek(deleteResponse::addId)
                .forEach(songRepository::deleteById);

        return deleteResponse;
    }

    private SongGetResponse fromSongEntity(Song song) {
        return (SongGetResponse) new SongGetResponse()
                .setId(song.getId())
                .setName(song.getName())
                .setYear(song.getYear())
                .setAlbum(song.getAlbum())
                .setResourceId(song.getResourceId())
                .setArtist(song.getArtist())
                .setLength(song.getLength());
    }

    private Song fromRequest(SongRequest request) {
        return Song.builder()
                .name(request.getName())
                .artist(request.getArtist())
                .album(request.getAlbum())
                .year(request.getYear())
                .resourceId(request.getResourceId())
                .length(request.getLength())
                .build();
    }
}
