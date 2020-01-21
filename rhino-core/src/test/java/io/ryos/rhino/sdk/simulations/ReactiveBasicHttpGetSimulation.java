package io.ryos.rhino.sdk.simulations;

import static io.ryos.rhino.sdk.dsl.HttpDsl.from;
import static io.ryos.rhino.sdk.dsl.MaterializableDslItem.http;
import static io.ryos.rhino.sdk.utils.TestUtils.getEndpoint;

import io.ryos.rhino.sdk.SimulationConfig;
import io.ryos.rhino.sdk.annotations.Dsl;
import io.ryos.rhino.sdk.annotations.Simulation;
import io.ryos.rhino.sdk.annotations.UserRepository;
import io.ryos.rhino.sdk.dsl.LoadDsl;
import io.ryos.rhino.sdk.dsl.Start;
import io.ryos.rhino.sdk.users.repositories.BasicUserRepositoryFactoryImpl;
import java.util.UUID;

@Simulation(name = "Reactive Test")
@UserRepository(factory = BasicUserRepositoryFactoryImpl.class)
public class ReactiveBasicHttpGetSimulation {

  private static final String FILES_ENDPOINT = getEndpoint("files");
  private static final String X_REQUEST_ID = "X-Request-Id";
  private static final String X_API_KEY = "X-Api-Key";

  @Dsl(name = "Load DSL Request")
  public LoadDsl singleTestDsl() {
    return Start.dsl()
        .run(http("Files Request")
            .header(session -> from(X_REQUEST_ID, "Rhino-" + UUID.randomUUID().toString()))
            .header(X_API_KEY, SimulationConfig.getApiKey())
            .auth()
            .endpoint(FILES_ENDPOINT)
            .get()
            .saveTo("result"));
  }
}
