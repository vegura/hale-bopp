package me.vegura.resourceprocessor.consumer.impl;

import lombok.RequiredArgsConstructor;
import me.vegura.resourceprocessor.consumer.ResourceConnector;
import me.vegura.resourceprocessor.dto.api.ResourceDTOResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceConnectorImpl implements ResourceConnector {

    @Value("${resource-service.host}")
    private String resourceServiceHost;

    @Value("${resource-service.port}")
    private String resourceServicePort;

    private final String FETCH_RESOURCE_URL = "http://" + resourceServiceHost + ":" + resourceServicePort + API_PREFIX;

    @Override
    public Optional<ResourceDTOResponse> findResourceById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String resourcePath = FETCH_RESOURCE_URL + "/" + id;

        ResponseEntity<ResourceDTOResponse> response = restTemplate.getForEntity(resourcePath, ResourceDTOResponse.class);
        ResourceDTOResponse resourceData = response.getBody();
        return Optional.ofNullable(resourceData);
    }
}
