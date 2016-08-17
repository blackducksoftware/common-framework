package com.blackducksoftware.tools.commonframework.core.config;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.commonframework.core.exception.CommonFrameworkException;

public class EProperties {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private Configuration config = new PropertiesConfiguration();
	private Properties propertiesObject; // lazily-generated

	public EProperties() {
		logger.debug("EProperties() constructor");
	}

	public void load(final File file) throws CommonFrameworkException {
		propertiesObject = null;

		final Configurations configs = new Configurations();
		try {
			config = configs.properties(file);
		} catch (final ConfigurationException e) {
			throw new CommonFrameworkException("Error loading properties from file " + file.getAbsolutePath() + ": "
					+ e.getMessage());
		}
		getProperties();
	}

	public Properties getProperties() {
		if (config == null) {
			logger.warn("getProperties(): config is null!");
		}
		if (propertiesObject != null) {
			return propertiesObject;
		}
		propertiesObject = new Properties();
		final Iterator<String> iter = config.getKeys();
		while (iter.hasNext()) {
			final String key = iter.next();
			logger.debug("getProperties(): including: " + key + "=" + config.getString(key));
			propertiesObject.put(key, config.getString(key));
		}
		return propertiesObject;
	}

	public void addAll(final Properties sourceProps) {
		propertiesObject = null;

		for (final Object keyObj : sourceProps.keySet()) {
			final String key = (String) keyObj;
			logger.debug("addAll(): adding: " + key + "=" + sourceProps.getProperty(key));
			config.addProperty(key, sourceProps.getProperty(key));
		}
		getProperties();
	}

	public void setProperty(final String key, final String value) {
		config.setProperty(key, value);
		getProperties().setProperty(key, value);
	}

	public boolean containsKey(final String key) {
		return config.containsKey(key);
	}

	public String get(final String key) {
		return config.getString(key);
	}

	public String getProperty(final String key) {
		return config.getString(key);
	}

	public Set<Object> keySet() {
		return getProperties().keySet();
	}

	public int size() {
		return getProperties().size();
	}

	@Override
	public String toString() {
		return getProperties().toString();
	}
}
