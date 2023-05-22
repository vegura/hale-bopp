package me.vegura.resourceprocessor.service.impl;

import me.vegura.resourceprocessor.dto.api.SongCreateMetaRequest;
import me.vegura.resourceprocessor.service.Mp3MetadataParser;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class Mp3MetadataParserImpl implements Mp3MetadataParser {

    private static final String NAME_META_HEADER = "dc:title";
    private static final String ARTIST_META_HEADER = "xmpDM:artist";
    private static final String ALBUM_META_HEADER = "xmpDM:album";
    private static final String YEAR_META_HEADER = "xmpDM:releaseDate";
    private static final String LENGTH_META_HEADER = "xmpDM:duration";

    @Override
    public SongMetadata parseMetadataFrom(byte[] data) throws TikaException, IOException, SAXException {
        BodyContentHandler bodyHandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();

        Mp3Parser mp3Parser = new Mp3Parser();
        mp3Parser.parse(new ByteArrayInputStream(data), bodyHandler, metadata, parseContext);

        return SongMetadata.builder()
                .name(metadata.get(NAME_META_HEADER))
                .artist(metadata.get(ARTIST_META_HEADER))
                .album(metadata.get(ALBUM_META_HEADER))
                .year(metadata.get(YEAR_META_HEADER))
                .length(metadata.get(LENGTH_META_HEADER))
                .build();
    }
}
