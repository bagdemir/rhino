/*
 * Copyright 2020 Ryos.io.
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

package io.ryos.rhino.sdk.dsl;

import io.ryos.rhino.sdk.data.UserSession;
import java.util.function.Predicate;

/**
 * Filters the objects out which do not satisfy the {@link Predicate}.
 *
 * @author Erhan Bagdemir
 */
public interface FilterDsl extends MaterializableDslItem {

  /**
   * Returns the {@link Predicate} which will be applied to the filter.
   *
   * @return {@link Predicate} instance.
   */
  Predicate<UserSession> getPredicate();
}
