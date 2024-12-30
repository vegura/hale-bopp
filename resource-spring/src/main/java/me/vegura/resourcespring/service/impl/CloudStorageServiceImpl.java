package me.vegura.resourcespring.service.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import me.vegura.resourcespring.service.BLOBService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Profile("cloud_storage")
public class CloudStorageServiceImpl implements BLOBService {

    private final Storage cloudStorageClient;

    private static final String BLOB_PREFIX = "resource/";
    private static final String CONTENT_TYPE_BLOB = "application/octet-stream";

    @Override
    public void putObject(String bucketName, String objectName, InputStream inputStream, long size) {
        String blobObjectName = BLOB_PREFIX + objectName;
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobObjectName)
                .setContentType(CONTENT_TYPE_BLOB)
                .build();
        try {
            Blob blob = cloudStorageClient.create(blobInfo, inputStream.readAllBytes());
            if (blob == null) {
                throw new RuntimeException("0 bytes written");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<?> getObject(String bucketName, String objectName) {
        String blobObjectName = BLOB_PREFIX + objectName;
        Blob blobRequestResult = cloudStorageClient.get(bucketName, blobObjectName);
        if (blobRequestResult == null) {
            throw new RuntimeException("Blob result is null");
        }

        return CompletableFuture.completedFuture(blobRequestResult.getContent());
    }

    @Override
    public CompletableFuture<DeleteObjectResponse> removeObject(String bucketName, String objectName) {
        return null;
    }

    @Override
    public CompletableFuture<CopyObjectResponse> copyObject(String fromBucket, String fromKey, String toBucket, String toKey) {
        return null;
    }

    @Override
    public void moveObject(String fromBucket, String fromKey, String toBucket, String toKey) {

    }
}
