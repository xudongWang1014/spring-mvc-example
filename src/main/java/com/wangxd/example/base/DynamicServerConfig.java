package com.wangxd.example.base;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 动态系统属性文件配置. 为注入Servlet上下文准备
 */
public class DynamicServerConfig extends PropertyPlaceholderConfigurer {
	/**
	 * Set a location of a properties file to be loaded.
	 * <p>
	 * Can point to a classic properties file or to an XML file that follows JDK
	 * 1.5's properties XML format.
	 */
	private boolean localOverride = false;

	private Properties[] localProperties;
	private static Map<Object, Object> ctxPropertiesMap;
	private Resource[] locations;

	private final PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

	private boolean ignoreResourceNotFound = false;

	/**
	 * Set if failure to find the property resource should be ignored. True is
	 * appropriate if the properties file is completely optional. Default is
	 * "false".
	 */
	@Override
	public void setIgnoreResourceNotFound(boolean ignoreResourceNotFound) {
		this.ignoreResourceNotFound = ignoreResourceNotFound;
	}

	/**
	 * Set local properties, e.g. via the "props" tag in XML bean definitions.
	 * These can be considered defaults, to be overridden by properties loaded
	 * from files.
	 */
	@Override
	public void setProperties(Properties properties) {
		this.localProperties = new Properties[] { properties };
		super.setProperties(properties);
	}

	/**
	 * Set local properties, e.g. via the "props" tag in XML bean definitions,
	 * allowing for merging multiple properties sets into one.
	 */
	@Override
	public void setPropertiesArray(Properties[] propertiesArray) {
		this.localProperties = propertiesArray;
	}

	@Override
	public void setLocation(Resource location) {
		this.locations = new Resource[] { location };
		super.setLocation(location);
	}

	/**
	 * Set locations of properties files to be loaded.
	 * <p>
	 * Can point to classic properties files or to XML files that follow JDK
	 * 1.5's properties XML format.
	 */

	@Override
	public void setLocations(Resource[] locations) {
		this.locations = locations;
		super.setLocations(locations);
	}

	/**
	 * Set whether local properties override properties from files. Default is
	 * "false": properties from files override local defaults. Can be switched
	 * to "true" to let local properties override defaults from files.
	 */
	@Override
	public void setLocalOverride(boolean localOverride) {
		this.localOverride = localOverride;
	}

	@Override
	public Properties mergeProperties() throws IOException {
		Properties result = new Properties();

		if (this.localOverride) {
			// Load properties from file upfront, to let local properties
			// override.
			loadProperties(result);
		}

		if (this.localProperties != null) {
			for (int i = 0; i < this.localProperties.length; i++) {
				CollectionUtils.mergePropertiesIntoMap(this.localProperties[i], result);
			}
		}

		if (!this.localOverride) {
			// Load properties from file afterwards, to let those properties
			// override.
			loadProperties(result);
		}

		return result;
	}

	/**
	 * Load properties into the given instance.
	 * 
	 * @param props
	 *            the Properties instance to load into
	 * @throws IOException
	 *             in case of I/O errors
	 * @see #setLocations
	 */
	@Override
	protected void loadProperties(Properties props) throws IOException {
			if (this.locations == null) {
				if (logger.isInfoEnabled()) {
					logger.error("找不到locations的配置信息");
				}
				return ;
			}
			for (int i = 0; i < this.locations.length; i++) {
				Resource location = this.locations[i];
				if (logger.isInfoEnabled()) {
					logger.info("Loading properties file from " + location);
				}
				InputStream is = null;
				try {
					/**
					 * 根据环境变量判断是否在开发，生产和测试计算机上面
					 */
					String runEnv = System.getProperty(SpringPropertiesActiveUtils.SPRING_PROFILES_ACTIVE, "run");
					String str = new StringBuilder(".").append(runEnv).append(".").toString();
					logger.info("Properties file is " + runEnv);
					if (location.getFilename().indexOf(str) > 0) {
						is = location.getInputStream();
						this.propertiesPersister.load(props, is);
						break;
					}

				} catch (IOException ex) {
					if (this.ignoreResourceNotFound) {
						if (logger.isWarnEnabled()) {
							logger.warn("Could not load properties from " + location + ": " + ex.getMessage());
						}
					} else {
						throw ex;
					}
				} finally {
					if (is != null) {
						is.close();
					}
				}
			}
	}

	@Override
	 protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		    super.processProperties(beanFactoryToProcess, props);
		    ctxPropertiesMap = new HashMap<>();
		    for (Object key : props.keySet()) {
			    String keyStr = key.toString();
			    String value = props.getProperty(keyStr);
			    ctxPropertiesMap.put(keyStr, value);
		    }
	    }
	
	public static Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

}
