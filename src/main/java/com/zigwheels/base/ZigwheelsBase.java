package com.zigwheels.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.zigwheels.utils.ExcelUtilsZigwheels;
import com.zigwheels.utils.ExtentReportManager;




public class ZigwheelsBase extends ExcelUtilsZigwheels {
	
	public ExtentReports report= ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	public ExtentTest logger1;
	
//	public static Logger log= LogManager.getLogger(ZigwheelsBase.class.getName());
	//constructor
	public ZigwheelsBase(String path) {
		super(path);
	}

	public WebDriver driver;
	public Properties prop = null;

	//invoke browser and setup property file by calling the setup class
	public void invokeBrowser(String browserName,String port) throws MalformedURLException {

		prop = ZigwheelsSetup.propertySet();
		driver = ZigwheelsSetup.getWebDriver(browserName,port);
	}
	
	//get Url
	public void getUrl(String urlKey) {
		driver.get(prop.getProperty(urlKey));
	}
	
	//function to hover
	public void moveToElement(By xpathKey) {
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(xpathKey)).build().perform();
	}
	
	//function to click
	public void click(By xpathKey) {
		driver.findElement(xpathKey).click();
	}
	
	//function to select by visible text
	public void selectManufacturer(By xpathKey, String text) {
		Select s = new Select(driver.findElement(xpathKey));
		s.selectByVisibleText(prop.getProperty(text));
	}
	
	//get data of name,price and expected launch month in india of bikes less than 4 lakh
	public String[][] getPrices(By priceKey, By nameKey, By expectedLaunch) {
		try {
			List<WebElement> prices = driver.findElements(priceKey);
			List<WebElement> name = driver.findElements(nameKey);
			List<WebElement> launchdate = driver.findElements(expectedLaunch);
			String[][] write = new String[3][3];

			double numberPrice = 0.0;
			int j = 0;
			for (int i = 0; i < prices.size(); i++) {
				String priceOnly[] = prices.get(i).getText().split("Rs. ");

				if (priceOnly[1].contains("Lakh")) {
					numberPrice = Double.parseDouble(priceOnly[1].split(" ")[0]);
					numberPrice = Math.floor(numberPrice*100000.0);

				} else {
					String thousand[] = priceOnly[1].split(",");
					numberPrice = Double.parseDouble(thousand[0] + thousand[1]);

				}

				if (numberPrice < 400000.0) {

					String[] date = launchdate.get(i).getText().split(":");
					
					System.out.println(name.get(i).getText());
					System.out.println(prices.get(i).getText());
					System.out.println(date[1].trim());
					System.out.println();
					write[j][0] = name.get(i).getText();
					write[j][1] = prices.get(i).getText();
					write[j][2] = date[1].trim();
					j = j + 1;
				}
			}

			return write;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	//function to take screenshot
	
	public void takeScreenshotWhenPassed(String fileKey) throws IOException {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir") + "\\screenshots\\"+prop.getProperty(fileKey)+"");
		FileUtils.copyFile(source, destination, true);
		
	}
	
	//add a screenshot when failed
	
	public String takeScreenshotWhenFailed() throws IOException {
		
		Date date= new Date();
		String currdate= new SimpleDateFormat("dd-MM-yy_hh-MM-ss").format(date);
		String path= System.getProperty("user.dir")+"//screenshots//";
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		File destination = new File(path+currdate+".png");
		FileUtils.copyFile(source, destination, true);
		
		return path+currdate+".png";
	}
	//function to explicit wait
	public void ExplicitWait(By xpathKey) {
		WebDriverWait wait = new WebDriverWait(driver, 180);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(xpathKey)));
	}
	
	//function to scroll page
	public void scrollPage(By xpathKey) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(xpathKey));
	}
	
	//function to get top usedCars
	public List<WebElement> getTopUsedCars(By xpathName) {
		List<WebElement> carNames = driver.findElements(xpathName);
		return carNames;
	}
	
	//click to open in new tab
	public void clickToOpenInNewTab(By xpathKey) {

		Actions a = new Actions(driver);
		a.keyDown(Keys.CONTROL).click(driver.findElement(xpathKey)).keyUp(Keys.CONTROL).build().perform();
		
	}
	
	//handle traversal through windows by index
	public void WindowHandle(int handle) {
		List<String> windows = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(handle));
	}
	
	//handle traversal of windows through iterator
	public void windowHandlesSet() {
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it= handles.iterator();
		while(it.hasNext()) { 
			it.next();
			it.next();
			String childWindow3=it.next();
			driver.switchTo().window(childWindow3);
		}
	}
	
	//sendKeys
	public void sendText(By xpathKey, String text) {
		driver.findElement(xpathKey).sendKeys(text);
	}
	
	//function to quit
	public void close() {
		driver.quit();
	}
}
