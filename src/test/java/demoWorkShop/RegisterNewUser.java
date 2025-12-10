package demoWorkShop;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import utilPack.DemoWebShopBaseClass;

public class RegisterNewUser extends DemoWebShopBaseClass {

	String gender = "Male";
	String Fname = "Sami";
	String Lname = "Karry";
	String email = "SamiK2@gmail.com";
	String pass = "Sami123";
	String confirmPass = "Sami123";
	String expectedConfirmMsg = "Your registration completed";
	SoftAssert sf = new SoftAssert();

	@Test
	public void RegisterUser() {
		clickOnElement("registerlink");
		goToSleep(3);
		selectGender(gender);
		goToSleep(2);
		sendKeys("firstNameTB", Fname);
		goToSleep(2);
		sendKeys("lastNameTB", Lname);
		goToSleep(2);
		sendKeys("emailTB", email);
		goToSleep(2);
		sendKeys("passwordTB", pass);
		goToSleep(2);
		sendKeys("confirmPasswordTB", confirmPass);
		goToSleep(2);
		clickOnElement("registerBtn");
		goToSleep(2);
		String actualconfirmMsg = getElementText("confirmMsg");
		sf.assertTrue(actualconfirmMsg.contains(expectedConfirmMsg));
		sf.assertAll();
		goToSleep(2);
		clickOnElement("continueBtn");
		goToSleep(2);
		logOutDemoWebShop();
	}

	public void selectGender(String gender) {
		if (gender == "Male") {
			clickOnElement("maleRadioBtn");
		} else {
			clickOnElement("femaleRadioBtn");
		}
	}
}
