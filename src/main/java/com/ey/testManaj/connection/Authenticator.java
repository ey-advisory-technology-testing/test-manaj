package com.ey.testManaj.connection;

import com.ey.testManaj.transport.Transport;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * The type Authenticator.
 */
public class Authenticator {

	private static HttpRequestFactory requestFactory = null;
	private static NetHttpTransport conn = null;

	/**
	 * Get http request factory.
	 *
	 * @return The http request factory
	 */
	public static HttpRequestFactory getRequestFactory() {
		setRequestFactory();
		return requestFactory;
	}

	/**
	 * Sets request factory.
	 */
	public static void setRequestFactory() {
		getTransport();
		Authenticator.requestFactory = conn.createRequestFactory();
		
	}


	/**
	 * Gets http transport instance.
	 */
	public static void getTransport() {
		conn = Transport.getInstance();
		
	}
}
