package me.vegura.resourcespring.service;

import me.vegura.resourcespring.dto.ResourceCreationRes;
import me.vegura.resourcespring.dto.ResourceResponse;

import java.util.List;
import java.util.Optional;

public interface ResourceService {
    ResourceCreationRes createResource(byte[] resource);
    Optional<ResourceResponse> getResourceBy(Long id);
    List<Long> deleteBy(List<Long> ids);
}
