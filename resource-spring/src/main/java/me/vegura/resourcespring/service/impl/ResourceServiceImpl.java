package me.vegura.resourcespring.service.impl;

import lombok.RequiredArgsConstructor;
import me.vegura.resourcespring.domain.ResourceMetadata;
import me.vegura.resourcespring.dto.ResourceCreationRes;
import me.vegura.resourcespring.repository.ResourceMetadataRepository;
import me.vegura.resourcespring.service.AwsS3Service;
import me.vegura.resourcespring.service.ResourceService;
import me.vegura.resourcespring.service.SignatureService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private static final String BUCKET_NAME = "resources";

    private final SignatureService signatureService;
    private final AwsS3Service s3Service;
    private final ResourceMetadataRepository resourceMetadataRepository;

    @Override
    public ResourceCreationRes createResource(byte[] resource) {
        ResourceMetadata metadata = createMetadataFrom(resource);
        s3Service.putObject(BUCKET_NAME,
                metadata.getResourceKey().toString(),
                new ByteArrayInputStream(resource),
                resource.length);
        ResourceMetadata saved = resourceMetadataRepository.save(metadata);
        return new ResourceCreationRes().setId(saved.getId());
    }

    private ResourceMetadata createMetadataFrom(byte[] array) {
        return ResourceMetadata.builder()
                .resourceKey(UUID.randomUUID())
                .fileSize(array.length)
                .signature(signatureService.sign(array))
                .build();
    }
}
