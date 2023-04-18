package me.vegura.resourcespring.service;

import me.vegura.resourcespring.dto.ResourceCreationRes;

public interface ResourceService {
    ResourceCreationRes createResource(byte[] resource);
}
