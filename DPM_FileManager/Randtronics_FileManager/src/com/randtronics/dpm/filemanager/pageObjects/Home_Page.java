package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Home_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element = null;

    public static WebElement lnk_LogOut(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("Logout"));
    	return element;
    }
    
    public static WebElement lnk_SytemManagement(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SystemManagementModule"));
    	return element;
    }
    
    public static WebElement lnk_Profile(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("ProfileLink"));
    	return element;
    }
    public static WebElement lnk_Policy(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("PolicyLink"));
    	return element;
    }
    public static WebElement lnk_Audit(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("AuditLink"));
    	return element;
    }
    
    public static WebElement lnk_Dashboard(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DashboardPageTitle"));
		return element;
	}

}

