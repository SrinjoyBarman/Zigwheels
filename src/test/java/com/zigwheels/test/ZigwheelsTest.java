package com.zigwheels.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.zigwheels.base.ZigwheelsBase;
import com.zigwheels.pages.ZigwheelsLogin;
import com.zigwheels.pages.ZigwheelsPageObject;
import com.zigwheels.pages.ZigwheelsUsedCars;

public class ZigwheelsTest extends ZigwheelsBase {
	
	public static String browser;
	// constructor
	public ZigwheelsTest() {
		super(System.getProperty("user.dir") + "\\zigwheels.xlsx");
	}

	/********************
	 * All variables passed in the name of ZigwheelsPageObject."variable" are being
	 * referred from the POM class
	 *********************/

	// parameters accepted from respective xml file.
	// setting up driver and property file.
	@Parameters({ "browsername", "portId" })
	@BeforeClass(alwaysRun = true)
	public void setUp(String browsername, String portId) throws MalformedURLException {
		invokeBrowser(browsername, portId);
		browser= browsername;
	}

	// test to get respective URL
	@Test(priority = 0, groups = { "Smoke", "Regression" })
	public void getWebsite() {
		getUrl("websiteUrl");
	}

	@Test(priority = 1, groups = { "Smoke" })
	public void uiHandles() throws InterruptedException {
		// hovering and clicking on new bikes
		
		if(browser.equalsIgnoreCase("mozilla"))
			logger = report.createTest("Smoke Test[Bikes] - Mozilla");
		else if(browser.equalsIgnoreCase("chrome"))
			logger = report.createTest("Smoke Test[Bikes] - Google Chrome");
		else if(browser.equalsIgnoreCase("Edge"))
			logger = report.createTest("Smoke Test[Bikes] - Microsoft Edge");
		
		logger1= logger.createNode("Bike Test Details");
		
		
		logger1.log(Status.INFO, "Initializing Browser");

		logger1.log(Status.INFO, "Navigate to Zigwheels application");
		
		moveToElement(ZigwheelsPageObject.newBikes);
		
		Thread.sleep(2000);
		click(ZigwheelsPageObject.upcomingBikes);
		
		logger1.log(Status.INFO, "Hover on [new bikes] and navigate to [upcoming bikes]");
		
		// selecting the manufacturer select from the visible text obtained from
		// .properties file
		selectManufacturer(ZigwheelsPageObject.selectManufacturer, "manufacturer");
		
		logger1.log(Status.INFO, "Set the manufaturer to [Honda]");
	}

	// accepting parameters of excel sheet from respective xml file.
	@Parameters("bikeExcelSheet")
	@Test(priority = 3, groups = { "Smoke" })
	public void writeExcel(String sheetname) {

		// obtaining data from website of prices less then 4 lakh through method in base
		// class and passing into a variable
		String[][] excelData = getPrices(ZigwheelsPageObject.bikeprices, ZigwheelsPageObject.bikenames,
				ZigwheelsPageObject.bikeLaunch);
		// adding columns by calling the method in excelutils
		addColumn(sheetname, "Bike Name");
		addColumn(sheetname, "Bike Price");
		addColumn(sheetname, "Bike Exp. Launch Month in India");

		// setting data into excel sheet by calling the method in excelutils and
		// asserting the boolean variable as to the details correctly written or not.
	    boolean b = setCellData(sheetname, excelData);
		
		logger1.log(Status.PASS, "Write upcoming [Honda] bikes of price less than [4 Lakh] in the respective excel sheets and assert when correctly written");
		
		Assert.assertTrue(b, "Upcoming bikes written to Excel data incorrect");
		
	}

	@Test(priority = 2, groups = "Smoke")
	public void takeScreenshot() {
		
		logger1.log(Status.INFO, "Take screenshot of the upcoming bikes after clicking [read more]");
		
		ExplicitWait(ZigwheelsPageObject.readMore);
		click(ZigwheelsPageObject.readMore);
		// scroll to the table provided after clicking read more and take the
		// screenshot.
		scrollPage(ZigwheelsPageObject.scroll);
		try {
			Thread.sleep(3000);
			takeScreenshotWhenPassed("bikes");
			

			logger1.pass("Upcoming bikes of [Honda] in India",
					MediaEntityBuilder
							.createScreenCaptureFromPath(
									System.getProperty("user.dir") + "\\screenshots\\" + prop.getProperty("bikes") + "")
							.build());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 4, groups = "Regression")
	public void usedCars() {
		// hover on used cars and click on used cars in chennai
		
		if(browser.equalsIgnoreCase("mozilla"))
			logger = report.createTest("Regression Test[Used Cars][Login Bug] - Mozilla");
		else if(browser.equalsIgnoreCase("chrome"))
			logger = report.createTest("Regression Test[Used Cars][Login Bug] - Google Chrome");
		else if(browser.equalsIgnoreCase("Edge"))
			logger = report.createTest("Regression Test[Used Cars][Login Bug] - Microsoft Edge");
		
		logger1= logger.createNode("Used Cars Test");

		logger1.log(Status.INFO, "Initializing Browser");

		logger1.log(Status.INFO, "Navigate to Zigwheels application");
		
		moveToElement(ZigwheelsUsedCars.usedCars);
		clickToOpenInNewTab(ZigwheelsUsedCars.usedCarsinChennai);
		
		logger1.log(Status.INFO, "Hover on [Used Cars] and navigate to [Used Cars in Chennai] in new tab");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// navigate to child window
		WindowHandle(1);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// click on select sort by and select the visible text passed from the
		// .properties file
		ExplicitWait(ZigwheelsUsedCars.sortUsedCars);
		selectManufacturer(ZigwheelsUsedCars.sortUsedCars, "sortby");
		
		logger1.log(Status.INFO,"Sort by [Price : High to Low]");
	}

	@Test(priority = 5, groups = "Regression")
	public void assertCars() {
		// getting all list of items in used cars in chennai sorted by Price:High to Low
		List<WebElement> list = getTopUsedCars(ZigwheelsUsedCars.carName);
		// asserting whether there are less than 1000 used cars in the selected category
		Assert.assertTrue(list.size() < 1000, "There are more than 1000 products");

		// asserting whether we are in the page used cars
		Assert.assertTrue(driver.getTitle().split("-")[0].trim().contains("Used Cars in Chennai"),
				"Title does not match");
		
		logger1.log(Status.PASS, "Assert whether title matches [Used Cars in Chennai] as well as there are less than 1000 cars");
	}

	@Test(priority = 6, groups = "Regression")
	public void switchLogin() {
		
		logger1= logger.createNode("Invalid Login Test");
		// get back to home page window
		WindowHandle(0);
		// click on login icon
		click(ZigwheelsLogin.loginbutton);
		
		logger1.log(Status.INFO, "Navigate back to parent tab and click on [login icon]");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// click on continue through facebook
		click(ZigwheelsLogin.facebookLogin);
		
		logger1.log(Status.INFO, "Click on [Continue through Facebook]");
		
		// navigate to new login window
		windowHandlesSet();
		driver.manage().window().maximize();

	}
	
	@Parameters("readData")
	@Test(priority = 7, groups = "Regression")
	public void LoginPage(String sheetname) {
		// send username and password passed through values from .properties file and
		// click on log in button
		
		sendText(ZigwheelsLogin.inputEmail, getCellData(sheetname,2,"USERNAME"));
		sendText(ZigwheelsLogin.inputPwd, getCellData(sheetname,2,"PASSWORD"));
		
		logger1.log(Status.INFO, "In the new tab, pass invalid [email-id] and [password]");
		
		click(ZigwheelsLogin.login);
		
		logger1.log(Status.INFO, "click on [log in] button");
	}

	@Test(priority = 8, groups = "Regression")
	public void assertBug() {
		// Asserting the displayed text is "Sorry, something went wrong."
		
		logger1.warning("Test Case may fail if given message does not match the displayed one.");
		
		Assert.assertEquals("Sorry, something went wrong.", driver.findElement(ZigwheelsLogin.bugmsg).getText());
		
		logger1.log(Status.FAIL, "Should display [Incorrect email address or phone number] but showing [Sorry, something went wrong.]");

		// take screenshot
		try {
			Thread.sleep(3000);
			takeScreenshotWhenPassed("login");
			
			logger1.error("Wrong message displayed on log in",
					MediaEntityBuilder
							.createScreenCaptureFromPath(
									System.getProperty("user.dir") + "\\screenshots\\" + prop.getProperty("login") + "")
							.build());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@AfterMethod(alwaysRun= true)
	public void getResult(ITestResult result) throws IOException {
		if(result.getStatus()== ITestResult.FAILURE) {
			logger1.log(Status.FAIL, "Failed Test is"+result.getName());
			logger1.log(Status.FAIL, result.getThrowable());
			logger1.fail("Failed",
					MediaEntityBuilder
							.createScreenCaptureFromPath(takeScreenshotWhenFailed()).build());
		}
		else if(result.getStatus()== ITestResult.SKIP) {
			logger1.log(Status.SKIP, "Skipped Test is"+result.getName());
			logger1.log(Status.SKIP, result.getThrowable());
		}
	}
	// quit the browser
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		report.flush();
		close();
	}
}
