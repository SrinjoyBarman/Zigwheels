package com.zigwheels.pages;

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
	
}
