package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class EventConfig_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element = null;
	
	 public static WebElement lnk_EventConfig(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EventConfigTab"));
	    	return element;
	 }
	 
	 public static WebElement btn_AddEventConfig(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EventConfigAdd"));
	    	return element;
	 }

    public static WebElement txtbx_EventRuleName(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("EventRuleName"));
    	return element;
    }
    
    public static WebElement txtbx_EventRuleDesc(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("EventRuleDesc"));
    	return element;
    }
    
    public static WebElement drpdown_EventRuleType(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("EventType"));
    	return element;
    }
    
    public static WebElement drpdown_EventUserName(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("EventUserName"));
    	return element;
    }
    
    public static WebElement drpdown_EventRiskLevel(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("EventRiskLevel"));
    	return element;
    }
    
    public static WebElement chkbox_SendSyslog(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SendSysLog"));
    	return element;
    }
    
    public static WebElement chkbox_SendSNMP(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SendSNMP"));
    	return element;
    }
    
    public static WebElement chkbox_SendMail(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("SendMail"));
    	return element;
    }
    
    public static WebElement txtbx_EventEmailAddress(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("EventEmailAddress"));
    	return element;
    }
    
	 public static WebElement btn_SaveEventAction(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EventRulesSave"));
	    	return element;
	 }
 
	 public static WebElement btn_CancelEventAction(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EventRulesCancel"));
	    	return element;
	 }
	 
	 public static WebElement btn_SaveEventGlobal(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("EventGlobalSave"));
	    	return element;
	 }
	 
	 public static WebElement alt_EventAction(WebDriver driver) throws Exception
	 {
		 parser.RepositoryParser(Constants.path_ObjectRepo);
		 element = driver.findElement(parser.getLocator("EventSuccessAlt"));
		 return element;
	 }

	 public static WebElement alt_EventGlobal(WebDriver driver) throws Exception
	 {
		 parser.RepositoryParser(Constants.path_ObjectRepo);
		 element = driver.findElement(parser.getLocator("EventGlobalAlt"));
		 return element;
	 }
}
