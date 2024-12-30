package me.vegura.resourceprocessor.service.impl;

import com.mpatric.mp3agic.Mp3File;
import me.vegura.resourceprocessor.service.Mp3MetadataParser;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class Mp3MetadataParserImpl implements Mp3MetadataParser {

    private static final String NAME_META_HEADER = "Title";
    private static final String ARTIST_META_HEADER = "xmpDM:artist";
    private static final String ALBUM_META_HEADER = "xmpDM:album";
    private static final String YEAR_META_HEADER = "xmpDM:releaseDate";
    private static final String LENGTH_META_HEADER = "xmpDM:duration";

    @Override
    public SongMetadata parseMetadataFrom(byte[] data) throws TikaException, IOException, SAXException {
        Optional<SongMetadata> maybeSongMetadata = parseDataId3V2(data);
        if (maybeSongMetadata.isPresent())
            return maybeSongMetadata.get();

        BodyContentHandler bodyHandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();

        Mp3Parser mp3Parser = new Mp3Parser();
        mp3Parser.parse(new ByteArrayInputStream(data), bodyHandler, metadata, parseContext);

        if (metadata.get(NAME_META_HEADER) == null)
            throw new RuntimeException("Song is not parsable");

        return SongMetadata.builder()
                .name(metadata.get(NAME_META_HEADER))
                .artist(metadata.get(ARTIST_META_HEADER))
                .album(metadata.get(ALBUM_META_HEADER))
                .year(metadata.get(YEAR_META_HEADER))
                .length(metadata.get(LENGTH_META_HEADER))
                .build();
    }

    public Optional<SongMetadata> parseDataId3V2(byte[] data) {
        try {
            String tempFile = UUID.randomUUID() + ".mp3";

            Path path = Files.write(Path.of(tempFile), data);

            Mp3File mp3File = new Mp3File(path.toFile());

            // Check for ID3v1 tags
            if (mp3File.hasId3v1Tag()) {
                System.out.println("ID3v1 Tag Found:");
                System.out.println("Title: " + mp3File.getId3v1Tag().getTitle());
                System.out.println("Artist: " + mp3File.getId3v1Tag().getArtist());
                System.out.println("Album: " + mp3File.getId3v1Tag().getAlbum());
                System.out.println("Year: " + mp3File.getId3v1Tag().getYear());
                System.out.println("Genre: " + mp3File.getId3v1Tag().getGenreDescription());

                return Optional.of(SongMetadata.builder()
                        .name(mp3File.getId3v1Tag().getTitle())
                        .artist(mp3File.getId3v1Tag().getArtist())
                        .album(mp3File.getId3v1Tag().getAlbum())
                        .year(mp3File.getId3v1Tag().getYear())
                        .length(String.valueOf(mp3File.getLength()))
                        .build());
            }

            // Check for ID3v2 tags
            if (mp3File.hasId3v2Tag()) {
                System.out.println("ID3v2 Tag Found:");
                System.out.println("Title: " + mp3File.getId3v2Tag().getTitle());
                System.out.println("Artist: " + mp3File.getId3v2Tag().getArtist());
                System.out.println("Album: " + mp3File.getId3v2Tag().getAlbum());
                System.out.println("Year: " + mp3File.getId3v2Tag().getYear());
                System.out.println("Genre: " + mp3File.getId3v2Tag().getGenreDescription());
                System.out.println("Comment: " + mp3File.getId3v2Tag().getComment());

                return Optional.of(SongMetadata.builder()
                        .name(mp3File.getId3v2Tag().getTitle())
                        .artist(mp3File.getId3v2Tag().getArtist())
                        .album(mp3File.getId3v2Tag().getAlbum())
                        .year(mp3File.getId3v2Tag().getYear())
                        .length(String.valueOf(mp3File.getLength()))
                        .build());
            }

            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
