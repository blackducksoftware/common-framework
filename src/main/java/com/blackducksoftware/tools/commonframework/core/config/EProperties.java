package com.blackducksoftware.tools.commonframework.core.config;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.blackducksoftware.tools.commonframework.core.exception.CommonFrameworkException;

public class EProperties {
	private Configuration props = new PropertiesConfiguration();
	private Properties propertiesObject; // lazily-generated

	public EProperties() {
		System.out.println("EProperties() constructor");
	}

	public void load(final File file) throws CommonFrameworkException {
		propertiesObject = null;

		final Configurations configs = new Configurations();
		try {
			props = configs.properties(file);
		} catch (final ConfigurationException e) {
			throw new CommonFrameworkException("Error loading properties from file " + file.getAbsolutePath() + ": "
					+ e.getMessage());
		}
		System.out.println("load(): hasNext: " + props.getKeys().hasNext());
	}

	public Properties getProperties() {
		if (props == null) {
			System.out.println("getProperties(): props is null!");
		}
		System.out.println("getProperties(): hasNext: " + props.getKeys().hasNext());
		if (propertiesObject != null) {
			return propertiesObject;
		}
		propertiesObject = new Properties();
		final Iterator<String> iter = props.getKeys();
		while (iter.hasNext()) {
			final String key = iter.next();
			propertiesObject.put(key, props.getString(key));
		}
		return propertiesObject;
	}

	public void addAll(final Properties sourceProps) {
		propertiesObject = null;

		for (final Object keyObj : sourceProps.keySet()) {
			final String key = (String) keyObj;
			props.addProperty(key, sourceProps.getProperty(key));
		}
	}

	public boolean containsKey(final String key) {
		return props.containsKey(key);
	}

	public String get(final String key) {
		return props.getString(key);
	}

	public String getProperty(final String key) {
		return props.getString(key);
	}

	public Set<Object> keySet() {
		return getProperties().keySet();
	}

	public int size() {
		return getProperties().size();
	}
}
