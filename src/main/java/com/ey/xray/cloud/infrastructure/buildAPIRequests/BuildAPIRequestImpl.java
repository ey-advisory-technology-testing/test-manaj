package com.ey.xray.cloud.infrastructure.buildAPIRequests;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * The xray.infrastructure package is internal to the project and should not be exposed outside. Any configurations, parameters and indirect method calls
 * should flow from the xray.application layer via the Initializer. In short, consuming xray.application should not know how or what is being done to initialize this project
 */
public class BuildAPIRequestImpl {

    public HttpRequest createPutRequest(String url, String payLoad) {
        return null;
    }

    public HttpRequest createGetRequest(String url) {
        return null;
    }

    public HttpRequest createPostRequest(String url, String payLoad) {

        // create a client
        var client = HttpClient.newHttpClient();

// create a request
        var request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("contentType", "application/json")
                .build();

// use the client to send the request
        //var response = client.send(request, new JsonBodyHandler<>(APOD.class));
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
        return request;
// the response
    }
}
