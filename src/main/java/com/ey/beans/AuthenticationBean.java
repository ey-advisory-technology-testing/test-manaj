package com.ey.beans;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The type Authentication bean.
 */
@JacksonXmlRootElement(localName = "alm-authentication")
public class AuthenticationBean {
	
	private String user;
	private String password;


	/**
	 * Gets username.
	 *
	 * @return the username
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets username.
	 *
	 * @param user the username
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password.
	 *
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

}
