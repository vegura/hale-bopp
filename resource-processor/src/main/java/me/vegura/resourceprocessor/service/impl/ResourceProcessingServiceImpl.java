package me.vegura.resourceprocessor.service.impl;

import lombok.RequiredArgsConstructor;
import me.vegura.resourceprocessor.consumer.ResourceConnector;
import me.vegura.resourceprocessor.dto.api.ResourceDTOResponse;
import me.vegura.resourceprocessor.service.ResourceProcessingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceProcessingServiceImpl implements ResourceProcessingService {

    private static final String TEMP_FILE_NAME = "classpath:temp.mp3";
    private final ResourceConnector resourceConnector;

    @Override
    public void getResourceAndParseMetadata(Long resourceId) {
        Optional<ResourceDTOResponse> maybeResourceData = resourceConnector.findResourceById(resourceId);
        if (maybeResourceData.isEmpty())
            throw new RuntimeException("Unable to fetch resource -> " + resourceId);

        // parse metadata
        // access to song service

    }
}
