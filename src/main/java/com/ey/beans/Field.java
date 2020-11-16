package com.ey.beans;



import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


/**
 * The type Field.
 */
@JacksonXmlRootElement(localName = "Field")

public class Field {

	@JacksonXmlProperty(localName = "Name", isAttribute = true)
	private String name;
	
	@JacksonXmlProperty(localName = "Value")
	private String value;

	/**
	 * Gets field name.
	 *
	 * @return the field name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets field name.
	 *
	 * @param name the field name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets field value.
	 *
	 * @return the field value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets field value.
	 *
	 * @param value the field value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}




