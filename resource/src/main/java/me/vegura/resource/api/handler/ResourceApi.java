package me.vegura.resource.api.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class ResourceApi {
  public static Handler<RoutingContext> createResource() {
    return it -> {};
  }
}
