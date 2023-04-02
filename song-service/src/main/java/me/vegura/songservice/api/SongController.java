package me.vegura.songservice.api;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import me.vegura.songservice.dto.SongDeleteResponse;
import me.vegura.songservice.dto.SongGetResponse;
import me.vegura.songservice.dto.SongRequest;
import me.vegura.songservice.dto.SongSaveResponse;
import me.vegura.songservice.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController @RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public SongSaveResponse createSongMeta(@Valid @RequestBody SongRequest request) {
        return songService.createSong(request);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getSong(@PathVariable("id") Long id) {
        Optional<SongGetResponse> maybeSong = songService.getSongBy(id);
        if (maybeSong.isEmpty())
            return ResponseEntity
                    .notFound()
                    .build();

        return ResponseEntity.ok(maybeSong.get());
    }

    @DeleteMapping
    public SongDeleteResponse deleteSongs(@RequestParam("ids") String ids) {
        List<Long> songIdsToDelete = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return songService.deleteSongsBy(songIdsToDelete);
    }
}
