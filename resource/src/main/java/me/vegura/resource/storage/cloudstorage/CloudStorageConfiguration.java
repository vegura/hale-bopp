package me.vegura.resource.storage.cloudstorage;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloudStorageConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(CloudStorageConfiguration.class);

  private static final String STORAGE_BUCKET = "hale-bopp-application-data";

  public static final String STORAGE_RESOURCE_FOLDER = "resource";

  private StorageOptions createCloudStorageConfiguration() {
    return StorageOptions.newBuilder()
      .setProjectId("lab-test-project-1-305710")
      .build();
  }

  public Storage getStorageInstance() {
    StorageOptions cloudStorageConfiguration = createCloudStorageConfiguration();
    return cloudStorageConfiguration.getService();
  }

  public Bucket getResourceBucket() {
    Bucket bucket = getStorageInstance().get(STORAGE_BUCKET);

    if (bucket == null) {
      String bucketIsNotReachableMessage = String.format("Bucket is not reachable -> %s", STORAGE_BUCKET);
      logger.error(bucketIsNotReachableMessage);
      throw new RuntimeException(bucketIsNotReachableMessage);
    }

    return bucket;
  }
}
