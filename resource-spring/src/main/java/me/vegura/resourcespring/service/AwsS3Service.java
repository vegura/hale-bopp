package me.vegura.resourcespring.service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface AwsS3Service {
    void putObject(String bucketName, String s3Key, InputStream inputStream, long size);
    CompletableFuture<ResponseInputStream<GetObjectResponse>> getObject(String bucketName, String s3Key);
    CompletableFuture<DeleteObjectResponse> removeObject(String bucketName, String s3Key);
    CompletableFuture<CopyObjectResponse> copyObject(String fromBucket, String fromKey, String toBucket, String toKey);
    void moveObject(String fromBucket, String fromKey, String toBucket, String toKey);
}
