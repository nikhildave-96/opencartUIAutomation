package base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import driver.DriverManager;
import utils.ExtentReporter;
import utils.PropertiesReader;

/**
 * BaseTest class contains browser initialization and termination method needed
 * before each @Test testcase inside testcase classes.
 * 
 * @author nikdav
 *
 */
public class BaseTest {
	static TakesScreenshot screenshot;
	private static final Logger logger = LogManager.getLogger(BaseTest.class);

	/**
	 * Captures screenshot of failed testcase.
	 * 
	 * @param result Object of TestNG's ITestResult interface
	 * @throws IOException
	 * @author nikdav
	 */
	public static void captureScreenshotOnFail(ITestResult result) throws IOException {
//		 \\screenshots\\ will create a folder if it doesn't exists already
		String ssDest = System.getProperty("user.dir") + "/screenshots/" + result.getName() + ".png";
//		 failure has a status of 2
		if (result.getStatus() == ITestResult.FAILURE) {
			screenshot = (TakesScreenshot) DriverManager.getDriver();
			File src = screenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File(ssDest));
			logger.info("screenshot captured for failed test -> '" + result.getName() + "' ...");
		}
	}

	/**
	 * Reads property value corresponding to the provided key.
	 * 
	 * @param key Key present inside the data.properties file
	 * @return Value associated with the key
	 * @author nikdav
	 */
	public static String readDataPropertyKey(String key) {
		return PropertiesReader.readKey(key);
	}

	/**
	 * Reads property value corresponding to the provided key.
	 * 
	 * @param key Key present inside the opencart.properties file
	 * @return Value associated with the key
	 * @author nikdav
	 */
	public static String readElementPropertyKey(String key) {
		return PropertiesReader.readElementKey(key);
	}

	/**
	 * Navigates to the configured application url.
	 */
	public static void launchApplication() {
		DriverManager.getDriver().get(PropertiesReader.readKey("oc_url"));
		logger.info("navigated to the application url ...");
	}

	/**
	 * Executes before executing the first @Test method.
	 * 
	 * @author nikdav
	 */
	@BeforeTest
	public static void beforeTests() {
		ExtentReporter.generateReport();
	}

	/**
	 * Initializes browser before every @Test testcase.
	 * 
	 * @BeforeTest method executes only once before execution of all @Test methods.
	 * @BeforeMethod method executes every time before execution of each @Test
	 *               method.
	 * @param m Object of Java's Method class
	 * @author nikdav
	 */
	@BeforeMethod
	public static void setUp(Method m) {
		ExtentReporter.extentTest = ExtentReporter.extentReport.createTest(m.getName());
		DriverManager.initBrowser();
		launchApplication();
		logger.info("========================= starting test case execution '" + m.getName() + "' ...");
	}

	/**
	 * Captures test information and terminates browser after every @Test testcase.
	 * 
	 * @AfterTest method executes only once after execution of last @Test method
	 * @AfterMethod method executes every time after execution of every @Test method
	 * @param result Object of TestNG's ITestResult interface
	 * @author nikdav
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@AfterMethod
	public static void tearDown(ITestResult result) throws IOException, InterruptedException {
		logger.info("========================= ending test case execution '" + result.getName() + "' ...");
		ExtentReporter.addTestStatus(result);
		captureScreenshotOnFail(result);
		DriverManager.closeBrowser();
	}

	/**
	 * Executes after executing the last @Test method.
	 * 
	 * @author nikdav
	 */
	@AfterTest
	public static void afterTests() {
		ExtentReporter.extentReport.flush();
	}
}
