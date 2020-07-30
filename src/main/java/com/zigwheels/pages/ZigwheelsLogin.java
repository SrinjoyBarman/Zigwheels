package com.zigwheels.pages;

import org.openqa.selenium.By;

public class ZigwheelsLogin {
	
/******************Locators involved in Regression Test- Login*******************/
	
	public static final By loginbutton= By.xpath("//*[@id=\"des_lIcon\"]");
	
	public static final By facebookLogin = By.xpath("//*[@id='myModal3-modal-content']/div[1]/div/div[2]/div[3]/span[2]");
	
	public static final By inputEmail = By.xpath("//*[@id='email']");
	
	public static final By inputPwd = By.xpath("//*[@id='pass']");
	
	public static final By login = By.xpath("//*[@value='Log In']");
	
	public static final By passwordIncorrectmsg = By.xpath("//*[@id=\"error_box\"]/div[1]");
	
	public static final By bugmsg = By.xpath("//h1");
}
