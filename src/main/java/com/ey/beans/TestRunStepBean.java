package com.ey.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.ey.utilities.PropertyReader;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The type Test run step bean.
 */
@JacksonXmlRootElement(localName = "Entity")
public class TestRunStepBean {

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
	 * Instantiates a new Test run step bean.
	 */
	public TestRunStepBean() {
		this.entity = new Entity();
		this.setType("run-step");
		this.fields = new ArrayList<Field>();
		this.reader = new PropertyReader();
	}

	/**
	 * update test run step http xml request.
	 *
	 * @param status the test step status
	 * @return the update test run step http xml request
	 */
	public TestRunStepBean updateTestRunStepXML(String status) {
		this.entity.setType(this.getType());
		addUpdateFields(status);
		this.entity.setFields(fields);
		return this;
	}

	/**
	 * Create test run step http xml request.
	 *
	 * @param stepProperties the test run step details
	 * @param stepName       the test run step name
	 * @return the create test run step http xml response
	 */
	public TestRunStepBean createTestRunStepXML(LinkedHashMap<String, String> stepProperties, String stepName) {
		this.entity.setType(this.getType());
		addName(stepName);
		for ( String key : stepProperties.keySet() ) {
			if(key.equals("attachment")){
				addFieldAndValue(key, "Y");
			}
			else {
				addFieldAndValue(key, stepProperties.get(key));
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
	public void addFieldAndValue(String fieldName, String value) {
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

	/**
	 * Add status field to the http xml request.
	 *
	 * @param status the status value
	 */
	public void addStatus(String status) {
		Field obj = new Field();
		obj.setName("status");
		obj.setValue(status);
		fields.add(obj);
	}

	/**
	 * Add name field to the http xml request.
	 *
	 * @param name the name value
	 */
	public void addName(String name) {
		Field obj = new Field();
		obj.setName("name");
		obj.setValue(name);
		fields.add(obj);
	}

	/**
	 * Update status field to the http xml request.
	 *
	 * @param status the status value
	 */
	public void addUpdateFields(String status) {
		addStatus(status);
	}

}
