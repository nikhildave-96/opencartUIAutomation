package utils;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import driver.DriverManager;

/**
 * TestUtils class contains necessary utilities for performing actions like
 * click, filling input field, etc. on the webelement in one place so that
 * consuming tests can focus strictly on the business logic.
 * 
 * @author nikdav
 *
 */
public class TestUtils {
	public static WebDriver driver = DriverManager.getDriver();
	public static By by;
	private static final Logger logger = LogManager.getLogger(TestUtils.class);
	public static String locString;
	public static WebElement element;

	/**
	 * Initializes object of the WebDriverWait class.
	 * 
	 * @return Instance of WebDriverWait
	 */
	public static WebDriverWait waitForElement() {
		return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15));
	}

//	 shortcut for generating javadoc comments -> type /** and then hit enter
	/**
	 * Enters text inside the inputbox element.
	 * 
	 * @param locatorName     Placeholder of the inputbox
	 * @param textTobeEntered Text/Value to be filled inside the inputbox
	 * @author nikdav
	 */
	public static void enterText(String locatorName, String textTobeEntered) {
		locString = PropertiesReader.getXpathString("inputByPlaceHolder", locatorName);
		try {
			element = waitForElement().until(ExpectedConditions.elementToBeClickable(By.xpath(locString)));
			element.clear();
			element.sendKeys(textTobeEntered);
			logger.info("entered text inside inputbox - " + locString + " ...");
		} catch (Exception e) {
			logger.error("Unable to enter text inside inputBox - " + locString + e.getMessage());
		}
	}

	/**
	 * Clicks button by button's text.
	 * 
	 * @param buttonText Text associated with the button
	 * @author nikdav
	 */
	public static void clickButton(String buttonText) {
		locString = PropertiesReader.getXpathString("buttonByText", buttonText);
		try {
			element = waitForElement().until(ExpectedConditions.elementToBeClickable(By.xpath(locString)));
			element.click();
			logger.info("clicked button - " + locString + " ...");
		} catch (Exception e) {
			logger.error("Unable to click button: " + locString + e.getMessage());
		}
	}

	/**
	 * Verifies text visibility.
	 * 
	 * @param text Text to be verified as visible
	 * @author nikdav
	 */
	public static void verifyTextIsPresent(String text) {
		locString = PropertiesReader.getXpathString("objByText", text);
		try {
			waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locString)));
			logger.info("'" + text + "' text is verified ...");
		} catch (Exception e) {
			logger.error("Unable to verify text: " + locString + e.getMessage());
		}
	}

	/**
	 * Clicks link element.
	 * 
	 * @param hyperlink Actual hyperlink
	 * @param linkText  Visible text associated with the link element
	 * @author nikdav
	 */
	public static void clickLink(String hyperlink, String linkText) {
		locString = PropertiesReader.getXpathString("objByAttrsVals", "@href", hyperlink, "text()", linkText);
		try {
			element = waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locString)));
			element.click();
			logger.info("clicked link - " + locString + " ...");
		} catch (Exception e) {
			logger.error("Unable to click link: " + locString + e.getMessage());
		}
	}

	/**
	 * Checks whether text is visible or not.
	 * 
	 * @param text Text to be verified
	 * @return True or false depending upon text's visibility
	 * @author nikdav
	 */
	public static boolean isTextVisible(String text) {
		locString = PropertiesReader.getXpathString("objByText", text);
		try {
			element = waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locString)));
			logger.info("'" + text + "' text is visible ...");
			return element.isDisplayed();
		} catch (Exception e) {
			logger.error("Unable to find text: " + locString + e.getMessage());
			return false;
		}
	}

	/**
	 * Clicks element associated with the specified text.
	 * 
	 * @param text Text associated with the element.
	 * @author nikdav
	 */
	public static void clickByVisibleText(String text) {
		locString = PropertiesReader.getXpathString("objByText", text);
		try {
			element = waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locString)));
			element.click();
			logger.info("clicked element by text: " + locString + " ...");
		} catch (Exception e) {
			logger.error("Unable to click element by text: " + locString + e.getMessage());
		}
	}

	/**
	 * Clicks element associated with the specified title.
	 * 
	 * @param title Title associated with the element.
	 * @author nikdav
	 */
	public static void clickByTitle(String title) {
		locString = PropertiesReader.getXpathString("objByTitle", title);
		try {
			element = waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locString)));
			element.click();
			logger.info("clicked element by title: " + locString + " ...");
		} catch (Exception e) {
			logger.error("Unable to click element by title: " + locString + e.getMessage());
		}
	}

	/**
	 * Clicks element associated with the specified value.
	 * 
	 * @param value Value associated with the element.
	 * @author nikdav
	 */
	public static void clickByValue(String value) {
		locString = PropertiesReader.getXpathString("objByValue", value);
		try {
			element = waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locString)));
			element.click();
			logger.info("clicked element by value: " + locString + " ...");
		} catch (Exception e) {
			logger.error("Unable to click element by value: " + locString + e.getMessage());
		}
	}

	/**
	 * Clicks checkbox associated with the specified text.
	 * 
	 * @param text Text associated with the checkbox.
	 * @author nikdav
	 */
	public static void clickCheckbox(String text) {
		locString = PropertiesReader.getXpathString("objByAttrsVals", "@type", "checkbox", "@name", text);
		try {
			element = waitForElement().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locString)));
//			System.out.println("cb status: " + element.isSelected());
			if (!element.isSelected()) {
				element.click();
				logger.info("clicked checkbox by text: " + locString + " ...");
			} else {
				logger.info("click not needed for checkbox by text: " + locString + " ...");
			}
		} catch (Exception e) {
			logger.error("Unable to click element by value: " + locString + e.getMessage());
		}
	}

	/**
	 * Gets current browser tab's title.
	 * 
	 * @return Title of the browser tab
	 * @author nikdav
	 */
	public static String getTitle() {
		logger.info("fetching browser tab title ...");
		return driver.getTitle();
	}

	/**
	 * Get the Login worksheet data.
	 * 
	 * @return Array object containing data as rows and columns.
	 * @throws IOException
	 */
	@DataProvider(name = "loginData")
	public static Object[][] getLoginData() throws IOException {
		logger.info("returning loginData object ...");
		return ExcelUtils.getData("Login");
	}

	/**
	 * Get the Register worksheet data.
	 * 
	 * @return Array object containing data as rows and columns.
	 * @throws IOException
	 */
	@DataProvider(name = "registrationData")
	public static Object[][] getRegistrationData() throws IOException {
		logger.info("returning registrationData object ...");
		return ExcelUtils.getData("Register");
	}
}
