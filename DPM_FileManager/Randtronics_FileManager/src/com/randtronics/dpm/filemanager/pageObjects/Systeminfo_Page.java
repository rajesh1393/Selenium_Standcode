package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Systeminfo_Page {

	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;

	public static WebElement lnk_SystemInformation(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("SystemInformation_lnk"));
		return element;
	}

	public static WebElement lnk_Backup_Tab(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Backup_Tab"));
		return element;
	}

	public static WebElement txtbx_Protect_pwd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Protect_pwd"));
		return element;
	}

	public static WebElement txtbx_Confirm_pwd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Confirm_pwd"));
		return element;
	}

	public static WebElement btn_BackupRestore(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("BackupRestore_btn"));
		return element;
	}

	public static WebElement btn_BackupRestoreClose(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Close_btn"));
		return element;
	}

	public static WebElement lnk_Restore(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Restore_Tab"));
		return element;
	}

	public static WebElement txtbx_Restore_Protectpwd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Restore_Protectpwd"));
		return element;
	}

	public static WebElement lnk_Restore_Browse(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Restore_Browse"));
		return element;
	}

	public static WebElement Restore_dialog(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Restore_dialog"));
		return element;
	}
}
