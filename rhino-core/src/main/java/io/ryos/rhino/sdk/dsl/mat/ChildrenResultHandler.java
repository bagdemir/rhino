/*
 * Copyright 2018 Ryos.io.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.ryos.rhino.sdk.dsl.mat;

import static io.ryos.rhino.sdk.dsl.specs.builder.SessionAccessor.getActiveUser;

import io.ryos.rhino.sdk.data.UserSession;
import io.ryos.rhino.sdk.dsl.ResultHandler;
import io.ryos.rhino.sdk.dsl.specs.HttpResponse;
import io.ryos.rhino.sdk.dsl.specs.HttpSpec;
import java.util.ArrayList;
import java.util.List;

public class ChildrenResultHandler implements ResultHandler<HttpResponse> {

  private final String contextKey;
  private final UserSession userSession;
  private final HttpSpec httpSpec;
  private final String containerKey;

  public ChildrenResultHandler(String contextKey, UserSession userSession,
      HttpSpec httpSpec,
      String containerKey) {
    this.contextKey = contextKey;
    this.userSession = userSession;
    this.httpSpec = httpSpec;
    this.containerKey = containerKey;
  }

  public ChildrenResultHandler(final UserSession userSession, final HttpSpec httpSpec,
      final String containerKey) {
    this(httpSpec.getResponseKey(), userSession, httpSpec, containerKey);
  }

  @Override
  public UserSession handle(final HttpResponse resultObject) {
    if (contextKey == null || resultObject == null) {
      return userSession;
    }

    var activatedUser = getActiveUser(httpSpec, userSession);
    var simulationSession = userSession.findSimulationSession(activatedUser);
    var resultList =
        simulationSession.<List<Object>>get(containerKey).orElse(new ArrayList<>());
    resultList.add(resultObject);
    simulationSession.add(containerKey, resultList);

    return userSession;
  }
}
