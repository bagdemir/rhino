package io.ryos.rhino.sdk.dsl.specs;

import io.netty.handler.codec.http.HttpHeaders;
import io.ryos.rhino.sdk.data.UserSession;
import io.ryos.rhino.sdk.dsl.specs.impl.HttpSpecImpl.RetryInfo;
import io.ryos.rhino.sdk.reporting.MeasurementImpl;
import io.ryos.rhino.sdk.reporting.UserEvent;
import io.ryos.rhino.sdk.reporting.UserEvent.EventType;
import io.ryos.rhino.sdk.runners.EventDispatcher;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Response;
import org.asynchttpclient.netty.request.NettyRequest;

public class HttpSpecAsyncHandler implements AsyncHandler<Response> {

  private final String stepName;
  private final String specName;
  private final String userId;
  private final boolean measurementEnabled;
  private final boolean cumulativeMeasurement;
  private final MeasurementImpl measurement;
  private volatile long start = -1;
  private volatile int status;
  private final Response.ResponseBuilder builder = new Response.ResponseBuilder();
  private final EventDispatcher eventDispatcher;
  private final RetryInfo retryInfo;

  public HttpSpecAsyncHandler(final UserSession session,
      final HttpSpec spec,
      final EventDispatcher eventDispatcher) {

    this.measurement = new MeasurementImpl(spec.getTestName(), session.getUser().getId());
    this.specName = spec.getTestName();
    this.userId = session.getUser().getId();
    this.stepName = spec.getMeasurementPoint();
    this.eventDispatcher = eventDispatcher;
    this.measurementEnabled = spec.isMeasurementEnabled();
    this.retryInfo = spec.getRetryInfo();
    this.cumulativeMeasurement = spec.isCumulativeMeasurement();
  }

  @Override
  public State onStatusReceived(final HttpResponseStatus responseStatus) {

    builder.reset();
    builder.accumulate(responseStatus);
    this.status = responseStatus.getStatusCode();

    return State.CONTINUE;
  }

  @Override
  public State onHeadersReceived(final HttpHeaders headers) {
    builder.accumulate(headers);
    return State.CONTINUE;
  }

  @Override
  public State onBodyPartReceived(final HttpResponseBodyPart bodyPart) {
    builder.accumulate(bodyPart);
    return State.CONTINUE;
  }

  /**
   * Handling the errors came out of the Http client, e.g connection timeouts.
   * <p>
   *
   * @param t Throwable instance.
   */
  @Override
  public void onThrowable(final Throwable t) {
    // There is no start event for user measurement, so we need to create one.
    // In Error case, we just want to make the error visible in stdout. We don't actually record
    // any metric here, thus the start/end timestamps are irrelevant.
    if (!measurementEnabled) {
      this.start = System.currentTimeMillis();
      var userEventStart = new UserEvent(
          "",
          userId,
          specName,
          start,
          start,
          0L,
          EventType.START,
          "",
          userId
      );

      measurement.record(userEventStart);
    }

    // Store the error event in the measurement stack.
    measurement.measure(t.getMessage(), "N/A");

    var userEventEnd = new UserEvent(
        "",
        userId,
        specName,
        start,
        0,
        0L,
        EventType.END,
        "",
        userId
    );

    measurement.record(userEventEnd);

    eventDispatcher.dispatchEvents(measurement);
  }

  @Override
  public Response onCompleted() {

    Response response = builder.build();
    var httpResponse = new HttpResponse(response);

    if (measurementEnabled && isReadyToMeasure(httpResponse)) {
      completeMeasurement();
    }

    return response;
  }

  public void completeMeasurement() {

    var elapsed = System.currentTimeMillis() - start;

    measurement.measure(stepName, String.valueOf(status));
    var userEventEnd = new UserEvent(
        "",
        userId,
        specName,
        start,
        start + elapsed,
        elapsed,
        EventType.END,
        "",
        userId
    );

    measurement.record(userEventEnd);

    eventDispatcher.dispatchEvents(measurement);
  }

  private boolean isReadyToMeasure(HttpResponse httpResponse) {
    if (retryInfo == null) {
      return true;
    }

    if (!cumulativeMeasurement) {
      return true;
    }

    return !retryInfo.getPredicate().test(httpResponse);
  }

  @Override
  public void onRequestSend(NettyRequest request) {

    if (measurementEnabled) {

      // if the start timestamp is not set, then set it. Otherwise, if it is a cumulative
      // measurement, and the start is already set, then skip it.
      if (start < 0 || !cumulativeMeasurement) {
        this.start = System.currentTimeMillis();
      }

      var userEventStart = new UserEvent(
          "",
          userId,
          specName,
          start,
          start,
          0L,
          EventType.START,
          "",
          userId
      );

      measurement.record(userEventStart);
    }
  }
}