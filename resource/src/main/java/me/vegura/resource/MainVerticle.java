package me.vegura.resource;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import me.vegura.resource.storage.cloudstorage.CloudStorageVerticle;
import me.vegura.resource.streaming.StreamingVerticle;
import me.vegura.resource.vertx.ApiVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) {
    logger.info("Starting application");
    vertx.deployVerticle(ApiVerticle.class.getName());
    vertx.deployVerticle(new CloudStorageVerticle());
    vertx.deployVerticle(new StreamingVerticle());
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(MainVerticle.class.getName());
  }
}
