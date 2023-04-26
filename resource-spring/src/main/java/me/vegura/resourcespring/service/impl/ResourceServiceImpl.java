package me.vegura.resourcespring.service.impl;

import lombok.RequiredArgsConstructor;
import me.vegura.resourcespring.domain.ResourceMetadata;
import me.vegura.resourcespring.dto.ResourceCreationRes;
import me.vegura.resourcespring.dto.ResourceResponse;
import me.vegura.resourcespring.repository.ResourceMetadataRepository;
import me.vegura.resourcespring.service.AwsS3Service;
import me.vegura.resourcespring.service.ResourceService;
import me.vegura.resourcespring.service.SignatureService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static me.vegura.resourcespring.configuration.RabbitMqConfig.AMQP_RESOURCE_NOTIFICATION_QUEUE;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private static final String BUCKET_NAME = "hale-bopp-music2";

    private final SignatureService signatureService;
    private final AwsS3Service s3Service;
    private final ResourceMetadataRepository resourceMetadataRepository;
    private final RabbitTemplate mqTemplate;

    @Override
    public ResourceCreationRes createResource(byte[] resource) {
        ResourceMetadata metadata = createMetadataFrom(resource);
        s3Service.putObject(BUCKET_NAME,
                metadata.getResourceKey().toString(),
                new ByteArrayInputStream(resource),
                resource.length);
        ResourceMetadata saved = resourceMetadataRepository.save(metadata);

        ResourceCreationRes resourceSaveRes = composeResponse(saved);
        mqTemplate.convertAndSend(AMQP_RESOURCE_NOTIFICATION_QUEUE, resourceSaveRes);
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
            ResponseBytes<GetObjectResponse> resourceResponseByteStream = s3Service.getObject(
                    BUCKET_NAME, resourceKey).get();
            return resourceResponseByteStream.asByteArray();
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

        resourceMetadataToDelete.forEach(it -> s3Service.removeObject(BUCKET_NAME, it.getResourceKey().toString()));
        resourceMetadataRepository.deleteAll(resourceMetadataToDelete);
        return resourceMetadataToDelete.stream()
                .map(ResourceMetadata::getId)
                .toList();
    }

    private ResourceMetadata createMetadataFrom(byte[] array) {
        return ResourceMetadata.builder()
                .resourceKey(UUID.randomUUID())
                .fileSize(array.length)
                .signature(signatureService.sign(array))
                .build();
    }
}
