package com.ey.beans;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ey.testmanaj.App;
import com.ey.utilities.PropertyReader;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The type Test set bean.
 */
@JacksonXmlRootElement(localName = "Entity")
public class TestSetBean {

	private Entity entity;

	@JacksonXmlProperty(localName = "Type", isAttribute = true)
	private String type;

	@JacksonXmlProperty(localName = "Field")
	@JacksonXmlElementWrapper(localName = "Fields")
	private List<Field> fields;

	/**
	 * The Reader.
	 */
	PropertyReader reader;

	/**
	 * Instantiates a new Test set bean.
	 */
	public TestSetBean() {
		this.entity = new Entity();
		this.setType("test-set");
		this.fields = new ArrayList<Field>();
		this.reader = new PropertyReader(App.getResourcesFilePath() + FileSystems.getDefault().getSeparator() + App.getConfigFileRoot() + "\\test-set.properties");
	}

	/**
	 * Create test set http xml request.
	 *
	 * @return the create test set http xml request
	 * @throws IOException the io exception
	 */
	public TestSetBean createTestSetXML() throws IOException {

		this.entity.setType(this.getType());
		addFields();
		this.entity.setFields(fields);
		return this;
	}

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


	/**
	 * Add field key-value pair to the http xml request.
	 *
	 * @param key the field key
	 * @param value the field value
	 */
	public void addField(String key, String value) {
		Field field = new Field();
		field.setName(key);
		field.setValue(value);
		fields.add(field);
	}

	/**
	 * Add fields to http xml request.
	 *
	 * @throws IOException the io exception
	 */
	public void addFields() throws IOException {
		Set<String> keys = reader.getAllKeys();
		
		for(String key:keys) {
			addField(key,reader.readValue(key));
		}
	}

}
