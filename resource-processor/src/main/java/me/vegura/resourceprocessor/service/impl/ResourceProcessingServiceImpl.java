package me.vegura.resourceprocessor.service.impl;

import lombok.RequiredArgsConstructor;
import me.vegura.resourceprocessor.consumer.ResourceConnector;
import me.vegura.resourceprocessor.consumer.SongServiceConnector;
import me.vegura.resourceprocessor.dto.api.ResourceDTOResponse;
import me.vegura.resourceprocessor.dto.api.SongCreateMetaRequest;
import me.vegura.resourceprocessor.exceptions.ResourceFetchException;
import me.vegura.resourceprocessor.exceptions.ResourceParsingException;
import me.vegura.resourceprocessor.service.Mp3MetadataParser;
import me.vegura.resourceprocessor.service.ResourceProcessingService;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceProcessingServiceImpl implements ResourceProcessingService {

    private final ResourceConnector resourceConnector;
    private final SongServiceConnector songServiceConnector;
    private final Mp3MetadataParser songParser;

    @Override
    public void getResourceAndParseMetadata(Long resourceId) {
        ResourceDTOResponse resourceDTOResponse = fetchResource(resourceId);
        Mp3MetadataParser.SongMetadata parsingResult = tryParseData(resourceDTOResponse.getResourceData());
        songServiceConnector.pushData(SongCreateMetaRequest.fromSongMeta(parsingResult, resourceId));
    }

    private Mp3MetadataParser.SongMetadata tryParseData(byte[] resourceData) {
        try {
            return songParser.parseMetadataFrom(resourceData);
        } catch (TikaException | IOException | SAXException e) {
            throw new ResourceParsingException(e);
        }
    }

    private ResourceDTOResponse fetchResource(Long resourceId) {
        Optional<ResourceDTOResponse> maybeResourceById = resourceConnector.findResourceById(resourceId);
        if (maybeResourceById.isEmpty())
            throw new ResourceFetchException("Unable to fetch resource");
        return maybeResourceById.get();
    }
}
