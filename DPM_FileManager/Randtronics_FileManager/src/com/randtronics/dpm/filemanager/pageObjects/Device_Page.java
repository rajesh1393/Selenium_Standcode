package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Device_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element = null;

    public static WebElement lnk_Device(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("DeviceTab"));
    	return element;
    }
    
    public static WebElement btn_Move(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("DeviceMove"));
    	return element;
    }
    
    public static WebElement lst_Select(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("DeviceSelect"));
    	return element;
    }
    
    public static WebElement btn_Save(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("DeviceMovesave"));
    	return element;
    }
    
    public static WebElement btn_Cancel(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("DeviceMoveCancel"));
    	return element;
    }
    
    public static WebElement alt_devicemove(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("DeviceAlt"));
    	return element;
    }
    
    public static WebElement lnk_devicegroupRoot(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("Device_GroupRoot"));
    	return element;
    }

}
