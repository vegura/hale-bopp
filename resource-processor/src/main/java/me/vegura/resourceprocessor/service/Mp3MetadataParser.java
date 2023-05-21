package me.vegura.resourceprocessor.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.vegura.resourceprocessor.dto.api.SongCreateMetaRequest;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface Mp3MetadataParser {
    SongMetadata parseMetadataFrom(byte[] data) throws TikaException, IOException, SAXException;

    @Data @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class SongMetadata {
        private String name;
        private String artist;
        private String album;
        private String length;
        private String year;
    }
}
