package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;


public class Profile_Page
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;
	
	public static WebElement txtbx_ProfileUserName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ProfileName"));
		return element;
	}
	
	public static WebElement txtbx_ProfileRoles(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ProfileRoles"));
		return element;
	}
	
	public static WebElement txtbx_ProfileGroups(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ProfileGroups"));
		return element;
	}
	
	public static WebElement txtbx_ProfileEmail(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ProfileEmail"));
		return element;
	}
	
	public static WebElement txtbx_OldPassword(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("OldPassword"));
		return element;
	}
	
	public static WebElement txtbx_NewPassword(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("NewPassword"));
		return element;
	}
	
	public static WebElement txtbx_ConfirmPassword(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ConfirmPassword"));
		return element;
	}
	
	public static WebElement btn_SaveProfile(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ProfileSaveBtn"));
		return element;
	}
	
	public static WebElement btn_CancelProfile(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ProfileCancelBtn"));
		return element;
	}
	
	public static WebElement alt_AlertMsg(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ProfileAlertMsg"));
		return element;
	}
}
