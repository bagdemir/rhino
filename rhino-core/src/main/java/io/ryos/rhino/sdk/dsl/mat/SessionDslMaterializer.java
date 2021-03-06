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

import io.ryos.rhino.sdk.data.UserSession;
import io.ryos.rhino.sdk.dsl.SessionDslItem;
import io.ryos.rhino.sdk.dsl.SessionDslItem.Scope;
import java.util.function.Supplier;
import reactor.core.publisher.Mono;

public class SessionDslMaterializer implements DslMaterializer {

  private final SessionDslItem dslItem;

  public SessionDslMaterializer(SessionDslItem dslItem) {
    this.dslItem = dslItem;
  }

  @Override
  public Mono<UserSession> materialize(UserSession userSession) {

    Supplier<Object> objectSupplier = dslItem.getObjectFunction();
    Object apply = objectSupplier.get();

    if (Scope.USER.equals(dslItem.getSessionScope())) {
      userSession.add(dslItem.getSessionKey(), apply);
    } else {
      userSession.getSimulationSession().add(dslItem.getSessionKey(), apply);
    }

    return Mono.just(userSession);
  }
}
