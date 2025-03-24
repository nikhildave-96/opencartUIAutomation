package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.TestUtils;

public class MyAccountTest extends BaseTest {

	@Test
	public void editAccountInfo() {
		LoginTest.verifyLogin();
		TestUtils.clickByVisibleText(readElementPropertyKey("editAccountLink"));
		TestUtils.verifyTextIsPresent(readElementPropertyKey("personalDetailsTxt"));
		TestUtils.enterText(readElementPropertyKey("telephoneInput"), "7777777777");
		TestUtils.clickByValue(readElementPropertyKey("continueBtn"));
		Assert.assertTrue(TestUtils.isTextVisible(readElementPropertyKey("accountUpdateSuccess")));
		Assert.assertTrue(TestUtils.isTextVisible(readElementPropertyKey("affiliateAccountLink")));
	}

	@Test
	public void verifyOrderHistory() {
		LoginTest.verifyLogin();
		TestUtils.clickByVisibleText(readElementPropertyKey("orderHistoryLink"));
		Assert.assertTrue(TestUtils.isTextVisible(readElementPropertyKey("noOrdersTxt")));
	}

	@Test
	public void verifyPasswordChangeValidation() {
		LoginTest.verifyLogin();
		TestUtils.clickByVisibleText(readElementPropertyKey("pwdChangeLink"));
		TestUtils.clickByValue(readElementPropertyKey("continueBtn"));
		TestUtils.verifyTextIsPresent(readElementPropertyKey("pwdChangeError"));
		TestUtils.enterText(readElementPropertyKey("passwordInput"), "12345");
		TestUtils.enterText(readElementPropertyKey("confirmPasswordInput"), "123456");
		TestUtils.clickByValue(readElementPropertyKey("continueBtn"));
		Assert.assertTrue(TestUtils.isTextVisible(readElementPropertyKey("pwdConfirmChangeError")));
	}
}
