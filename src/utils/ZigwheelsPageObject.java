package com.zigwheels.utils;

import org.openqa.selenium.By;

public class ZigwheelsPageObject {
	
	/******************Locators involved in Smoke Test - Bikes*********************/
	
	public static final By newBikes= By.xpath("//*[@id=\"headerNewNavWrap\"]/div[2]/ul/li[3]/a");
	
	public static final By upcomingBikes= By.xpath("//*[@id=\"headerNewNavWrap\"]/div[2]/ul/li[3]/ul/li/div[1]/ul/li[3]/a");
	
	public static final By selectManufacturer= By.xpath("//select[@id='makeId']");
	
	public static final By bikeprices= By.xpath("//div[@class='clr-bl p-5']");
	
	public static final By bikenames= By.xpath("//strong[@class='lnk-hvr block of-hid h-height']");
	
	public static final By bikeLaunch= By.xpath("//div[@class='clr-try fnt-15']");
	
	public static final By readMore= By.xpath("//span[@class='lnk-c fnt-14 rmec']");
	
	public static final By scroll= By.xpath("/html/body/main/div/div/div[1]/div[1]/div[1]/div/div/div[1]/h1");
	
	/******************Locators involved in Regression Test- Cars******************/
	
	public static final By usedCars= By.xpath("//*[@id=\"headerNewNavWrap\"]/div[2]/ul/li[5]/a");
	
	public static final By usedCarsinChennai= By.xpath("//*[@id=\"headerNewNavWrap\"]/div[2]/ul/li[5]/ul/li/div/ul/li[9]/a");
	
	public static final By sortUsedCars= By.xpath("//*[@id=\"websortbyusedcar\"]");
	
	public static final By carName= By.xpath("//div[@class='pl-30 zw-sr-paddingLeft']/a");
	
	public static final By carPrice= By.xpath("//span[@class='zw-cmn-price n pull-left mt-3']");
	
	/******************Locators involved in Regression Test- Login*******************/
	
	public static final By loginbutton= By.xpath("//*[@id=\"des_lIcon\"]");
	
	public static final By facebookLogin = By.xpath("//*[@id='myModal3-modal-content']/div[1]/div/div[2]/div[3]/span[2]");
	
	public static final By inputEmail = By.xpath("//*[@id='email']");
	
	public static final By inputPwd = By.xpath("//*[@id='pass']");
	
	public static final By login = By.xpath("//*[@value='Log In']");
	
	public static final By passwordIncorrectmsg = By.xpath("//*[@id=\"error_box\"]/div[1]");
	
	public static final By bugmsg = By.xpath("//h1");

	
	
	
}
