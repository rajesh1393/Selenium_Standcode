package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Email_Page 
{
		public static RepositoryParser parser=new RepositoryParser();
		private static WebElement element = null;

	    public static WebElement lnk_Email(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailTab"));
	    	return element;
	    }
	    public static WebElement txt_IPaddress(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailServerIP"));
	    	return element;
	    }
	    public static WebElement txt_Port(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailServerPort"));
	    	return element;
	    }
	    public static WebElement txt_sent_from(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailServersentFrom"));
	    	return element;
	    }
	    public static WebElement list_Authentication(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailAuthentication"));
	    	return element;
	    }
	    public static WebElement txt_Email_Username(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailUsername"));
	    	return element;
	    }
	    public static WebElement txt_Email_Password(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailPassword"));
	    	return element;
	    }
	    public static WebElement list_Encryption(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailEncryption"));
	    	return element;
	    }
	    public static WebElement btn_Test(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailTest"));
	    	return element;
	    }
	    public static WebElement lnk_Email_Save(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailSave"));
	    	return element;
	    }
	    public static WebElement btn_Cancel(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailCancel"));
	    	return element;
	    }
	    public static WebElement alt_message(WebDriver driver) throws Exception
	    {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EmailAlertmsg"));
	    	return element;
	    }

}
