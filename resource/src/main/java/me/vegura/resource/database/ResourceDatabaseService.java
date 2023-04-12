package me.vegura.resource.database;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgPool;
import me.vegura.resource.database.entity.Resource;
import me.vegura.resource.database.impl.ResourceDatabaseServiceImpl;

@ProxyGen
@VertxGen
public interface ResourceDatabaseService {

  static ResourceDatabaseService create(PgPool pgPool, Handler<AsyncResult<ResourceDatabaseService>> resultHandler) {
    return new ResourceDatabaseServiceImpl(pgPool, resultHandler);
  }

  static ResourceDatabaseService createProxy(Vertx vertx, String address) {
    return new ResourceDatabaseServiceVertxEBProxy(vertx, address);
  }

  Future<String> createResource(Resource resource);
  Future<String> getResourceBy(Integer id);
  Future<String> deleteResourceBy(Integer id);
}
