package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.utility.RepositoryParser;
import com.randtronics.dpm.filemanager.config.Constants;

public class SystemRole_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;
	
	 public static WebElement lnk_SytemManagement_SystemRole(WebDriver driver) throws Exception
	 {
	    parser.RepositoryParser(Constants.path_ObjectRepo);
	    element = driver.findElement(parser.getLocator("SystemRoleTab"));
	    return element;
	 }
	 
	 public static WebElement btn_AddSystemRole(WebDriver driver) throws Exception
	 {
	    parser.RepositoryParser(Constants.path_ObjectRepo);
	    element = driver.findElement(parser.getLocator("AddSystemRoleBtn"));
	    return element;
	 }

	public static WebElement txtbx_RoleName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("RoleName"));
		return element;
	}
	
	public static WebElement txtbx_RoleDescription(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("RoleDescription"));
		return element;
	}
	
	public static WebElement txtbx_RoleRights(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("RoleRightsLeft"));
		return element;
	}
	
	public static WebElement txtbx_RoleRightsRight(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("RoleRightsRight"));
		return element;
	}
	
	 public static WebElement Arw_SytemSelectRole(WebDriver driver) throws Exception
	 {
	    parser.RepositoryParser(Constants.path_ObjectRepo);
	    element = driver.findElement(parser.getLocator("RoleSelectArrow"));
	    return element;
	 }
	    
	 public static WebElement Arw_SytemUnselectRole(WebDriver driver) throws Exception
	 {
	    parser.RepositoryParser(Constants.path_ObjectRepo);
	    element = driver.findElement(parser.getLocator("RoleUnselectArrow"));
	    return element;
	 }
	
	public static WebElement btn_SaveRole(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("RoleSaveBtn"));
		return element;
	}
	
	public static WebElement btn_CancelRole(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("RoleCancelBtn"));
		return element;
	}
	
	public static WebElement Alt_SystemRoleSuccess(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("RoleAlertSuccess"));
    	return element;
    }
	
	public static WebElement Alt_SystemRoleFail(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("RoleAlertFail"));
    	return element;
    }
	
	public static WebElement btn_AddUser(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("RoleUserAddBtn"));
		return element;
	}
	
	public static WebElement icon_Delete(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DeleteIcon"));
		return element;
	}
}
