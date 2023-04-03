package me.vegura.resource.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.ObjectMetadataProvider;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import me.vegura.resource.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.UUID;

@RequiredArgsConstructor
public class S3StorageService implements ResourceService {

  private static final Logger logger = LoggerFactory.getLogger(S3StorageService.class);
  private static volatile S3StorageService service;

  private final S3StorageConfiguration storageConfiguration;

  public S3StorageService getInstance() {
    if (service == null) {
      synchronized (this) {
        if (service == null) {
          service = new S3StorageService(new S3StorageConfiguration());
        }
      }
    }
    return service;
  }

  @Override
  public Promise<JsonArray> saveResource(byte[] resourceBytes) {
    String resourceId = UUID.randomUUID().toString();
    AmazonS3 s3Client = storageConfiguration.getS3Client();
    if (s3Client.doesBucketExistV2(BUCKET_NAME)) {
      logger.info("Saving resource to bucket -> ");
      ByteArrayInputStream resourceStream = new ByteArrayInputStream(resourceBytes);
//      s3Client.putObject(BUCKET_NAME, resourceId, resourceStream, );
    }
  }
}
