package me.vegura.resource.storage;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class S3StorageVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(S3StorageVerticle.class);

  public static final String ESB_RESOURCE_SAVE = "resource.save";

  private final S3StorageService service;

  @Override
  public void start() {
      vertx.eventBus()
        .consumer(ESB_RESOURCE_SAVE, this::saveResourceHandler);
  }

  private void saveResourceHandler(Message<byte[]> message) {
    logger.info("Message length -> {}", message.body().length);
    service.saveResource(message.body());
    message.reply(new JsonObject().put("id", "123"));
  }
}
