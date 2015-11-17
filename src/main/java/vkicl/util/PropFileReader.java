package vkicl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PropFileReader {
	private static Logger log = Logger.getLogger(PropFileReader.class);
	private static PropFileReader INSTANCE = null;
	private static Properties app = null;
	private static Properties system = null;
	private static String SERVER_PROPERTIES = "vkicl.properties";
	private static String APP_PROPERTIES = "app.properties";

	public static PropFileReader getInstance() {
		if (null == INSTANCE)
			INSTANCE = new PropFileReader();
		return INSTANCE;
	}

	private PropFileReader() {
		try {
			app = new Properties();
			InputStream appStream = getClass().getClassLoader()
					.getResourceAsStream(APP_PROPERTIES);
			app.load(appStream);
			log.info("Properties Loaded");
			log.info(System.getProperty("catalina.base"));
			File configDir = new File(System.getProperty("catalina.base"),
					"conf");
			File configFile = new File(configDir, SERVER_PROPERTIES);
			InputStream systemStream = new FileInputStream(configFile);
			system = new Properties();
			system.load(systemStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String get(String key) {
		String value = "";
		try {
			if (StringUtils.isNotEmpty(key)) {
				key = key.replaceAll("query.unique.beWtUnit",
						"query.unique.wtUnit");
				key = key.replaceAll("query.unique.sectionWtUnit",
						"query.unique.wtUnit");
				key = key.replaceAll("query.unique.actualWtUnit",
						"query.unique.wtUnit");

				key = key.replaceAll("query.unique.dispatchedTo",
						"query.unique.customerLocation");
			}

			value = app.getProperty(key);
			if (StringUtils.isEmpty(value))
				log.error("Key with NULL Value = " + key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

	// Will fetch from Tomcat/conf/inv.properties
	public String getSystem(String key) {
		String value = "";
		try {
			value = system.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}
}
