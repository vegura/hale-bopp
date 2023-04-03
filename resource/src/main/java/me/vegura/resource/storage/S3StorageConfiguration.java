package me.vegura.resource.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3StorageConfiguration {
  public AmazonS3 getS3Client() {
    AWSCredentials credentials = new BasicAWSCredentials(
      "AKIAV6HHWWUV53SLV66X",
      "gyWPNltbROXciPB3kLWXll3OD1TItVjBVNxFJvS8");

    AmazonS3 s3Client = AmazonS3ClientBuilder
      .standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .withRegion(Regions.US_EAST_1)
      .build();

    return s3Client;
  }
}
