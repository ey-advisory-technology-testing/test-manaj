package com.ey.testManaj.urls;

import java.io.IOException;

import com.ey.testManaj.App;
import com.ey.beans.SessionBean;
import com.ey.utilities.PropertyReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.api.client.http.GenericUrl;

/**
 * The type Build url.
 */
public class BuildURL {

	private static String baseURL;
	private static String domain;
	private static String project;
	
	private static PropertyReader propReader;

	/**
	 * Sets ALM resources path, base url, domain and project.
	 *
	 * @throws IOException the io exception
	 */
	public static void setEnv() throws IOException {
		BuildURL.propReader = new PropertyReader(App.getResourcesFilePath() + "\\resources\\configuration.properties");
		BuildURL.baseURL = propReader.readValue("baseURL");
		BuildURL.domain = propReader.readValue("domain");
		BuildURL.project = propReader.readValue("project");
	}

	/**
	 * Gets alm authentication url.
	 *
	 * @return the alm authentication url
	 */
	public static String getAuthenticationURL() {

		return baseURL + "authentication-point/authenticate";
	}

	/**
	 * Gets check authentication url.
	 *
	 * @return the check authentication url
	 */
	public static String getCheckAuthentication() {

		return baseURL + "?login-form-required=y/rest/is-authenticated";
	}

	/**
	 * Gets en point url for all the alm entities.
	 *
	 * @param urlType the entity for which url is requested
	 * @return the end point url for the entity
	 */
	public static String getURL(String urlType) {
		switch (urlType) {
		case "authentication":
			return getAuthenticationURL();
		case "isAuthenticated":
			return getCheckAuthentication();
		case "testPlan":
			return getTestsURL();
		case "getSession":
			return getSessionURL();
		case "createTestRun":
			return getCreateTestRunURL();
		case "createTestSet":
			return createTestSet();
		case "testInstance":
			return getTestInstanceURL();
		case "testRun":
			return getTestRunURL();
		case "testSetFolder":
			return getTestSetFolderURL();
		case "baseProject":
			return getProjectURL();
		default:
			return "";
		}
	}

	/**
	 * Gets test set folder end point url.
	 *
	 * @return the test set folder end point url
	 */
	public static String getTestSetFolderURL() {

		return baseURL + "rest/domains/" + domain + "/projects/" + project + "/test-set-folders";
	}

	/**
	 * Gets test run end point url.
	 *
	 * @return the test run end point url
	 */
	public static String getTestRunURL() {

		return baseURL + "rest/domains/" + domain + "/projects/" + project + "/runs";
	}

	/**
	 * Gets project end point url.
	 *
	 * @return the project end point url
	 */
	public static String getProjectURL() {

		return baseURL + "rest/domains/" + domain + "/projects/" + project;
	}

	/**
	 * Gets test instance end point url.
	 *
	 * @return the test instance end point url
	 */
	public static String getTestInstanceURL() {

		return baseURL + "rest/domains/" + domain + "/projects/" + project + "/test-instances";
	}

	/**
	 * Gets Create testset end point url.
	 *
	 * @return the create testset end point url
	 */
	public static String createTestSet() {

		return baseURL + "rest/domains/" + domain + "/projects/"  + project + "/test-sets";
	}


	/**
	 * Gets create test run end point url.
	 *
	 * @return the create test run end point url
	 */
	public static String getCreateTestRunURL() {

		return baseURL + "rest/domains/" + domain + "/projects/" + project + "/runs";
	}

	/**
	 * Gets alm session end point url.
	 *
	 * @return the alm session end point url
	 */
	public static String getSessionURL() {

		return baseURL + "rest/site-session";
	}

	/**
	 * Gets generic url for the given string end point url.
	 *
	 * @param endPoint the end point url
	 * @return the generic url
	 */
	public static GenericUrl getGenericURL(String endPoint) {
		String tempURL = getURL(endPoint);
		GenericUrl url = null;
		if (!("".equalsIgnoreCase(tempURL))) {
			url = new GenericUrl(getURL(endPoint));
		}

		return url;
	}


	/**
	 * Build session xml request string.
	 *
	 * @return the session xml request string
	 * @throws JsonProcessingException the json processing exception
	 */
	public static String buildSessionXML() throws JsonProcessingException {
		SessionBean sBean = new SessionBean();
		sBean.setClientType("REST Client");
		XmlMapper mapper = new XmlMapper();
		return mapper.writeValueAsString(sBean);
	}

	/**
	 * Gets tests end point url.
	 *
	 * @return the tests end point url
	 */
	public static String getTestsURL() {

		return baseURL + "rest/domains/" + domain + "/projects/" + project + "/tests";
	}

}
