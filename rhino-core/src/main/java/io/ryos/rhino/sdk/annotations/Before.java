/*
  Copyright 2018 Ryos.io.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package io.ryos.rhino.sdk.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method annotation to mark clean up methods, that is run after every scenario (or DSL) execution.
 * Use methods, for instance, to release resources and clean up the testing environment. If
 * simulation contains multiple scenarios, then before and after methods will be run before and
 * after every scenarios in the simulation.
 * <p>
 *
 * If the simulation is need to be set up you might prefer to use {@link Prepare} static method -
 * and {@link CleanUp}, respectively, which is called before the simulation run for every user.
 * Prepare and clean up static methods might be handy e.g to create some resources for
 * simulation, and release them after after simulation.
 * <p>
 *
 * @author Erhan Bagdemir
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
}
