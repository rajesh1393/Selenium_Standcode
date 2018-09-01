package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.utility.RepositoryParser;
import com.randtronics.dpm.filemanager.config.Constants;

public class SystemUser_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element = null;
	
	 public static WebElement lnk_SytemManagement_SystemUser(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("SystemUserTab"));
	    	return element;
	 }
	 
	 public static WebElement btn_AddSystemUser(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("AddSystemUserBtn"));
	    	return element;
	 }

    public static WebElement txtbx_SytemUserName(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserName"));
    	return element;
    }
    
    public static WebElement txtbx_SytemUserDescription(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserDescription"));
    	return element;
    }
    
    public static WebElement txtbx_SytemUserTelephoneNo(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserTelephoneNo"));
    	return element;
    }
    
    public static WebElement txtbx_SytemUserEmail(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserEmail"));
    	return element;
    }
    
    public static WebElement txtbx_SytemUserPassword(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserPassword"));
    	return element;
    }
    
    public static WebElement txtbx_SytemUserConfirmPassword(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserConfirmPassword"));
    	return element;
    }
    
    public static WebElement txtbx_SytemUserRole(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element=driver.findElement(parser.getLocator("SystemUserRole"));
    	return element;
    }
    
    public static WebElement txtbx_SytemUserSelectedRole(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserSelectedRole"));
    	return element;
    }
    
    public static WebElement Arw_SytemUserSelectRole(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserSelectArrow"));
    	return element;
    }
    
    public static WebElement Arw_SytemUserUnselectRole(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserUnselectArrow"));
    	return element;
    }
    
    public static WebElement btn_SystemUserSave(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserSaveBtn"));
    	return element;
    }
    
    public static WebElement btn_SystemUserCancel(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserCancelBtn"));
    	return element;
    }
    
    public static WebElement Alt_SystemUser(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemUserAltMsg"));
    	return element;
    }
}

