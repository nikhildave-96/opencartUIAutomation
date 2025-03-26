package driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import utils.PropertiesReader;

/**
 * DriverManager class helps initialize configured browser's instance and quit
 * browser upon test completion.
 * 
 * @author nikdav
 *
 */
public class DriverManager {
	public static WebDriver driver;
//	 log4j related configuration file (.xml/.yaml) should be present under
//	 'src/main/resources' or 'src/test/resources' directory/folder
	private static final Logger logger = LogManager.getLogger(DriverManager.class);

	/**
	 * Get WebDriver object.
	 * 
	 * @return WebDriver instance
	 * @author nikdav
	 */
	public static WebDriver getDriver() {
		return driver;
	}

	/**
	 * Instantiates configured browser's instance.
	 * 
	 * @author nikdav
	 */
	public static void initBrowser() {
		logger.info("running on system '" + System.getenv("os") + "' ...");
		String browserName = PropertiesReader.readKey("browser").toLowerCase();
		logger.info("launching '" + browserName + "' browser ...");
		if (driver == null) {
			switch (browserName) {
			case "chrome":
//				 options can be added to capabilities using .setCapability() method and the
//				 DesiredCapabilities's object can be passed as an argument while creating
//				 driver's object
				ChromeOptions chOptions = new ChromeOptions();
				chOptions.addArguments("headless");
				driver = new ChromeDriver(chOptions);
//				 selenium 4 onwards doesn't require configuring system property like below
//				 System.setProperty("webdriver.chrome.driver", "<pathToDriverExefile>");
				logger.info("launched chrome browser ...");
				break;
			case "firefox":
				FirefoxOptions ffOptions = new FirefoxOptions();
				ffOptions.addArguments("incognito", "start-maximized");
				driver = new FirefoxDriver(ffOptions);
				logger.info("launched firefox browser ...");
				break;
			case "edge":
				EdgeOptions edOptions = new EdgeOptions();
				edOptions.addArguments("incognito", "headless");
				driver = new EdgeDriver(edOptions);
				logger.info("launched edge browser ...");
				break;
			default:
				logger.error("incorrect browser value provided ...");
			}
		}
//		driver.manage().window().setSize(new Dimension(1920, 1080));
	}

	/**
	 * Closes all open browser instances.
	 * 
	 * @author nikdav
	 */
	public static void closeBrowser() {
		if (driver != null) {
			driver.quit();
			logger.info("closed browser ...");
			driver = null;
		}
	}
}
