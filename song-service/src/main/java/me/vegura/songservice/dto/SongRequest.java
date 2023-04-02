package me.vegura.songservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data @Builder @AllArgsConstructor @NoArgsConstructor @Accessors(chain = true)
public class SongRequest implements Serializable {
    @NotNull
    private String name;
    @NotNull
    private String artist;
    private String album;
    @NotNull
    private Long length;
    @NotNull
    private String resourceId;
    private String year;
}
