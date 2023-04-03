package me.vegura.resource.service;

import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;

public interface ResourceService {

  String BUCKET_NAME = "hale-bopp-music";

  Promise<JsonArray> saveResource(byte[] resourceBytes);

}
