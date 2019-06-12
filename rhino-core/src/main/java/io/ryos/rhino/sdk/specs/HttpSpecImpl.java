package io.ryos.rhino.sdk.specs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import org.asynchttpclient.Response;

/**
 * Html implementation of {@link Spec}.
 * <p>
 *
 * @author Erhan Bagdemir
 * @since 1.1.0
 */
public class HttpSpecImpl extends AbstractSpec implements HttpSpec {

  private InputStream toUpload;
  private String enclosingSpec;
  private String stepName;

  private List<Function<Response, Entry<String, List<String>>>> headers = new ArrayList<>();
  private List<Function<Response, Entry<String, List<String>>>> queryParams = new ArrayList<>();

  private Method httpMethod;
  private Function<Response, String> endpoint;

  /**
   * Creates a new {@link HttpSpecImpl}.
   * <p>
   *
   * @param measurement The name of the measurement.
   */
  public HttpSpecImpl(String measurement) {
    this.stepName = measurement;
  }

  @Override
  public HttpSpec get() {
    this.httpMethod = Method.GET;
    return this;
  }

  @Override
  public HttpSpec head() {
    this.httpMethod = Method.HEAD;
    return this;
  }

  @Override
  public HttpSpec put() {
    this.httpMethod = Method.PUT;
    return this;
  }

  @Override
  public HttpSpec patch() {
    this.httpMethod = Method.PATCH;
    return this;
  }

  @Override
  public HttpSpec post() {
    this.httpMethod = Method.POST;
    return this;
  }

  @Override
  public HttpSpec delete() {
    this.httpMethod = Method.DELETE;
    return this;
  }

  @Override
  public HttpSpec options() {
    this.httpMethod = Method.OPTIONS;
    return this;
  }

  @Override
  public HttpSpec endpoint(final String endpoint) {
    this.endpoint = (r) -> endpoint;
    return this;
  }

  @Override
  public HttpSpec endpoint(Function<Response, String> endpoint) {
    this.endpoint = endpoint;
    return this;
  }

  @Override
  public HttpSpec header(String key, List<String> values) {
    this.headers.add((e) -> Map.entry(key, values));
    return this;
  }

  @Override
  public HttpSpec header(String key, String value) {
    this.headers.add((e) -> Map.entry(key, Collections.singletonList(value)));
    return this;
  }

  @Override
  public HttpSpec header(Function<Response, Entry<String, List<String>>> headerFunction) {
    this.headers.add(headerFunction);
    return this;
  }

  @Override
  public HttpSpec queryParam(String key, List<String> values) {
    this.headers.add((e) -> Map.entry(key, values));
    return this;
  }

  @Override
  public HttpSpec queryParam(String key, String value) {
    this.headers.add((e) -> Map.entry(key, Collections.singletonList(value)));
    return this;
  }

  @Override
  public HttpSpec queryParam(Function<Response, Entry<String, List<String>>> headerFunction) {
    this.headers.add(headerFunction);
    return this;
  }

  @Override
  public HttpSpec upload(final InputStream inputStream) {
    this.toUpload = inputStream;
    return this;
  }

  @Override
  public InputStream getUploadContent() {
    return toUpload;
  }

  @Override
  public Method getMethod() {
    return httpMethod;
  }

  @Override
  public List<Function<Response, Entry<String, List<String>>>> getHeaders() {
    return headers;
  }

  @Override
  public List<Function<Response, Entry<String, List<String>>>> getQueryParameters() {
    return queryParams;
  }

  @Override
  public Spec withName(final String name) {
    this.enclosingSpec = name;
    return this;
  }

  @Override
  public String getName() {
    return this.stepName;
  }

  @Override
  public Function<Response, String> getEndpoint() {
    return endpoint;
  }

  @Override
  public String getTestName() {
    return enclosingSpec;
  }

  @Override
  public void setTestName(String testName) {
    this.enclosingSpec = testName;
  }

  @Override
  public String getMeasurementName() {
    return stepName;
  }
}
