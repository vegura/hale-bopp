package me.vegura.songservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class SongSaveResponse {
    private Long id;
}
