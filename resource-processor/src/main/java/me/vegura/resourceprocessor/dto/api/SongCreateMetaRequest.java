package me.vegura.resourceprocessor.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class SongCreateMetaRequest implements Serializable {
    private String name;
    private String artist;
    private String album;
    private Long length;
    private Long resourceId;
    private String year;
}
