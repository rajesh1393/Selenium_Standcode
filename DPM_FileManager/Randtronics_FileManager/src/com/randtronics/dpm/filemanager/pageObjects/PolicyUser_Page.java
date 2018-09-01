package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class PolicyUser_Page
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;
	
	public static WebElement lnk_UserTab(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("UserTab"));
	    	return element;
	 }
	public static WebElement btn_AddUser(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("AddUser_Link"));
	    	return element;
	 }
	public static WebElement btn_ImportUser(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("ImportUser_Link"));
	    	return element;
	 }
	public static WebElement drpdwn_Userlist(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("UserList_drpdwn"));
	    	return element;
	 }
	public static WebElement dialog_AddUser(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("DialogHeading_AddUser"));
	    	return element;
	 }
	public static WebElement txt_Username(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("UserName_Policy"));
	    	return element;
	 }
	public static WebElement txt_UserDesc(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("UserDesc_Policy"));
	    	return element;
	 }
	public static WebElement lst_AllRoles(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("AllRolesList_Policy"));
	    	return element;
	 }
	public static WebElement lst_SelectedRoles(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("SelectedRolesList_Policy"));
	    	return element;
	 }
	public static WebElement arw_Select(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("RightArw_User_Policy"));
	    	return element;
	 }
	public static WebElement arw_Deselect(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("LeftArw_User_Policy"));
	    	return element;
	 }
	public static WebElement btn_SaveUser(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Save_User"));
	    	return element;
	 }
	public static WebElement btn_CancelUser(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Cancel_User"));
	    	return element;
	 }
	public static WebElement alt_User(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Alt_User_Policy"));
	    	return element;
	 }
	public static WebElement alt_Inner(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Alt_Innter_Add"));
	    	return element;
	 }
	public static WebElement dialogHeading_Delete(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("dialogHeading_DeleteUser"));
	    	return element;
	 }
}
