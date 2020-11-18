package com.ey.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.ey.testmanaj.App;
import com.ey.utilities.PropertyReader;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The type Test run bean.
 */
@JacksonXmlRootElement(localName = "Entity")
public class TestRunBean {

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
	 * Instantiates a new Test run bean.
	 */
	public TestRunBean() {
		this.entity = new Entity();
		this.setType("run");
		this.fields = new ArrayList<Field>();
		reader = new PropertyReader(App.getResourcesFilePath() + "\\resources\\test-run.properties");
	}

	/**
	 * Create test run http xml request.
	 *
	 * @return the create test run http xml request
	 * @throws IOException the io exception
	 */
	public TestRunBean createTestRunXML() throws IOException {
		this.entity.setType(this.getType());
		addCreateFields();
		this.entity.setFields(fields);
		return this;
	}

	/**
	 * Update test run http xml request.
	 *
	 * @param Status the test run status
	 * @return the update test run http xml request
	 */
	public TestRunBean updateTestRunXML(String Status) {
		this.entity.setType(this.getType());
		addUpdateFields(Status);
		this.entity.setFields(fields);
		return this;
	}

	/**
	 * Add fields to test run http xml request.
	 *
	 * @param runProperties the test run step details
	 * @return the test run http xml request
	 */
	public TestRunBean updateAllFieldsTestRunXML(LinkedHashMap<String, String> runProperties){
		this.entity.setType(this.getType());
		for ( String key : runProperties.keySet() ) {
			if(key.equals("attachment")){
				addFieldAndValue(key, "Y");
			}
			else {
				addFieldAndValue(key, runProperties.get(key));
			}
		}
		this.entity.setFields(fields);
		return this;
	}

	/**
	 * Add field key-value pair to the http xml request.
	 *
	 * @param fieldName the field key
	 * @param value the field value
	 */
	private void addFieldAndValue(String fieldName, String value) {
		Field obj = new Field();
		obj.setName(fieldName);
		obj.setValue(value);
		fields.add(obj);
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

	private void addStatus(String Status) {
		Field status = new Field();
		status.setName("status");
		status.setValue(Status);
		fields.add(status);
	}
	
	private void addField(String key, String value) {
		Field field = new Field();
		field.setName(key);
		field.setValue(value);
		fields.add(field);
	}

	private void addCreateFields() throws IOException {
		
		Set<String> keys = reader.getAllKeys();
		
		for(String key:keys) {
			addField(key,reader.readValue(key));
		}
	}
	
	
	private void addUpdateFields(String status) {
		addStatus(status);
	}
}
