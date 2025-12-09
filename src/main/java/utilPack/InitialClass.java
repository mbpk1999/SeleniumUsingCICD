package utilPack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InitialClass {

	public WebDriver driver;
	public Properties locatorString;
	public WebDriverWait wait;
	public static WebElement ele = null;
	public static JavascriptExecutor js;

	public void launchBrowser(String browser) {
		if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		if (browser.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		js = (JavascriptExecutor) driver;
	}

	public void loadPropertyFile() {
		locatorString = new Properties();
		try {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/resources/locators.properties");
			locatorString.load(fis);
		} catch (IOException e) {
			System.out.println("Error in InitalClass/loadPropertyFile method : " + e);
		}
	}

	public WebElement createLocator(String label) {
		String labelText = locatorString.getProperty(label);
		String[] labelTextArray = labelText.split("~");
		String method = labelTextArray[0];
		String path = labelTextArray[1];
		switch (method) {
		case "id": {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(path)));
			wait.until(ExpectedConditions.elementToBeClickable(By.id(path)));
			ele = driver.findElement(By.id(path));
			break;
		}
		case "name": {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(path)));
			wait.until(ExpectedConditions.elementToBeClickable(By.name(path)));
			ele = driver.findElement(By.name(path));
			break;
		}
		case "linkText": {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(path)));
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText(path)));
			ele = driver.findElement(By.linkText(path));
			break;
		}
		case "partialLinkText": {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(path)));
			wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(path)));
			ele = driver.findElement(By.partialLinkText(path));
			break;
		}
		case "xpath": {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
			ele = driver.findElement(By.xpath(path));
			break;
		}
		case "cssSelector": {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(path)));
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(path)));
			ele = driver.findElement(By.cssSelector(path));
			break;
		}
		default: {
			System.out.println("Invalid locator syntax...Please Check the Syntax!!!");
			break;
		}
		}
		return ele;
	}

	public void clickOnElement(String locator) {
		try {
			ele = createLocator(locator);
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
			ele.click();
		} catch (Exception e) {
			System.out.println("Error in InitialClass/clickOnElement method : " + e);
		}
	}
	
	public void sendKeys(String locator, String text)
	{
		try {
			ele = createLocator(locator);
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
			ele.sendKeys(text);
		} catch (Exception e) {
			System.out.println("Error in InitialClass/sendKeys method : " + e);
		}
	}
	
	public String getElementText(String locator)
	{
		String text = null;
		try {
			ele = createLocator(locator);
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
			text = ele.getText();
		} catch (Exception e) {
			System.out.println("Error in InitialClass/getElementText method : " + e);
		}
		return text;
	}
	
	public void goToSleep(int sec)
	{
		try {
			Thread.sleep(Duration.ofSeconds(sec));
		} catch (InterruptedException e) {
			System.out.println("Error in InitialClass/goToSleep method : "+e);
		}
	}
	
	public String[][] readDataFromExcel(String sheetname)
	{
		String [][]dataSet = null;
		try {
			File file = new File(System.getProperty("user.dir")+"/src/test/java/sampleCheck/testData.xlsx");
			FileInputStream fis = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sheetName = wb.getSheet(sheetname);
			int totalRows = sheetName.getLastRowNum();
			//System.out.println("Number of rows: "+totalRows);
			Row headerRow = sheetName.getRow(0);
			int totalColumns = headerRow.getLastCellNum();
			//System.out.println("Number of columns: "+totalColumns);
			dataSet = new String[totalRows][totalColumns];
			DataFormatter df = new DataFormatter();
			for(int i=1; i<=totalRows; i++)
			{
				for(int j=0; j<totalColumns; j++)
				{
					dataSet[i-1][j] = df.formatCellValue(sheetName.getRow(i).getCell(j));
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error in InitialClass/readDataFromExcel method : "+e);
		}
		return dataSet;
	}

}
