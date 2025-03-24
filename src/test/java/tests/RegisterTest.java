package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.TestUtils;

public class RegisterTest extends BaseTest {
	@Test(dataProvider = "registrationData", dataProviderClass = TestUtils.class)
	public static void verifyUserRegisterDP(String fName, String lName, String email, String telephone, String pwd,
			String pwdConfirm, String newsSubscription) {
		TestUtils.clickByTitle(readElementPropertyKey("myAccountTxt"));
		TestUtils.clickByVisibleText(readElementPropertyKey("registerTxt"));

		TestUtils.enterText(readElementPropertyKey("firstNameInput"), fName);
		TestUtils.enterText(readElementPropertyKey("lastNameInput"), lName);
		TestUtils.enterText(readElementPropertyKey("emailInput"), email);
		TestUtils.enterText(readElementPropertyKey("telephoneInput"), telephone);
		TestUtils.enterText(readElementPropertyKey("passwordInput"), pwd);
		TestUtils.enterText(readElementPropertyKey("confirmPasswordInput"), pwdConfirm);
		TestUtils.clickByVisibleText(newsSubscription);
		TestUtils.clickCheckbox("agree");
//		TestUtils.clickByValue(readElementPropertyKey("continueBtn"));
		Assert.assertTrue(TestUtils.isTextVisible(readElementPropertyKey("accountCreatedTxt")));
	}
}
