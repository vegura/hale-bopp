package me.vegura.resource.api;

import io.reactivex.Completable;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.api.validation.HTTPRequestValidationHandler;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

import static me.vegura.resource.api.Endpoints.UPLOAD_RESOURCE;

public class HttpServerVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startFuture) throws Exception {
    HttpServer server = vertx.createHttpServer();

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.post(UPLOAD_RESOURCE).produces("audio/mpeg");
//      .handler()
  }
//  public void start(Future<Void> startFuture) throws Exception {
//    HttpServer httpServer = vertx.createHttpServer();
//    HttpServer httpServer = vertx.createHttpServer();

//    Router router = Router.router();
//    Router router = Router.router(vertx);
//    router.route().handler(BodyHandler.create());
//    router.post(UPLOAD_RESOURCE).produces("audio/mpeg");
//      .handler()
//    startFuture.succeeded();
//  }
}
