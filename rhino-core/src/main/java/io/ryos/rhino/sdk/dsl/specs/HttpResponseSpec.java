package io.ryos.rhino.sdk.dsl.specs;

/**
 * Terminating specification used to store the response object.
 * <p>
 *
 * @author Erhan Bagdemir
 * @since 1.1.0
 */
public interface HttpResponseSpec extends Spec {

  HttpSpec saveTo(String keyName);

  HttpSpec saveTo(String keyName, Scope scope);
}