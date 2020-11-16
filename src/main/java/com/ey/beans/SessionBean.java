package com.ey.beans;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The type Session bean.
 */
@JacksonXmlRootElement(localName ="session-parameters")
public class SessionBean {
	
	@JacksonXmlProperty(localName = "client-type")
	private String clientType;

	/**
	 * Gets client type.
	 *
	 * @return the client type
	 */
	public String getClientType() {
		return clientType;
	}

	/**
	 * Sets client type.
	 *
	 * @param clientType the client type
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	
	
}
