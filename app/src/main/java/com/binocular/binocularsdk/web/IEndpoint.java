package com.binocular.binocularsdk.web;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * Interface for the endpoints
 */
public interface IEndpoint {
    HttpUriRequest getHttpRequest();
}
