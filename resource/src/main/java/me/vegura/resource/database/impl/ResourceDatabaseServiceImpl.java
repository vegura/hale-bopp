package me.vegura.resource.database.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.pgclient.PgPool;
import me.vegura.resource.database.ResourceDatabaseService;
import me.vegura.resource.database.entity.Resource;

public class ResourceDatabaseServiceImpl implements ResourceDatabaseService {
  public ResourceDatabaseServiceImpl(PgPool pgPool, Handler<AsyncResult<ResourceDatabaseService>> resultHandler) {

  }

  @Override
  public Future<String> createResource(Resource resource) {
    return null;
  }

  @Override
  public Future<String> getResourceBy(Integer id) {
    return null;
  }

  @Override
  public Future<String> deleteResourceBy(Integer id) {
    return null;
  }
}
