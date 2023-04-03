package me.vegura.resource;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import me.vegura.resource.storage.S3StorageVerticle;
import me.vegura.resource.vertx.ApiVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.deployVerticle(ApiVerticle.class.getName());
    vertx.deployVerticle(S3StorageVerticle.class.getName());
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(MainVerticle.class.getName());
  }
}
