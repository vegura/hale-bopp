package me.vegura.songservice.service;

import me.vegura.songservice.dto.SongDeleteResponse;
import me.vegura.songservice.dto.SongGetResponse;
import me.vegura.songservice.dto.SongRequest;
import me.vegura.songservice.dto.SongSaveResponse;

import java.util.List;
import java.util.Optional;

public interface SongService {
    SongSaveResponse createSong(SongRequest request);
    Optional<SongGetResponse> getSongBy(Long id);
    SongDeleteResponse deleteSongsBy(List<Long> ids);
}
