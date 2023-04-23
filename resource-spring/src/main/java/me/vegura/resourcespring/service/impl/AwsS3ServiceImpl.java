package me.vegura.resourcespring.service.impl;

import lombok.RequiredArgsConstructor;
import me.vegura.resourcespring.service.AwsS3Service;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

    private final S3AsyncClient s3Client;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void putObject(String bucketName, String s3Key, InputStream inputStream, long size) {
        var putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();
        var requestBody = AsyncRequestBody.fromInputStream(inputStream, size, taskExecutor.getThreadPoolExecutor());
        s3Client.putObject(putRequest, requestBody);
    }

    @Override
    public CompletableFuture<ResponseBytes<GetObjectResponse>> getObject(String bucketName, String s3Key) {
        var getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(s3Key).build();
        return s3Client.getObject(getObjectRequest, AsyncResponseTransformer.toBytes());
    }

    @Override
    public CompletableFuture<DeleteObjectResponse> removeObject(String bucketName, String s3Key) {
        var deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(s3Key).build();
        return s3Client.deleteObject(deleteObjectRequest);
    }

    @Override
    public CompletableFuture<CopyObjectResponse> copyObject(String fromBucket, String fromKey, String toBucket, String toKey) {
        var copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(fromBucket)
                .sourceKey(fromKey)
                .destinationBucket(toBucket)
                .destinationKey(toKey)
                .build();
        return s3Client.copyObject(copyObjectRequest);
    }

    @Override
    public void moveObject(String fromBucket, String fromKey, String toBucket, String toKey) {
        copyObject(fromBucket, fromKey, toBucket, toKey).thenAccept(it -> removeObject(fromBucket, fromKey));
    }
}
