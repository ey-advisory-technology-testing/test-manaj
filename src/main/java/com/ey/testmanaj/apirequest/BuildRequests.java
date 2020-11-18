package com.ey.testmanaj.apirequest;

import java.io.IOException;

import com.ey.testmanaj.connection.Authenticator;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;

/**
 * The type Build requests.
 */
public class BuildRequests {
	
	private static final HttpRequestFactory requestFactory = Authenticator.getRequestFactory();

	/**
	 * Create put http request.
	 *
	 * @param url - End point url for the request
	 * @param payLoad - xml resquest body
	 * @return the put http request
	 * @throws IOException the io exception
	 */
	public HttpRequest createPutRequest(GenericUrl url, String payLoad) throws IOException {

		return requestFactory.buildPutRequest(url,
				ByteArrayContent.fromString("application/xml", payLoad));
	}

	/**
	 * Create get http request.
	 *
	 * @param url - End point url for the request
	 * @return the get http request
	 * @throws IOException the io exception
	 */
	public HttpRequest createGetRequest(GenericUrl url) throws IOException {

		return requestFactory.buildGetRequest(url);
	}

	/**
	 * Create post request http request.
	 *
	 * @param url - End point url for the request
	 * @param payLoad - xml resquest body
	 * @return the post http request
	 * @throws IOException the io exception
	 */
	public HttpRequest createPostRequest(GenericUrl url, String payLoad) throws IOException {

		return requestFactory.buildPostRequest(url,
				ByteArrayContent.fromString("application/xml", payLoad));
	}

	/**
	 * Create delete http request.
	 *
	 * @param runStepUrl - End point url for the request
	 * @return the delete http request
	 * @throws IOException the io exception
	 */
	public HttpRequest createDeleteRequest(GenericUrl runStepUrl) throws IOException {

		return requestFactory.buildDeleteRequest(runStepUrl);
	}
}
