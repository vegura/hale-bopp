/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package me.vegura.resource.database.reactivex;

import io.vertx.reactivex.RxHelper;
import io.vertx.reactivex.ObservableHelper;
import io.vertx.reactivex.FlowableHelper;
import io.vertx.reactivex.impl.AsyncResultMaybe;
import io.vertx.reactivex.impl.AsyncResultSingle;
import io.vertx.reactivex.impl.AsyncResultCompletable;
import io.vertx.reactivex.WriteStreamObserver;
import io.vertx.reactivex.WriteStreamSubscriber;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.vertx.core.Handler;
import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.lang.rx.RxGen;
import io.vertx.lang.rx.TypeArg;
import io.vertx.lang.rx.MappingIterator;


@RxGen(me.vegura.resource.database.ResourceDatabaseService.class)
public class ResourceDatabaseService {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ResourceDatabaseService that = (ResourceDatabaseService) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public static final TypeArg<ResourceDatabaseService> __TYPE_ARG = new TypeArg<>(    obj -> new ResourceDatabaseService((me.vegura.resource.database.ResourceDatabaseService) obj),
    ResourceDatabaseService::getDelegate
  );

  private final me.vegura.resource.database.ResourceDatabaseService delegate;
  
  public ResourceDatabaseService(me.vegura.resource.database.ResourceDatabaseService delegate) {
    this.delegate = delegate;
  }

  public ResourceDatabaseService(Object delegate) {
    this.delegate = (me.vegura.resource.database.ResourceDatabaseService)delegate;
  }

  public me.vegura.resource.database.ResourceDatabaseService getDelegate() {
    return delegate;
  }


  public static me.vegura.resource.database.reactivex.ResourceDatabaseService create(io.vertx.reactivex.pgclient.PgPool pgPool, io.vertx.core.Handler<io.vertx.core.AsyncResult<me.vegura.resource.database.reactivex.ResourceDatabaseService>> resultHandler) { 
    me.vegura.resource.database.reactivex.ResourceDatabaseService ret = me.vegura.resource.database.reactivex.ResourceDatabaseService.newInstance((me.vegura.resource.database.ResourceDatabaseService)me.vegura.resource.database.ResourceDatabaseService.create(pgPool.getDelegate(), new io.vertx.lang.rx.DelegatingHandler<>(resultHandler, ar -> ar.map(event -> me.vegura.resource.database.reactivex.ResourceDatabaseService.newInstance((me.vegura.resource.database.ResourceDatabaseService)event)))));
    return ret;
  }

  public static me.vegura.resource.database.reactivex.ResourceDatabaseService createProxy(io.vertx.reactivex.core.Vertx vertx, java.lang.String address) { 
    me.vegura.resource.database.reactivex.ResourceDatabaseService ret = me.vegura.resource.database.reactivex.ResourceDatabaseService.newInstance((me.vegura.resource.database.ResourceDatabaseService)me.vegura.resource.database.ResourceDatabaseService.createProxy(vertx.getDelegate(), address));
    return ret;
  }

  public io.vertx.core.Future<java.lang.String> createResource(me.vegura.resource.database.entity.Resource resource) { 
    io.vertx.core.Future<java.lang.String> ret = delegate.createResource(resource).map(val -> val);
    return ret;
  }

  public io.reactivex.Single<java.lang.String> rxCreateResource(me.vegura.resource.database.entity.Resource resource) { 
    return AsyncResultSingle.toSingle($handler -> {
      createResource(resource).onComplete($handler);
    });
  }

  public io.vertx.core.Future<java.lang.String> getResourceBy(java.lang.Integer id) { 
    io.vertx.core.Future<java.lang.String> ret = delegate.getResourceBy(id).map(val -> val);
    return ret;
  }

  public io.reactivex.Single<java.lang.String> rxGetResourceBy(java.lang.Integer id) { 
    return AsyncResultSingle.toSingle($handler -> {
      getResourceBy(id).onComplete($handler);
    });
  }

  public io.vertx.core.Future<java.lang.String> deleteResourceBy(java.lang.Integer id) { 
    io.vertx.core.Future<java.lang.String> ret = delegate.deleteResourceBy(id).map(val -> val);
    return ret;
  }

  public io.reactivex.Single<java.lang.String> rxDeleteResourceBy(java.lang.Integer id) { 
    return AsyncResultSingle.toSingle($handler -> {
      deleteResourceBy(id).onComplete($handler);
    });
  }

  public static ResourceDatabaseService newInstance(me.vegura.resource.database.ResourceDatabaseService arg) {
    return arg != null ? new ResourceDatabaseService(arg) : null;
  }

}
