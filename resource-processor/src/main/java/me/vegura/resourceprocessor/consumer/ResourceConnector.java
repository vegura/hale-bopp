package me.vegura.resourceprocessor.consumer;

import me.vegura.resourceprocessor.dto.api.ResourceDTOResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ResourceConnector {
    String API_PREFIX = "/resources";

    Optional<ResourceDTOResponse> findResourceById(Long id);
}
