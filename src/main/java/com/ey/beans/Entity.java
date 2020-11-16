package com.ey.beans;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The type Entity.
 */
@JacksonXmlRootElement(localName = "Entity")
public class Entity {
	
	@JacksonXmlProperty(localName = "Type", isAttribute = true)
	private String type;

	/**
	 * Gets entity type.
	 *
	 * @return the entity type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets entity type.
	 *
	 * @param type the entity type
	 */
	public void setType(String type) {
		this.type = type;
	}

	@JacksonXmlProperty(localName = "Field")
	@JacksonXmlElementWrapper(localName = "Fields")
	private List<Field> fields;

	/**
	 * Gets entity fields.
	 *
	 * @return the entity fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * Sets entity fields.
	 *
	 * @param fields the entity fields
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
