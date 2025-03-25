package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// formatting code manually --> ctrl + shift + f 
// formatting code automatically upon saving file --> Window -> Preferences -> Java -> Editor -> SaveActions and check the Format source code 
/**
 * PropertiesReader class contains useful methods to read/fetch data from
 * .properties files.
 * 
 * @author nikdav
 *
 */
public class PropertiesReader {
	static Properties dataProp;
	static Properties locProp;
	static Properties elementProp;
	static HashMap<String, String> configMap;
	static HashMap<String, String> locatorMap;
	static HashMap<String, String> appElementMap;
	private static final Logger logger = LogManager.getLogger(PropertiesReader.class);

	/**
	 * Static block helping initialize the maps.
	 */
	static {
		initConfigMap();
		initLocatorMap();
		initElementMap();
	}

	/**
	 * Reads specified key's value from the data.properties file.
	 * 
	 * @param key Key present inside data.properties file
	 * @return Value associated with the key
	 * @author nikdav
	 */
	public static String readKey(String key) {
		String envValue = System.getenv(key);
		if (configMap.get(key) == null && envValue == null) {
			throw new NullPointerException("'" + key
					+ "' neither exists inside data.properties file nor defined as an environment variable ...");
		} else if (configMap.get(key) != null && envValue != null) {
			throw new RuntimeException(
					"'" + key + "' exists inside data.properties file but also defined as an environment variable ...");
		} else if (configMap.get(key) != null && envValue == null) {
			logger.info("'" + key + "' key read from .properties file ...");
			return configMap.get(key).trim();
		} else {
			logger.info("'" + key + "' key read as an environment variable ...");
			return envValue.trim();
		}
	}

	/**
	 * Reads specified key's value from the opencart.properties file
	 * 
	 * @param key Key present inside opencart.properties file
	 * @return Value associated with the key
	 * @author nikdav
	 */
	public static String readElementKey(String key) {
		if (appElementMap.get(key) == null) {
			logger.error("'" + key + "' does not exists inside .properties file ...");
			return null;
		} else {
//			logger.info("'" + key + "' key read from .properties file ...");
			return appElementMap.get(key).trim();
		}
	}

	/**
	 * Parameterizes xpath by substituting values provided.
	 * 
	 * @param locatorName Locator key present inside locator.properties file.
	 * @param strings     Variable string arguments to be substituted.
	 * @return Parameterized xpath string to be consumed by By.xpath method.
	 * @author nikdav
	 */
	public static String getXpathString(String locatorName, String... strings) {
		String locFrmMap = locatorMap.get(locatorName).trim();
		if (!locFrmMap.isEmpty()) {
//			logger.info("locFrmMap: " + locFrmMap);
			for (int i = 0; i <= strings.length - 1; i++) {
				locFrmMap = locFrmMap.replaceFirst("#text\\d", strings[i]);
			}
//			logger.info("locatorString: " + locFrmMap + " ...");
			return locFrmMap;
		} else {
			logger.error("'" + locatorName + "' does not exists inside .properties file ...");
			return null;
		}
	}

	/**
	 * Initializing and populating the config map with the content from
	 * data.properties file.
	 * 
	 * @author nikdav
	 */
	public static void initConfigMap() {
//		 System.getProperty("user.dir") provides project's root path
//		 \\src\\test.... is relative path from project's root directory and \\ is
//		 needed as to overcome syntax error for using single \
		try {
			FileInputStream data = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/java/resources/data.properties");
			dataProp = new Properties();
			dataProp.load(data);
			logger.info("loaded data from data.properties file ...");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		configMap = new HashMap<String, String>();
		for (String propKey : dataProp.stringPropertyNames()) {
			configMap.put(propKey, dataProp.getProperty(propKey));
		}
	}

	/**
	 * Initializing and populating the locator map with the content from
	 * locator.properties file.
	 * 
	 * @author nikdav
	 */
	public static void initLocatorMap() {
		try {
			FileInputStream data = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/java/resources/locator.properties");
			locProp = new Properties();
			locProp.load(data);
			logger.info("loaded data from locator.properties file ...");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		locatorMap = new HashMap<String, String>();
		for (String propKey : locProp.stringPropertyNames()) {
			locatorMap.put(propKey, locProp.getProperty(propKey));
		}
	}

	/**
	 * Initializing and populating the locator map with the content from
	 * opencart.properties file.
	 * 
	 * @author nikdav
	 */
	public static void initElementMap() {
		try {
			FileInputStream data = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/java/resources/opencart.properties");
			elementProp = new Properties();
			elementProp.load(data);
			logger.info("loaded data from opencart.properties file ...");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		appElementMap = new HashMap<String, String>();
		for (String propKey : elementProp.stringPropertyNames()) {
			appElementMap.put(propKey, elementProp.getProperty(propKey));
		}
	}

	public static void main(String[] args) {
		System.out.println(readKey("url"));
		System.out.println(System.getProperty("os.name"));
	}
}
