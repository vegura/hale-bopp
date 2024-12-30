package me.vegura.resourcespring.service;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface BLOBService {
    void putObject(String bucketName, String objectName, InputStream inputStream, long size);
    CompletableFuture<?> getObject(String bucketName, String objectName);
    CompletableFuture<DeleteObjectResponse> removeObject(String bucketName, String objectName);
    CompletableFuture<CopyObjectResponse> copyObject(String fromBucket, String fromKey, String toBucket, String toKey);
    void moveObject(String fromBucket, String fromKey, String toBucket, String toKey);
}
