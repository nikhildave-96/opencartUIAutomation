package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.TestUtils;

/**
 * Testcase classes shouldn't contain anything apart from business logic. It
 * should just contain calls to utility functions and assertions to decide
 * testcase status.
 * 
 * @author nikdav
 *
 */
public class LoginTest extends BaseTest {

//	manual way of retrying failed test by explicitly specifying retryAnalyzer attribute
	@Test(priority = 8) // , retryAnalyzer = TestListener.class
	public void verifyLoginPageNavigation() {
		TestUtils.clickByTitle(readElementPropertyKey("myAccountTxt"));
		TestUtils.clickByVisibleText(readElementPropertyKey("loginTxt"));
		Assert.assertTrue(TestUtils.isTextVisible(readElementPropertyKey("returningCustomerTxt")));
	}

//	 lower the priority number, higher is the actual priority
	@Test(priority = 9) // (dependsOnMethods = { "verifyloginPageNavigation" })
	public static void verifyLogin() {
		TestUtils.clickByTitle(readElementPropertyKey("myAccountTxt"));
		TestUtils.clickByVisibleText(readElementPropertyKey("loginTxt"));
//		credentials for sensitive/production app should come from environment variables
//		login to be kept inside base test or not depends upon application requirement
		TestUtils.enterText(readElementPropertyKey("userNameInput"), readDataPropertyKey("oc_username"));
		TestUtils.enterText(readElementPropertyKey("passwordInput"), readDataPropertyKey("oc_password"));
		TestUtils.clickByValue(readElementPropertyKey("loginTxt"));
		Assert.assertTrue(TestUtils.isTextVisible(readElementPropertyKey("affiliateAccountLink")));
	}

	@Test(dataProvider = "loginData", dataProviderClass = TestUtils.class)
	public static void verifyLoginDP(String userName, String password, boolean loginStatus) {
		TestUtils.clickByTitle(readElementPropertyKey("myAccountTxt"));
		TestUtils.clickByVisibleText(readElementPropertyKey("loginTxt"));
		TestUtils.enterText(readElementPropertyKey("userNameInput"), userName);
		TestUtils.enterText(readElementPropertyKey("passwordInput"), password);
		TestUtils.clickByValue(readElementPropertyKey("loginTxt"));
		if (loginStatus) {
			Assert.assertTrue(TestUtils.isTextVisible(readElementPropertyKey("affiliateAccountLink")));
		} else {
			Assert.assertEquals(TestUtils.isTextVisible(readElementPropertyKey("loginError")), !loginStatus);
		}
	}
}
