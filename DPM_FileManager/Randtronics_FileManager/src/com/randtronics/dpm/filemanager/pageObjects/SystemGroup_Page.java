package com.randtronics.dpm.filemanager.pageObjects;


import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;
import com.randtronics.dpm.filemanager.config.Constants;

public class SystemGroup_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;
	private static List<WebElement> ele1;
	
	public static WebElement lnk_SytemManagement_Group(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("GroupTab"));
	    	return element;
	 }
	
	public static WebElement Page_Heading(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("PageTitle"));
	    	return element;
	 }
	public static WebElement Btn_Add(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AddBtn"));
		return element;
	}
	public static WebElement Btn_Move(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("MoveBtn"));
		return element;
	}
	public static WebElement DialogHeading_AddPage(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AddDialogHeading"));
		return element;
	}
	
	public static WebElement DialogHeading_Move(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("MoveDialogHeading"));
		return element;
	}
	public static WebElement NameTxtbx_AddPage(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Group_Name"));
		return element;
	}
	public static WebElement DescTxtbx_AddPage(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Group_Description"));
		return element;
	}
	public static WebElement SaveBtn_AddPage(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("SaveBtn_Add"));
		return element;
	}
	public static WebElement CancelBtn_AddPage(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("CancelBtn_Add"));
		return element;
	}
	
	public static WebElement Alt_GroupSuccess(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Add_AlertMsg"));
		return element;
	}
	
	public static WebElement AltInvalid_Add(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Add_InvalidAlert"));
		return element;
	}
	
	public static WebElement ModifyDialog_Heading(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("SM_Group_Modify_DialogHeading"));
		return element;
	}
	
	public static WebElement DeleteDialog_Heading(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DeleteGroupHeading"));
		return element;
	}
	
	public static WebElement Yeslnk_Delete(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("YesLink_Group"));
		return element;
	}
	
	public static WebElement Nolnk_Delete(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("NoLink_Group"));
		return element;
	}
	
	public static WebElement Txt_FromMove(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Txt_FromMove"));
		return element;
	}
	
	public static WebElement Txt_ToMove(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Txt_ToMove"));
		return element;
	}
	
	public static WebElement drpdwn_Move(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("drpdwn_Move"));
		return element;
	}
	public static WebElement btn_SaveMove(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("btn_SaveMove"));
		return element;
	}
	public static WebElement btn_CancelMove(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("btn_CancelMove"));
		return element;
	}
}
