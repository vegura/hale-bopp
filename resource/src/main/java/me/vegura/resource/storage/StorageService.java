package me.vegura.resource.storage;

import com.google.gson.JsonArray;
import io.vertx.core.Future;

public interface StorageService {
  Future<String> saveResource(byte[] resourceBytes);
  Future<byte[]> fetchResource(String resourceId);
}
