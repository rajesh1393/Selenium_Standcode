package com.randtronics.dpm.filemanager.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Dashboard_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;
	private static List<WebElement> ele1;
	
	public static WebElement lnk_Dashboard(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DashboardPageTitle"));
		return element;
	}
	
	/*public static WebElement Dashboard_PageTitle(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DashboardPageTitle"));
		return element;
	}*/
	
	
	public static WebElement lnk_DisplayOptions(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DisplayOptLink"));
		return element;
	}
	
	public static WebElement drpdwn_Hours(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DashboardDropDown"));
		return element;
	}
	
	public static List<WebElement> FramesList_Dashboard(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		ele1=(List<WebElement>) driver.findElement(parser.getLocator("DashboardFramesList"));
		return ele1;
	}
	
	public static WebElement Heading_DBDialog(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DashboardConfig"));
		return element;
	}
	
	public static WebElement AllDashboardList_DBDialog(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AllDashboardList"));
		return element;
	}
	
	public static WebElement SelectedDashboardList_DBDialog(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("SelectDashboardList"));
		return element;
	}
	
	public static WebElement RightArrow_DBDialog(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("RightArrow"));
		return element;
	}
	public static WebElement LeftArrow_DBDialog(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("LeftArrow"));
		return element;
	}
	
	public static WebElement SaveBtn_DBDialog(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DisplaySaveBtn"));
		return element;
	}
	public static WebElement CancelBtn_DBDialog(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DisplayCancelBtn"));
		return element;
	}
}
