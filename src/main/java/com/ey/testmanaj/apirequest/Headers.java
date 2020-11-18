package com.ey.testmanaj.apirequest;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;

/**
 * The type Headers.
 */
public class Headers {

	private static final HttpHeaders header = new HttpHeaders();

	/**
	 * Sets headers for the http request.
	 *
	 * @param headers - The headers
	 * @param req - The http request
	 */
	public void setHeader(String headers,HttpRequest req) {
		header.set("Cookie", headers);
		req.setHeaders(header);
	}


	/**
	 * Get cookies.
	 *
	 * @param headers - The headers
	 * @return - The cookies
	 */
	public String getCookies(HttpHeaders headers) {

		String cookie;

		Object o = headers.getFirstHeaderStringValue("set-cookie");

		cookie = o.toString();

		return cookie;
	}
}
