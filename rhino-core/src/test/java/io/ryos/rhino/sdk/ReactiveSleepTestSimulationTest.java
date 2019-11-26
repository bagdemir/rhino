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

package io.ryos.rhino.sdk;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.ryos.rhino.sdk.simulations.ReactiveSleepTestSimulation;
import org.junit.Rule;
import org.junit.Test;

public class ReactiveSleepTestSimulationTest {

  private static final String AUTH_ENDPOINT = "test.oauth2.endpoint";
  private static final String WIREMOCK_PORT = "wiremock.port";
  private static final String PROPERTIES_FILE = "classpath:///rhino.properties";

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());
  
  @Test
  public void testReactiveSleepTestSimulation() {
    System.setProperty(AUTH_ENDPOINT, "http://localhost:" + wireMockRule.port() + "/token");
    System.setProperty(WIREMOCK_PORT, Integer.toString(wireMockRule.port()));

    Simulation.getInstance(PROPERTIES_FILE, ReactiveSleepTestSimulation.class).start();
  }
}
