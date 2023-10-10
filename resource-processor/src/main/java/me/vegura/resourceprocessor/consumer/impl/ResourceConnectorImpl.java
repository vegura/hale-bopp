package me.vegura.resourceprocessor.consumer.impl;

import lombok.RequiredArgsConstructor;
import me.vegura.resourceprocessor.consumer.ResourceConnector;
import me.vegura.resourceprocessor.dto.api.ResourceDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceConnectorImpl implements ResourceConnector {

    private static final String RESOURCE_SERVICE_LINK = "Resource-Service-Spring";
    private final RestTemplate restTemplate;

    @Override
    public Optional<ResourceDTOResponse> findResourceById(Long id) {
        String resourcePath = "http://" + RESOURCE_SERVICE_LINK + API_PREFIX + "/" + id;

        ResponseEntity<ResourceDTOResponse> response = restTemplate.getForEntity(resourcePath, ResourceDTOResponse.class);
        ResourceDTOResponse resourceData = response.getBody();
        return Optional.ofNullable(resourceData);
    }
}
