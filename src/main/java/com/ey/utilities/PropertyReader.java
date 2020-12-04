package com.ey.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * The type Property reader.
 */
public class PropertyReader {
	
	private String propFile;

	/**
	 * Instantiates a new Property reader.
	 *
	 * @param propFileLocation the property file location
	 */
	public PropertyReader(String propFileLocation) {
		this.propFile = propFileLocation;
	}

	/**
	 * Read value from property file for given key.
	 *
	 * @param key the key
	 * @return the value for the given key
	 * @throws IOException the io exception
	 */
	public String readValue(String key) throws IOException
	{

		FileInputStream fis = getPropFile();
		Properties prop = new Properties();
		
		prop.load(fis);
		return prop.getProperty(key);
	}

	/**
	 * Instantiates a new Property reader.
	 */
	public PropertyReader() {
		this.propFile = "./src/main/resources/config.properties";
	}

	/**
	 * Write value to the given key.
	 *
	 * @param key   the key
	 * @param value the value
	 * @throws ConfigurationException the configuration exception
	 */
	public void writeValue(String key, String value) throws ConfigurationException {
		PropertiesConfiguration config = new PropertiesConfiguration(this.propFile);
		config.setProperty(key, value);
		config.save();
	}

	/**
	 * Gets all keys from the property file.
	 *
	 * @return the all keys
	 * @throws IOException the io exception
	 */
	public Set<String> getAllKeys() throws IOException{
		FileInputStream fis = getPropFile();
		Properties prop = new Properties();
		
		prop.load(fis);
		return prop.stringPropertyNames();
	}

	/**
	 * Gets property file.
	 *
	 * @return property file input stream
	 */
	public FileInputStream getPropFile() {
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(new File(this.propFile));
			
		}catch (Exception e) {
			throw new RuntimeException(" file Not found "+ this.propFile + " "+e.getLocalizedMessage());
		}
		return fileInputStream;
	}
}
