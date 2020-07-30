package com.zigwheels.test;

import java.io.IOException;
import java.net.MalformedURLException;

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

public class ZigWheelsValidTest extends ZigwheelsBase {
	public static String browser;
	// constructor
	public ZigWheelsValidTest() {
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
	@Test(priority = 0, groups = { "Regression" })
	public void getWebsite() {
		getUrl("websiteUrl");
	}

	@Test(priority = 1, groups = "Regression")
	public void Login() {
		// click on login icon
		
		if(browser.equalsIgnoreCase("mozilla"))
			logger = report.createTest("Regression Test[No Login Bug] - Mozilla");
		else if(browser.equalsIgnoreCase("chrome"))
			logger = report.createTest("Regression Test[No Login Bug] - Google Chrome");
		else if(browser.equalsIgnoreCase("Edge"))
			logger = report.createTest("Regression Test[No Login Bug] - Microsoft Edge");
		
		logger1= logger.createNode("Valid Login Test");
		
		logger1.log(Status.INFO, "Initializing Browser");

		logger1.log(Status.INFO, "Navigate to Zigwheels application");
		
		WindowHandle(0);
		click(ZigwheelsLogin.loginbutton);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		logger1.log(Status.INFO, "Click on Login Icon");
		
		// click on continue through facebook and navigate to child window
		click(ZigwheelsLogin.facebookLogin);
		
		logger1.log(Status.INFO, "Click on [Continue with Facebook]");
		
		WindowHandle(1);
		driver.manage().window().maximize();

	}
	
	@Parameters("readData")
	@Test(priority = 2, groups = "Regression")
	public void LoginPage(String sheetname) {
		// send username and password passed through values from .properties file and
		// click on log in button
		
		sendText(ZigwheelsLogin.inputEmail, getCellData(sheetname,3,"USERNAME"));
		sendText(ZigwheelsLogin.inputPwd, getCellData(sheetname,3,"PASSWORD"));
		
		logger1.log(Status.INFO, "In the new tab, pass valid [email-id] and [password]");
		
		click(ZigwheelsLogin.login);
		
		logger1.log(Status.INFO, "click on [log in] button");
	}

	@Test(priority = 3, groups = "Regression")
	public void assertReEnterPass() {

		// Asserting the displayed text is "Please re-enter your password"
		
		logger1.warning("Test Case may fail if given message does not match the displayed one.");
		
		Assert.assertEquals("Please re-enter your password",
				driver.findElement(ZigwheelsLogin.passwordIncorrectmsg).getText());
		
		logger1.log(Status.PASS, "Should display [Re-Enter Password] and showing [Re-Enter Password]");

		
		// click on login button
		try {
			Thread.sleep(4000);
			takeScreenshotWhenPassed("validlogin");
			
			logger1.pass("Right message displayed on log in",
					MediaEntityBuilder
							.createScreenCaptureFromPath(
									System.getProperty("user.dir") + "\\screenshots\\" + prop.getProperty("validlogin") + "")
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
