package me.vegura.resourcespring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

import java.net.URI;

@Configuration
public class AwsConfig {

    @Value(value = "${aws.region}")
    private String awsRegion;

    @Value(value = "${aws.s3.fs.endpoint}")
    private String awsS3FsEndpoint;

    @Bean
    public S3AsyncClient s3Client(@Value("${aws.isLocal}") boolean isLocal) {
        if (isLocal) {
            return S3AsyncClient.builder()
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .endpointOverride(URI.create(awsS3FsEndpoint))
                    .forcePathStyle(true)
                    .region(Region.of(awsRegion))
                    .build();
        }
        return S3AsyncClient.builder().build();
    }
}
