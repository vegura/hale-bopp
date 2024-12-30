package me.vegura.resource.storage.s3;

import com.amazonaws.services.s3.AmazonS3;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import lombok.RequiredArgsConstructor;
import me.vegura.resource.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.UUID;


@RequiredArgsConstructor
public class S3StorageService implements StorageService {

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
  public Future<String> saveResource(byte[] resourceBytes) {
    String resourceId = UUID.randomUUID().toString();
    AmazonS3 s3Client = storageConfiguration.getS3Client();
    Promise<Object> promise = Promise.promise();

    if (s3Client.doesBucketExistV2("hale-bopp-bucket")) {
      logger.info("Saving resource to bucket -> ");
      ByteArrayInputStream resourceStream = new ByteArrayInputStream(resourceBytes);
//      s3Client.putObject(BUCKET_NAME, resourceId, resourceStream, );
      promise.complete("done");
    }
    return null;
  }

  @Override
  public Future<byte[]> fetchResource(String resourceId) {
    return null;
  }
}
