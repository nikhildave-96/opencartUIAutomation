package driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
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
	public static WebDriver driver; // normal execution

//	 log4j related configuration file (.xml/.yaml) should be present under
//	 'src/main/resources' or 'src/test/resources' directory/folder
	private static final Logger logger = LogManager.getLogger(DriverManager.class);

//	initializing a threadlocal variable driver of WebDriver data type
//	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>(); // thread-safe parallel execution

	/**
	 * Get WebDriver object.
	 * 
	 * @return WebDriver instance
	 * @author nikdav
	 */
	public static WebDriver getDriver() {
		return driver; // normal execution
//		use .get() method of ThreadLocal class to return threadlocal variable driver reference
//		return driver.get(); // thread-safe parallel execution
	}

	/**
	 * Instantiates configured browser's instance.
	 * 
	 * @author nikdav
	 */
	public static void initBrowser() {
		String browserName = PropertiesReader.readKey("oc_browser").toLowerCase();
		logger.info("launching '" + browserName + "' browser ...");
//		normal execution; comment the if condition for thread-safe parallel execution
		if (driver == null) {
			switch (browserName) {
			case "chrome":
//				 options can be added to capabilities using .setCapability() method and the
//				 DesiredCapabilities's object can be passed as an argument while creating
//				 driver's object
				ChromeOptions chOptions = new ChromeOptions();
// 				only headless works for GitHub Actions, else 'org.openqa.selenium.SessionNotCreatedException' is thrown, remove headless when running test locally if required
				chOptions.addArguments("headless");
				driver = new ChromeDriver(chOptions); // normal execution
//				using .set() method of ThreadLocal class to assign browser/driver reference to threadlocal variable driver
//				driver.set(new ChromeDriver(chOptions)); // thread-safe parallel execution
				logger.info(
						"Thread ID is: " + Thread.currentThread().getId() + ", Driver reference is: " + getDriver());
//				 selenium 4 onwards doesn't require configuring system property like below
//				 System.setProperty("webdriver.chrome.driver", "<pathToDriverExefile>");
				logger.info("launched chrome browser ...");
				break;
			case "firefox":
				FirefoxOptions ffOptions = new FirefoxOptions();
				ffOptions.addArguments("headless"); // "incognito", "start-maximized"
				driver = new FirefoxDriver(ffOptions); // normal execution
//				driver.set(new FirefoxDriver(ffOptions)); // thread-safe parallel execution
				logger.info(
						"Thread ID is: " + Thread.currentThread().getId() + ", Driver reference is: " + getDriver());
				logger.info("launched firefox browser ...");
				break;
			case "edge":
				EdgeOptions edOptions = new EdgeOptions();
				edOptions.addArguments("headless");
				driver = new EdgeDriver(edOptions); // normal execution
//				driver.set(new EdgeDriver(edOptions)); // thread-safe parallel execution
				logger.info(
						"Thread ID is: " + Thread.currentThread().getId() + ", Driver reference is: " + getDriver());
				logger.info("launched edge browser ...");
				break;
			default:
				logger.error("incorrect browser value provided ...");
			}
		}
		driver.manage().window().setSize(new Dimension(1920, 1080)); // normal execution
//		driver.get().manage().window().setSize(new Dimension(1920, 1080)); // thread-safe parallel execution
	}

	/**
	 * Closes all open browser instances.
	 * 
	 * @author nikdav
	 */
	public static void closeBrowser() {
//		// normal execution
		if (driver != null) {
			driver.quit();
			logger.info("closed browser ...");
			driver = null;
		}
//		// thread-safe parallel execution
//		if (getDriver() != null) {
//			getDriver().quit();
//			logger.info("closed browser ...");	
//			use .remove() method of ThreadLocal class to clear memory allocation of threadlocal variable driver to avoid memory leaks
//			driver.remove();
//		}
	}
}
