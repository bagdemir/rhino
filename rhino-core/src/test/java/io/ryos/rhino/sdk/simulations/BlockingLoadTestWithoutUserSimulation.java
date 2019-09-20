package io.ryos.rhino.sdk.simulations;

import io.ryos.rhino.sdk.annotations.*;
import io.ryos.rhino.sdk.providers.UUIDProvider;
import io.ryos.rhino.sdk.reporting.Measurement;
import javax.ws.rs.core.Response.Status;

@Simulation(name = "Server-Status Simulation Without User")
public class BlockingLoadTestWithoutUserSimulation {

  @Provider(factory = UUIDProvider.class)
  private String uuid;

  @Scenario(name = "scenario2")
  @Disabled
  public void scenario2(Measurement measurement) throws InterruptedException {
    //System.out.println("scenario2 - on " + Thread.currentThread().getMeasurementPoint());
    System.out.println("Test");
    measurement.measure("measurement1", Status.OK.toString());
  }
}

