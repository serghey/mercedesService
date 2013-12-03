package md.mercedes.service.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that loads the configuration properties
 *
 */
public class AppConfig {
	private static final Logger LOG = LoggerFactory
			.getLogger(AppConfig.class);

	private static final String ADMIN_SETTINGS_FILE_PATH = "configFileOverride";
	private static final String DEFAULT_ADMIN_SETTINGS_NAME = "/project.properties";
	private static final AppConfig INSTANCE = new AppConfig();
	private final Properties props = new Properties();

	private AppConfig() {
		init();
	}
	
	public static AppConfig getInstance(){
		return INSTANCE;
	}

	private void init() {
		String settingsFilePath = System.getProperty(ADMIN_SETTINGS_FILE_PATH);
		try {
			if (settingsFilePath == null) {
				props.load(this.getClass().getResourceAsStream(
						DEFAULT_ADMIN_SETTINGS_NAME));
			} else {
				props.load(new FileInputStream(settingsFilePath));
			}
		} catch (IOException e) {
			LOG.error("Could not find the properties configurations: {}",
					e.getMessage());
		}
	}

	public String getProperty(String propertyKey) {

		return props.getProperty(propertyKey);
	}
	
	public String getProperty(String propertyKey, String defaultValue) {

		return props.getProperty(propertyKey, defaultValue);
	}
}
