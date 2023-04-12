package me.vegura.resource.database.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link me.vegura.resource.database.entity.Resource}.
 * NOTE: This class has been automatically generated from the {@link me.vegura.resource.database.entity.Resource} original class using Vert.x codegen.
 */
public class ResourceConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Resource obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
      }
    }
  }

  public static void toJson(Resource obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Resource obj, java.util.Map<String, Object> json) {
  }
}
