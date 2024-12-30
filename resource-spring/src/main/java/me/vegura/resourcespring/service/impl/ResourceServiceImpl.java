package me.vegura.resourcespring.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.vegura.resourcespring.domain.ResourceMetadata;
import me.vegura.resourcespring.dto.ResourceCreationRes;
import me.vegura.resourcespring.dto.ResourceResponse;
import me.vegura.resourcespring.repository.ResourceMetadataRepository;
import me.vegura.resourcespring.service.BLOBService;
import me.vegura.resourcespring.service.ResourceService;
import me.vegura.resourcespring.service.SignatureService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor @Slf4j
public class ResourceServiceImpl implements ResourceService {

    private static final String BUCKET_NAME = "hale-bopp-application-data";

    @Value("${rabbitmq.resource-upload.notification.routingkey}")
    private String resourceUploadNotificationRoutingKey;

    @Value("${rabbitmq.resource-upload.notification.exchange}")
    private String resourceUploadNotificationExchange;

    private final SignatureService signatureService;
    private final BLOBService blobService;
    private final ResourceMetadataRepository resourceMetadataRepository;
    private final RabbitTemplate mqTemplate;

    @Override
    public ResourceCreationRes createResource(byte[] resource) {
        ResourceMetadata metadata = createMetadataFrom(resource);
        blobService.putObject(BUCKET_NAME,
                metadata.getResourceKey().toString(),
                new ByteArrayInputStream(resource),
                resource.length);
        ResourceMetadata saved = resourceMetadataRepository.save(metadata);

        ResourceCreationRes resourceSaveRes = composeResponse(saved);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            log.info("Sending message -> {}", objectMapper.writeValueAsString(resourceSaveRes));
            mqTemplate.convertAndSend(
                    resourceUploadNotificationExchange,
                    resourceUploadNotificationRoutingKey,
                    objectMapper.writeValueAsString(resourceSaveRes));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return resourceSaveRes;
    }

    private ResourceCreationRes composeResponse(ResourceMetadata meta) {
        return new ResourceCreationRes().setId(meta.getId());
    }

    @Override
    public Optional<ResourceResponse> getResourceBy(Long id) {
        Optional<ResourceMetadata> maybeResourceMetadata = resourceMetadataRepository.findById(id);
        if (maybeResourceMetadata.isEmpty())
            return Optional.empty();

        ResourceMetadata resourceMetadata = maybeResourceMetadata.get();
        byte[] resourceBytes = tryReadingFromS3(resourceMetadata.getResourceKey().toString());
        return Optional.of(ResourceResponse.builder()
                .resourceData(resourceBytes)
                .id(resourceMetadata.getId())
                .build());
    }

    private byte[] tryReadingFromS3(String resourceKey) {
        try {
            return (byte []) blobService.getObject(BUCKET_NAME, resourceKey).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> deleteBy(List<Long> ids) {
        List<ResourceMetadata> resourceMetadataToDelete = ids.stream()
                .map(resourceMetadataRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        resourceMetadataToDelete.forEach(it -> blobService.removeObject(BUCKET_NAME, it.getResourceKey().toString()));
        resourceMetadataRepository.deleteAll(resourceMetadataToDelete);
        return resourceMetadataToDelete.stream()
                .map(ResourceMetadata::getId)
                .toList();
    }

    @Override
    public Optional<String> getResourceBLOBId(Long resourceId) {
        Optional<ResourceMetadata> maybeResourceMetadata = resourceMetadataRepository.findById(resourceId);
        if (maybeResourceMetadata.isEmpty())
            return Optional.empty();

        ResourceMetadata resourceMetadata = maybeResourceMetadata.get();
        return Optional.ofNullable(resourceMetadata.getResourceKey().toString());
    }

    private ResourceMetadata createMetadataFrom(byte[] array) {
        return ResourceMetadata.builder()
                .resourceKey(UUID.randomUUID())
                .fileSize(array.length)
                .signature(signatureService.sign(array))
                .build();
    }
}
