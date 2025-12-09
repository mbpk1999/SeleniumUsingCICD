package utilPack;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class DemoWebShopBaseClass extends InitialClass{
	
	@BeforeTest
	public void startBrowser()
	{
		System.out.println("Starting Browser");
		launchBrowser("Chrome");
		loadPropertyFile();
		driver.get("http://demowebshop.tricentis.com/");
	}
	
	public void logOutDemoWebShop()
	{
		clickOnElement("logOutLink");
		goToSleep(5);
	}
	
	@AfterTest
	public void killBrowser()
	{
		  System.out.println("Closing Browser"); driver.close();	 
	}
	

	

}
