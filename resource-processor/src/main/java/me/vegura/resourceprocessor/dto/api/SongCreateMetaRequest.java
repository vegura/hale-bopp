package me.vegura.resourceprocessor.dto.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.vegura.resourceprocessor.service.Mp3MetadataParser;

import java.io.Serializable;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SongCreateMetaRequest implements Serializable {
    private String name;
    private String artist;
    private String album;
    private Long length;
    private String resourceId;
    private String year;

    public static SongCreateMetaRequest fromSongMeta(Mp3MetadataParser.SongMetadata metadata, Long resourceId) {
        Long length = (long) Double.parseDouble(metadata.getLength());
        return SongCreateMetaRequest.builder()
                .name(metadata.getName())
                .artist(metadata.getArtist())
                .album(metadata.getAlbum())
                .resourceId(resourceId.toString())
                .year(metadata.getYear())
                .length(length)
                .build();
    }
}
