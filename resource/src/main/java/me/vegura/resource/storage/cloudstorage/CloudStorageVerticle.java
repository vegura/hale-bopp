package me.vegura.resource.storage.cloudstorage;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class CloudStorageVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(CloudStorageVerticle.class);

  public static final String ESB_RESOURCE_SAVE = "cloudstorage.resource.save";
  public static final String ESB_RESOURCE_FETCH = "cloudstorage.resource.get";

  private static final String RESOURCE_ID_KEY = "resource_id";

  private final CloudStorageService storageService = new CloudStorageService(
    new CloudStorageConfiguration()
  );

  @Override
  public void start() {
    logger.info("Started CloudStorage Verticle");

    vertx.eventBus().consumer(ESB_RESOURCE_SAVE, this::saveResourceHandler);
    vertx.eventBus().consumer(ESB_RESOURCE_FETCH, this::fetchResourceHandler);
  }

  private void fetchResourceHandler(Message<JsonObject> message) {
    JsonObject fetchRequest = message.body();
    String resourceId = fetchRequest.getString(RESOURCE_ID_KEY);
    logger.info(String.format("Trying to fetch resource -> %s", resourceId));

    Future<byte[]> fetchResourceFuture = storageService.fetchResource(resourceId);
    fetchResourceFuture.onSuccess(message::reply);
    fetchResourceFuture.onFailure(handler -> message.reply(null));
  }

  private void saveResourceHandler(Message<byte[]> message) {
    logger.info("Message length -> {}", message.body().length);
    Future<String> savingFuture = storageService.saveResource(message.body());
    savingFuture.onSuccess(asyncResult -> {
      message.reply(
        new JsonObject()
          .put("status", "OK")
          .put("id", asyncResult)
      );
    });

    savingFuture.onFailure(asyncError -> {
      message.reply(
        new JsonObject()
          .put("status", "NOK")
      );
    });
  }

}
