package utilPack;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class ParaBankBaseClass extends InitialClass{
	
	@BeforeTest
	public void startBrowser()
	{
		System.out.println("Starting Browser");
		launchBrowser("Chrome");
		driver.get("https://parabank.parasoft.com/parabank/index.htm");
	}
	
	
	@AfterTest
	public void killBrowser()
	{
		System.out.println("Closing Browser");
		driver.close();
	}
	

}
