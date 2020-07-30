package com.zigwheels.pages;

import org.openqa.selenium.By;

public class ZigwheelsUsedCars {
	
	/******************Locators involved in Regression Test- Cars******************/
	
	public static final By usedCars= By.xpath("//*[@id=\"headerNewNavWrap\"]/div[2]/ul/li[5]/a");
	
	public static final By usedCarsinChennai= By.xpath("//*[@id=\"headerNewNavWrap\"]/div[2]/ul/li[5]/ul/li/div/ul/li[9]/a");
	
	public static final By sortUsedCars= By.xpath("//*[@id=\"websortbyusedcar\"]");
	
	public static final By carName= By.xpath("//div[@class='pl-30 zw-sr-paddingLeft']/a");
	
	public static final By carPrice= By.xpath("//span[@class='zw-cmn-price n pull-left mt-3']");
	
}
