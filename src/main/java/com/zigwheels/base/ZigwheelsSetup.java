package com.zigwheels.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ZigwheelsSetup {
	public static WebDriver driver;
	public static Properties prop=null;
	Platform Win10;
	static String nodeUrl;
	
	/***********setting up the driver through selenium - local webdriver***********/
	
	public static WebDriver getLocalWebdriver(String browsername) {
		if(browsername.equalsIgnoreCase("mozilla")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//drivers//geckodriver.exe");
			driver= new FirefoxDriver();
		}
		else if(browsername.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver.exe");
			driver= new ChromeDriver();
		}
		else if(browsername.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"//drivers//msedgedriver.exe");
			driver= new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		return driver;
	}
	
	
	/***********take a step forward setting up the driver through selenium grid- remote webdriver***********/
	
	public static WebDriver getWebDriver(String browserName,String portId) throws MalformedURLException {
		
		
		if(browserName.equalsIgnoreCase("mozilla")) {
			nodeUrl= portId;
			new DesiredCapabilities();
			DesiredCapabilities cap= DesiredCapabilities.firefox();
			cap.setBrowserName("firefox");
			cap.setPlatform(Platform.WINDOWS);
			driver= new RemoteWebDriver(new URL(nodeUrl),cap);
		}
		
		else if(browserName.equalsIgnoreCase("Edge")) {
			nodeUrl= portId;
			new DesiredCapabilities();
			DesiredCapabilities cap= DesiredCapabilities.edge();
			EdgeOptions options = new EdgeOptions();
			cap.setBrowserName("Edge");
			cap.setVersion("84.0.522.40");
			cap.setPlatform(Platform.WINDOWS);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			driver= new RemoteWebDriver(new URL(nodeUrl),cap);	
		}
		
//		else if(browserName.equalsIgnoreCase("internetexplorer")) {
//			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\IEDriverServer.exe");
//			driver= new InternetExplorerDriver();
//		}
		
		else if(browserName.equalsIgnoreCase("chrome")) {
			nodeUrl= portId;
			new DesiredCapabilities();
			DesiredCapabilities cap= DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.WINDOWS);
			driver= new RemoteWebDriver(new URL(nodeUrl),cap);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		return driver;
	}
	
	/******************setting the properties file****************/
	public static Properties propertySet() {
		if(prop==null) {
			prop= new Properties();
			try {
				FileInputStream fis= new FileInputStream(System.getProperty("user.dir")+"//zigwheels.properties");
				try {
					prop.load(fis);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
}
