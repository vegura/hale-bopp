package me.vegura.resourcespring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Configuration
@PropertySource("classpath:aws-setup.properties")
public class AwsConfig {

    @Value(value = "${s3.region.name}")
    private String awsRegion;

    @Value(value = "${aws.s3.fs.endpoint}")
    private String awsS3FsEndpoint;

    @Value("${access.key.id}")
    private String accessKeyId;

    @Value("${access.key.secret}")
    private String accessKeySecret;

    @Bean
    public S3AsyncClient s3Client(@Value("${aws.isLocal}") boolean isLocal) {
        AwsCredentialsProvider creds = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKeyId, accessKeySecret));
        if (!isLocal) {
            return S3AsyncClient.builder()
                    .credentialsProvider(creds)
//                    .endpointOverride(URI.create(awsS3FsEndpoint))
                    .forcePathStyle(true)
                    .region(Region.of(awsRegion))
                    .build();
        }

        return S3AsyncClient.builder().build();
    }
}
