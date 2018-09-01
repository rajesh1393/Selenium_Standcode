package com.randtronics.dpm.filemanager.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.utility.RepositoryParser;
import com.randtronics.dpm.filemanager.config.Constants;

public class PolicyRole_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;
	private static List<WebElement> ele1;
	
	public static WebElement lnk_PolicyRole(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("RoleTab"));
	    	return element;
	 }
	
	public static WebElement PageHeading_Policy(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("PolicyPageHeading"));
	    	return element;
	 }
	public static WebElement btn_AddRole(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("AddRole_Link"));
	    	return element;
	 }
	public static WebElement dialogHeading_Addrole(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("DialogHeading_Role"));
	    	return element;
	 }
	public static WebElement txt_Name(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Name_Role_Policy"));
	    	return element;
	 }
	public static WebElement txt_Desc(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Desc_Role_Policy"));
	    	return element;
	 }
	public static WebElement btn_Save(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Save_Role_Policy"));
	    	return element;
	 }
	public static WebElement btn_Cancel(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Cancel_Role_Policy"));
	    	return element;
	 }
	public static WebElement alt_RolePolicy(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Alert_Role_Policy"));
	    	return element;
	 }
	public static WebElement dialogHeading_Delete(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("DialogHeading_Delete"));
	    	return element;
	 }
	

}
