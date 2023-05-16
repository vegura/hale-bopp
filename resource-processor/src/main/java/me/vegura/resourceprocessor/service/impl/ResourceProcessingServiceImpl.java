package me.vegura.resourceprocessor.service.impl;

import lombok.RequiredArgsConstructor;
import me.vegura.resourceprocessor.consumer.ResourceConnector;
import me.vegura.resourceprocessor.consumer.SongServiceConnector;
import me.vegura.resourceprocessor.dto.api.ResourceDTOResponse;
import me.vegura.resourceprocessor.dto.api.SongCreateMetaRequest;
import me.vegura.resourceprocessor.service.ResourceProcessingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceProcessingServiceImpl implements ResourceProcessingService {

    private final ResourceConnector resourceConnector;
    private final SongServiceConnector songServiceConnector;

    @Override
    public void getResourceAndParseMetadata(Long resourceId) {
        Optional<ResourceDTOResponse> maybeResourceData = resourceConnector.findResourceById(resourceId);
        if (maybeResourceData.isEmpty())
            throw new RuntimeException("Unable to fetch resource -> " + resourceId);

        // parse metadata
        SongCreateMetaRequest songMeta = SongCreateMetaRequest.builder()
                .name("Name")
                .album("album")
                .artist("artist")
                .resourceId(resourceId)
                .build();
        // access to song service
        songServiceConnector.pushData(songMeta);
    }
}
