package com.randtronics.dpm.filemanager.appModules;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.appModules.Signin_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.Login_Page;
import com.randtronics.dpm.filemanager.pageObjects.Profile_Page;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Signin_Action 
{
	public static RepositoryParser parser=new RepositoryParser();
	public static WebElement element;
	public static boolean elestat;
	public static Properties prop=new Properties();

	public enum TEST_RESULT 
	{
		RESULT_SUCCESS
		{
			@Override
			public String toString()
			{
				return "Success";
			}
		},	
		RESULT_FAILURE
		{
			@Override
			public String toString()
			{
				return "Failure";
			}
		},
		RESULT_ERROR
		{
			@Override
			public String toString()
			{
				return "Error";
			}
		}, 
	}

	
	public static void execute_Login(WebDriver driver,String sUsername,String sPassword)throws Exception
	{
		Login_Page.txtbx_UserName(driver).sendKeys(sUsername);
		Login_Page.txtbx_Password(driver).sendKeys(sPassword);
		Login_Page.btn_LogIn(driver).click(); 
	}
		
	 public static boolean checkElementVisible(WebElement element)throws Exception
	 {
		  if(element.isDisplayed())
	  	  {
			  elestat=true;
	  	  }
	  	   else
	  	  {
	  		 elestat=false;
	  	  }
		  return elestat;
	  }
	 	 
	 public static TEST_RESULT execute_testLoginValidClick(WebDriver driver,String sUsername,String sPassword)throws Exception
	 {
		 Login_Page.txtbx_UserName(driver).sendKeys(sUsername);
		 Login_Page.txtbx_Password(driver).sendKeys(sPassword);
		 Login_Page.btn_LogIn(driver).click(); 
		 boolean chk=checkElementVisible(Home_Page.lnk_LogOut(driver));
		 Home_Page.lnk_LogOut(driver).click();
		 if(chk==true)
		 {
			 return TEST_RESULT.RESULT_SUCCESS;
		 }
		 else if(chk==false)
		 {
			 return TEST_RESULT.RESULT_FAILURE;
		 }
		 else
		 {
			 return TEST_RESULT.RESULT_ERROR;
		 }
	 }
	 
	 public static TEST_RESULT execute_Enter(WebDriver driver,String sUsername,String sPassword)throws Exception
	 {
		 parser.RepositoryParser(Constants.path_ObjectRepo);
		 Login_Page.txtbx_UserName(driver).sendKeys(sUsername);
		 Login_Page.txtbx_Password(driver).sendKeys(sPassword);
		 Login_Page.btn_LogIn(driver).sendKeys(Keys.ENTER);
		 boolean chk=checkElementVisible(Home_Page.lnk_LogOut(driver));
		 Home_Page.lnk_LogOut(driver).click();
		 if(chk==true)
		 {
			 return TEST_RESULT.RESULT_SUCCESS;
		 }
		 else if(chk==false)
		 {
			 return TEST_RESULT.RESULT_FAILURE;
		 }
		 else
		 {
			 return TEST_RESULT.RESULT_ERROR;
		 }	 
	 }
	 
	 public static TEST_RESULT execute_inValid(WebDriver driver,String sUsername,String sPassword)throws Exception
	 {
		 String actualURL="https://localhost:8443/dpmfile_web/index.jsp";
		 String ExpectedURL="";
		 Login_Page.txtbx_UserName(driver).sendKeys(sUsername);
		 Login_Page.txtbx_Password(driver).sendKeys(sPassword);
		 Login_Page.btn_LogIn(driver).click(); 
		 Thread.sleep(3000);
		 ExpectedURL=driver.getCurrentUrl();
		 if(!(ExpectedURL.equals(actualURL)))
		 {
			 driver.get(Constants.baseUrl);
			 return TEST_RESULT.RESULT_SUCCESS;
		 }
		 else if(ExpectedURL.equals(actualURL))
		 {
			 driver.get(Constants.baseUrl);
			 return TEST_RESULT.RESULT_FAILURE;
		 }
		 else
		 {
			 driver.get(Constants.baseUrl);
			 return TEST_RESULT.RESULT_ERROR;
		 }
	 }
	 
	 public static TEST_RESULT call_ProfileEmail_Save(WebDriver driver,String email) throws Exception
	 {
		 String altmsg="";
		 Profile_Page.txtbx_ProfileEmail(driver).clear();
		 Profile_Page.txtbx_ProfileEmail(driver).sendKeys(email);
		 Profile_Page.btn_SaveProfile(driver).click();
		 Thread.sleep(3000);
	     boolean elepre=checkElementVisible(driver.findElement(By.id("profileResp")));
	     System.out.println(elepre);
	     if(elepre)
	     {
		     altmsg=Profile_Page.alt_AlertMsg(driver).getText();
	     }
	     Thread.sleep(3000);
	     Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
	     Matcher matcher = pattern.matcher(email);

	     if (matcher.matches() && altmsg.equals("Email or Password updated successfully."))
	     {
			 return TEST_RESULT.RESULT_SUCCESS;
	     }
	     else if(!(matcher.matches() && altmsg.equals("Email or Password updated successfully.")))
	     {
	    	 return TEST_RESULT.RESULT_FAILURE;
	     }
	     else
	     {
	    	 return TEST_RESULT.RESULT_ERROR;
	     }
	 }
	 
	 public static TEST_RESULT call_ProfileEmail_Cancel(WebDriver driver,String email) throws Exception
	 {
		 Profile_Page.txtbx_ProfileEmail(driver).clear();
		 Profile_Page.txtbx_ProfileEmail(driver).sendKeys(email);
		 Profile_Page.btn_CancelProfile(driver).click();
		 String DashbordPageTitle=Home_Page.lnk_Dashboard(driver).getText();
		 Home_Page.lnk_Profile(driver).click();
		 String getEmailId=Profile_Page.txtbx_ProfileEmail(driver).getText();
		 if((DashbordPageTitle.equals("Dashboard")) && (getEmailId!=email))
		 {
			 Profile_Page.btn_CancelProfile(driver).click();
			 return TEST_RESULT.RESULT_SUCCESS;
		 }
		 else
		 {
			 Profile_Page.btn_CancelProfile(driver).click();
	    	 return TEST_RESULT.RESULT_FAILURE;
		 }	
	 }

	public static TEST_RESULT call_ProfilePassword_Save(WebDriver driver,
			String oldPwd, String newPwd, String confirmPwd) throws Exception
	{
		 Profile_Page.txtbx_OldPassword(driver).sendKeys(oldPwd);
		 Profile_Page.txtbx_NewPassword(driver).sendKeys(newPwd);
		 Profile_Page.txtbx_ConfirmPassword(driver).sendKeys(confirmPwd);
		 Profile_Page.btn_SaveProfile(driver).click();

		 boolean alt_Success=Profile_Page.alt_AlertMsg(driver).isDisplayed();
		 System.out.println("success alert status:"+alt_Success);
		 Thread.sleep(3000);
		 boolean chkDialog=driver.findElement(By.id("view_profile")).isDisplayed();
		 System.out.println("Add dialog status:"+chkDialog);

		 if((alt_Success==true) && (chkDialog==false))
		 {
			 driver.get(Constants.baseUrl);
			 return TEST_RESULT.RESULT_SUCCESS;
		 }
		 else
		 {
			 return TEST_RESULT.RESULT_FAILURE;
		 }	
	}
	
	 public static String execute_chkacclockout(WebDriver driver,String sUsername,String sPassword)throws Exception
	 {
		 Login_Page.txtbx_UserName(driver).sendKeys(sUsername);
		 Login_Page.txtbx_Password(driver).sendKeys(sPassword);
		 Login_Page.btn_LogIn(driver).click(); 
		 Thread.sleep(3000);
		 String getMsg=Login_Page.Alt_LogIn(driver).getText();
		 driver.get(Constants.baseUrl);
		 return getMsg;
	 }

}
