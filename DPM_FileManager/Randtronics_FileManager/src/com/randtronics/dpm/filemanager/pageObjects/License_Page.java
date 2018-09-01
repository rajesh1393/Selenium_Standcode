package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;;

public class License_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element = null;
	
	public static WebElement lnk_License(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("requestLicense"));
    	return element;
    }
	
	public static WebElement btn_requestLicense(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("Licenselink"));
    	return element;
    }
	
	public static WebElement btn_importLicense(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("importLicense"));
    	return element;
    }
	
	public static WebElement txt_customerName(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("customername"));
    	return element;
    }
	
	public static WebElement txt_LicenseEmail(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("Licenseemail"));
    	return element;
    }
	
	public static WebElement arw_LicenseName(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("Licensenamearw"));
    	return element;
    }
	
	public static WebElement txt_requestCode(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("requestcode"));
    	return element;
    }
	
	public static WebElement btn_generateCode(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("generatecode"));
    	return element;
    }
	
	public static WebElement btn_saveasFile(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("saveasfile"));
    	return element;
    }
	
	public static WebElement btn_LicenseClose(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("Licenseclosebtn"));
    	return element;
    }
	
	public static WebElement txt_uploadLicenseFile(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("uploadLicensefile"));
    	return element;
    }
	
	public static WebElement btn_uploadLicense(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("uploadLicensebtn"));
    	return element;
    }
	
	public static WebElement btn_reLogin(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("reloginbtn"));
    	return element;
    }
	
	public static WebElement alt_License(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("License_altmsg"));
    	return element;
    }
	
	public static WebElement btn_closeUploadLicense(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("closeuploadLicense"));
    	return element;
    }
	
	public static WebElement btn_licenseUploadBrowse(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("licenseBrowse"));
    	return element;
    }
}
