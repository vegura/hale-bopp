package me.vegura.resource.storage.cloudstorage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageException;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import lombok.RequiredArgsConstructor;
import me.vegura.resource.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.UUID;

@RequiredArgsConstructor
public class CloudStorageService implements StorageService {

  private static final Logger logger = LoggerFactory.getLogger(CloudStorageService.class);

  private final CloudStorageConfiguration cloudStorageConfiguration;

  @Override
  public Future<String> saveResource(byte[] resourceBytes) {
    String resourceId = UUID.randomUUID().toString();
    Bucket resourceBucket = cloudStorageConfiguration.getResourceBucket();
    logger.info(
      String.format("Storing track into bucket -> %s/%s",
        resourceBucket,
        CloudStorageConfiguration.STORAGE_RESOURCE_FOLDER));
    String blobName = CloudStorageConfiguration.STORAGE_RESOURCE_FOLDER + "/" + resourceId;

    Promise<String> savePromise = Promise.promise();
    try {
      resourceBucket.create(blobName, resourceBytes, "application/octet-stream");
      savePromise.complete(blobName);
    } catch (StorageException exception) {
      savePromise.fail(exception.getMessage());
    }

    return savePromise.future();
  }

  @Override
  public Future<byte[]> fetchResource(String resourceId) {
    String resourceStorageId = CloudStorageConfiguration.STORAGE_RESOURCE_FOLDER + "/" + resourceId;

    Promise<byte[]> fetchPromise = Promise.promise();

    Blob resourceBlob = cloudStorageConfiguration
      .getResourceBucket()
      .get(resourceStorageId);

    byte[] resourceContents = resourceBlob.getContent();
    logger.info("Returning resource -> " + resourceContents.length);
    if (resourceContents.length != 0)
      fetchPromise.complete(resourceContents);
    else
      fetchPromise.fail(String.format("Resource is not found -> %s", resourceId));

    return fetchPromise.future();
  }
}
