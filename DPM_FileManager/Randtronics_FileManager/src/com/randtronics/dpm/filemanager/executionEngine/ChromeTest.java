package com.randtronics.dpm.filemanager.executionEngine;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;

public class ChromeTest 
{
	protected static WebDriver driver;
	
	
	@BeforeSuite
	public void beforeSuite() throws Exception
	{
		try
		{
			ExcelUtils.setExcelFile(Constants.Path_TestData + Constants.File_TestData); 
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	 @BeforeClass
	 public void beforeClass() throws Exception
	 {
		System.setProperty(Constants.CHROME_DRIVER,Constants.CHROME_DRIVER_EXEPATH);
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Constants.implicitWaitSec, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(Constants.baseUrl);
	 }
	 
	 @AfterClass
	 public void afterClass() throws Exception
	 {
		 driver.close();
	 }
}
