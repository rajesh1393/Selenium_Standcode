package com.randtronics.dpm.filemanager.pageObjects;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class PasswordMgmt_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;

	
	public static WebElement lnk_passwordMgmt(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("PwdMgmtTab"));
	    	return element;
	 }
	
	public static WebElement title_PwdMgmt(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("PwdMgmtTitle"));
	    	return element;
	 }
	
	public static WebElement txt_minimalLength(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("MiniLengthTxtBx"));
	    	return element;
	 }
	
	public static WebElement txt_noOfOldpwd(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("NumRememberTxtBx"));
	    	return element;
	 }
	
	public static WebElement chkBx_CapitalLetters(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("CapitalLetterChkBx"));
	    	return element;
	 }
	
	public static WebElement chkBx_SmallLetters(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("SmallLetterChkBx"));
	    	return element;
	 }
	
	public static WebElement chkBx_Digits(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("DigitsChkBx"));
	    	return element;
	 }
	
	public static WebElement chkBx_SpecialCharacters(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("SplCharChkBx"));
	    	return element;
	 }
	
	public static WebElement txt_expiryPeriod(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("ExpPeriodTxtBx"));
	    	return element;
	 }
	
	public static WebElement txt_accountLockout(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("LockoutTxtBx"));
	    	return element;
	 }
	
	public static WebElement btn_savePwdmgmt(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("PwdMgmt_Save"));
	    	return element;
	 }
	
	public static WebElement btn_Cancel(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("PwdMgmt_Cancel"));
	    	return element;
	 }
	
	public static WebElement alt_PwdMgmt(WebDriver driver) throws Exception
	 {
	    	parser.RepositoryParser(Constants.path_ObjectRepo);
	    	element = driver.findElement(parser.getLocator("Pwdmgmt_Save_Alt"));
	    	return element;
	 }
	
	public static WebElement txt_oldPassword(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("currentPwd"));
		return element;
	}
	
	public static WebElement txt_newPassword(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("newPwd"));
		return element;
	}
	
	public static WebElement txt_confirmNewPassword(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("cnfNewPwd"));
		return element;
	}
	
	public static WebElement btn_saveProfile(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ProfileSave"));
		return element;
	}
	
	public static WebElement alt_saveProfile(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ProfileError"));
		return element;
	}


}
