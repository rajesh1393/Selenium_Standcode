package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.utility.RepositoryParser;
import com.randtronics.dpm.filemanager.config.Constants;

public class Login_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;
	
	public static WebElement txtbx_UserName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("UserName"));
		return element;
	}
	
	public static WebElement txtbx_Password(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Password"));
		return element;
	}
	
	public static WebElement btn_LogIn(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("LoginButton"));
		return element;
	}
	
	public static WebElement Alt_LogIn(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AlertMsg"));
		return element;
	}
}

