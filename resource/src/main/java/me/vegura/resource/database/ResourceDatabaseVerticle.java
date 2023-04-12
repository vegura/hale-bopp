package me.vegura.resource.database;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class ResourceDatabaseVerticle extends AbstractVerticle {

  private PostgresDatabaseConfig databaseConfig;

  private void initialize(Vertx vertx) {
    databaseConfig = new PostgresDatabaseConfig(vertx);

  }

  @Override
  public void start() {
    initialize(vertx);
  }
}
