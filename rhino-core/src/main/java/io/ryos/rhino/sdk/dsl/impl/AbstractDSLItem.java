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

package io.ryos.rhino.sdk.dsl.impl;

import io.ryos.rhino.sdk.dsl.DslItem;
import org.apache.commons.lang3.Validate;

public abstract class AbstractDSLItem implements DslItem {

  private String name;
  private DslItem parent;

  public AbstractDSLItem(String name) {
    Validate.notNull(name, "keyName must not be null.");
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public DslItem getParent() {
    return parent;
  }

  @Override
  public void setParent(DslItem parent) {
    this.parent = parent;
  }

  @Override
  public boolean hasParent() {
    return parent != null;
  }

  @Override
  public String getParentName() {
    return parent != null ? parent.getName() : "";
  }
}
