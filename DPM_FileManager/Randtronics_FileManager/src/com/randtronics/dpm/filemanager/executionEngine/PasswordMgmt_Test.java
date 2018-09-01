package com.randtronics.dpm.filemanager.executionEngine;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.PasswordMgmt_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class PasswordMgmt_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,KeyValue,pwd,ConfirmPassword;
	public static RepositoryParser parser=new RepositoryParser();
	
	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password) throws Exception
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_SytemManagement(driver).click();
			PasswordMgmt_Page.lnk_passwordMgmt(driver).click();
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_PasswordMgmt);
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}
	
	@Test(priority=1)
	@Parameters({"username","password"})
	public void pwdComplexity(String username,String password)
	{	
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		
		 Pattern pattern;
		 Matcher matcher;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				parser.RepositoryParser(Constants.path_ObjectRepo);
				
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				KeyValue=ExcelUtils.getCellData(iRow, 4);
				pwd=ExcelUtils.getCellData(iRow, 5);
				ConfirmPassword=ExcelUtils.getCellData(iRow, 6);
				
				String[] pwdComplex=KeyValue.split(";");
				int pwdComplexLen=pwdComplex.length;
				System.out.println(pwdComplexLen);

				if(MethodName.equalsIgnoreCase("pwdComplexity"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						
						List<WebElement> chkboxComplex = driver.findElements(By.name(parser.prop.getProperty("NoofCheckBox")));
						int chkboxSize = chkboxComplex.size();
						
						for(int i=0;i<chkboxSize;i++)
						{
							boolean chk=chkboxComplex.get(i).isSelected();
							if(chk==true)
							{
								chkboxComplex.get(i).click();
							}
						}
						

						for(int i=0;i<pwdComplexLen;i++)
						{
							for(int j=0; j< chkboxSize ; j++ )
							{
								String sValue = chkboxComplex.get(j).getAttribute("value");							
								if (sValue.equalsIgnoreCase(pwdComplex[i]))
								{
									chkboxComplex.get(j).click();
									break;
								}
							}
						}
						
						Thread.sleep(5000);
						PasswordMgmt_Page.btn_savePwdmgmt(driver).click();
						
						boolean alt_Success=PasswordMgmt_Page.alt_PwdMgmt(driver).isDisplayed();
						
						if(alt_Success==true)
						{
							Home_Page.lnk_Profile(driver).click();
							Thread.sleep(3000);
							
							PasswordMgmt_Page.txt_oldPassword(driver).sendKeys(password);
							PasswordMgmt_Page.txt_newPassword(driver).sendKeys(pwd);
							PasswordMgmt_Page.txt_confirmNewPassword(driver).sendKeys(ConfirmPassword);
							PasswordMgmt_Page.btn_saveProfile(driver).click();
							
							//String PASSWORD_PATTERN ="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
							
							String PASSWORD_PATTERN1 ="(?=.*[A-Z])";
							String PASSWORD_PATTERN2 ="(?=.*[a-z])";
							String PASSWORD_PATTERN3 ="(?=.*\\d)";
							String PASSWORD_PATTERN4 ="(?=.*[@#$%])";
							
							String pwdPattern="(";
							
							for(int i=0;i<pwdComplexLen;i++)
							{
								if(pwdComplex[i].equals("1"))
								{
									pwdPattern=pwdPattern+PASSWORD_PATTERN1;
								}
								if(pwdComplex[i].equals("2"))
								{
									pwdPattern=pwdPattern+PASSWORD_PATTERN2;
								}
								if(pwdComplex[i].equals("3"))
								{
									pwdPattern=pwdPattern+PASSWORD_PATTERN3;
								}
								if(pwdComplex[i].equals("4"))
								{
									pwdPattern=pwdPattern+PASSWORD_PATTERN4;
								}
							}
							pwdPattern=pwdPattern+")";
							
							pattern = Pattern.compile(pwdPattern);
							matcher = pattern.matcher(pwd);
							if(matcher.matches())
							{
								ExcelUtils.setCellData("Pass","Password Complexity Met",iRow, 7,8);
							}
							else
							{
								ExcelUtils.setCellData("Fail","Password Complexity Not Met",iRow, 7,8);
								Assert.fail();
							}
							
						}
						else
						{
							ExcelUtils.setCellData("Fail","Password Complexity Not Saved",iRow, 7,8);
							Assert.fail();
						}

					}
					else
					{
						ExcelUtils.setCellData("Skipped","",iRow, 7,8);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 7,8);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + KeyValue);

			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=2)
	@Parameters({"username","password"})
	public void minimumLength(String username,String password)
	{	
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		
		 Pattern pattern;
		 Matcher matcher;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				parser.RepositoryParser(Constants.path_ObjectRepo);
				
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				KeyValue=ExcelUtils.getCellData(iRow, 4);
				pwd=ExcelUtils.getCellData(iRow, 5);
				ConfirmPassword=ExcelUtils.getCellData(iRow, 6);
				
				if(MethodName.equalsIgnoreCase("minimumLength"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						
						PasswordMgmt_Page.txt_minimalLength(driver).clear();
						PasswordMgmt_Page.txt_minimalLength(driver).sendKeys(KeyValue);
						PasswordMgmt_Page.btn_savePwdmgmt(driver).click();
						
						//boolean alt_Success=driver.findElement(By.id(parser.prop.getProperty("PwdMgmt_AltMsg"))).isDisplayed();
						String getSuccessAlt=PasswordMgmt_Page.alt_PwdMgmt(driver).getText();
						if(getSuccessAlt.equals("Changes saved successfully."))
						{
							Home_Page.lnk_Profile(driver).click();
							Thread.sleep(3000);
							PasswordMgmt_Page.txt_oldPassword(driver).sendKeys(password);
							PasswordMgmt_Page.txt_newPassword(driver).sendKeys(pwd);
							PasswordMgmt_Page.txt_confirmNewPassword(driver).sendKeys(ConfirmPassword);
							PasswordMgmt_Page.btn_saveProfile(driver).click();
							
							String PASSWORD_PATTERN ="((?=.{"+ KeyValue + ",}))";
							System.out.println(PASSWORD_PATTERN);
							
							pattern = Pattern.compile(PASSWORD_PATTERN);
							matcher = pattern.matcher(pwd);
							if(matcher.matches())
							{
								ExcelUtils.setCellData("Pass","Password Length Met",iRow, 7,8);
							}
							else
							{
								ExcelUtils.setCellData("Fail","Password Length Not Met",iRow, 7,8);
								Assert.fail();
							}
							
						}
						else
						{
							ExcelUtils.setCellData("Fail","Password Length Not Saved",iRow, 7,8);
							Assert.fail();
						}

					}
					else
					{
						ExcelUtils.setCellData("Skipped","",iRow, 7,8);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 7,8);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + KeyValue);

			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=3)
	@Parameters({"username","password"})
	public void oldPasswordToRemember(String username,String password)
	{	
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
	
		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				parser.RepositoryParser(Constants.path_ObjectRepo);
				
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				//KeyValue=ExcelUtils.getCellData(iRow, 4);
				KeyValue="5";
				pwd=ExcelUtils.getCellData(iRow, 5);
				ConfirmPassword=ExcelUtils.getCellData(iRow, 6);
				
				if(MethodName.equalsIgnoreCase("oldPasswordToRemember"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						String currentpwd="";
						PasswordMgmt_Page.txt_noOfOldpwd(driver).clear();		
						PasswordMgmt_Page.txt_noOfOldpwd(driver).sendKeys(KeyValue);	
						PasswordMgmt_Page.btn_savePwdmgmt(driver).click();
						
						//boolean alt_Success=driver.findElement(By.id(parser.prop.getProperty("PwdMgmt_AltMsg"))).isDisplayed();
						String getSuccessAlt=PasswordMgmt_Page.alt_PwdMgmt(driver).getText();
						if(getSuccessAlt.equals("Changes saved successfully."))
						{
							Home_Page.lnk_Profile(driver).click();
							Thread.sleep(3000);
							int oldpwdtorem=Integer.parseInt(KeyValue);
							for(int i=0;i<(oldpwdtorem+1);i++)
							{
								if(i==0)
								{
									PasswordMgmt_Page.txt_oldPassword(driver).clear();
									PasswordMgmt_Page.txt_oldPassword(driver).sendKeys(password);
								}
								else
								{
									PasswordMgmt_Page.txt_oldPassword(driver).clear();
									PasswordMgmt_Page.txt_oldPassword(driver).sendKeys(currentpwd);
								}
								PasswordMgmt_Page.txt_newPassword(driver).clear();
								PasswordMgmt_Page.txt_newPassword(driver).sendKeys(password);
								
								PasswordMgmt_Page.txt_confirmNewPassword(driver).clear();
								PasswordMgmt_Page.txt_confirmNewPassword(driver).sendKeys(password);
								
								PasswordMgmt_Page.btn_saveProfile(driver).click();
								
								String getprofileAlt=PasswordMgmt_Page.alt_saveProfile(driver).getText();
								
								if(getprofileAlt.equals("Please correct the errors in input data to proceed."))
								{
									
									if(i==0)
									{
										PasswordMgmt_Page.txt_oldPassword(driver).clear();
										PasswordMgmt_Page.txt_oldPassword(driver).sendKeys(password);
									}
									else
									{
										PasswordMgmt_Page.txt_oldPassword(driver).clear();
										PasswordMgmt_Page.txt_oldPassword(driver).sendKeys(currentpwd);
									}
									pwd=(pwd+(i+1));
									System.out.println("New Password:"+pwd);
									PasswordMgmt_Page.txt_newPassword(driver).clear();
									PasswordMgmt_Page.txt_newPassword(driver).sendKeys(pwd);
									
									PasswordMgmt_Page.txt_confirmNewPassword(driver).clear();
									PasswordMgmt_Page.txt_confirmNewPassword(driver).sendKeys(pwd);
									
									PasswordMgmt_Page.btn_saveProfile(driver).click();
									
									currentpwd=pwd;
									String getSuccessAlt1=PasswordMgmt_Page.alt_saveProfile(driver).getText();
									if(getSuccessAlt1.equals("Profile data has been updated successfully!"))
									{
										continue;
									}
									else
									{
										ExcelUtils.setCellData("Error","Error in saving new password",iRow, 7,8);
										Assert.fail();
									}
								}
								else
								{
									if(i==oldpwdtorem)
									{
										ExcelUtils.setCellData("Pass","No of Old Passwords to Remember - Success",iRow, 7,8);
									}
									else
									{
										ExcelUtils.setCellData("Fail","No of Old Passwords to Remember - Failed",iRow, 7,8);
										Assert.fail();
									}
								}
							}
						}
						else
						{
							ExcelUtils.setCellData("Fail","No of Old passwords to Remember Not Saved",iRow, 7,8);
							Assert.fail();
						}

					}
					else
					{
						ExcelUtils.setCellData("Skipped","",iRow, 7,8);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 7,8);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + KeyValue);

			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=3)
	@Parameters({"username","password"})
	public void accountLockoutThreshold(String username,String password)
	{	
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
        
		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				parser.RepositoryParser(Constants.path_ObjectRepo);

				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				KeyValue=ExcelUtils.getCellData(iRow, 4);
				pwd=ExcelUtils.getCellData(iRow, 5);
				ConfirmPassword=ExcelUtils.getCellData(iRow, 6);

				if(MethodName.equalsIgnoreCase("accountLockoutThreshold"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();

						PasswordMgmt_Page.txt_accountLockout(driver).clear();		
						PasswordMgmt_Page.txt_accountLockout(driver).sendKeys(KeyValue);	
						PasswordMgmt_Page.btn_savePwdmgmt(driver).click();

						String getSuccessAlt=PasswordMgmt_Page.alt_PwdMgmt(driver).getText();
						if(getSuccessAlt.equals("Changes saved successfully."))
						{
							driver.navigate().refresh();
							Home_Page.lnk_LogOut(driver).click();
							int acclockout=Integer.parseInt(KeyValue);
							for(int i=0;i<(acclockout+1);i++)
							{
								pwd=(pwd+(i+1));
								String getResultMsg=Signin_Action.execute_chkacclockout(driver, username, pwd);
								if(getResultMsg.equals("Login failed. Please try again."))
								{
									continue;
								}
								else
								{
									if(i==acclockout)
									{
										ExcelUtils.setCellData("Pass","Account Lockout Threshold - Success",iRow, 7,8);
										Signin_Action.execute_Login(driver, username, password);
										Home_Page.lnk_SytemManagement(driver).click();
										PasswordMgmt_Page.lnk_passwordMgmt(driver).click();
									}
									else
									{
										ExcelUtils.setCellData("Fail","Account Lockout Threshold - Failed",iRow, 7,8);
										Signin_Action.execute_Login(driver, username, password);
										Home_Page.lnk_SytemManagement(driver).click();
										PasswordMgmt_Page.lnk_passwordMgmt(driver).click();
									}
								}
							}
						}
						else
						{
							ExcelUtils.setCellData("Fail","Account Lockout Not Saved",iRow, 7,8);
							Assert.fail();
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped","",iRow, 7,8);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 7,8);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + KeyValue);

			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=5)
	@Parameters({"username","password"})
	public void expirationPeriod(String username,String password)
	{	
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
        
		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				parser.RepositoryParser(Constants.path_ObjectRepo);

				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				KeyValue=ExcelUtils.getCellData(iRow, 4);
				pwd=ExcelUtils.getCellData(iRow, 5);
				ConfirmPassword=ExcelUtils.getCellData(iRow, 6);

				if(MethodName.equalsIgnoreCase("expirationPeriod"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();

						PasswordMgmt_Page.txt_expiryPeriod(driver).clear();		
						PasswordMgmt_Page.txt_expiryPeriod(driver).sendKeys(KeyValue);	
						PasswordMgmt_Page.btn_savePwdmgmt(driver).click();

						String getSuccessAlt=PasswordMgmt_Page.alt_PwdMgmt(driver).getText();
						if(getSuccessAlt.equals("Changes saved successfully."))
						{
							driver.navigate().refresh();
							int expiry=Integer.parseInt(KeyValue);
							if(expiry<30)
							{
								ExcelUtils.setCellData("Pass","Expiration Period - Success",iRow, 7,8);
							}
							else
							{
								ExcelUtils.setCellData("Fail","Expiration Period - Failed",iRow, 7,8);
							}
						}
						else
						{
							ExcelUtils.setCellData("Fail","Expiry Period Not Saved",iRow, 7,8);
							Assert.fail();
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped","",iRow, 7,8);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 7,8);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + KeyValue);

			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=6)
	public void signOut() throws Exception
	{
		try
		{
			driver.navigate().refresh();
			Home_Page.lnk_LogOut(driver).click();
		}
		catch(Exception e)
		{
			Assert.fail("Sign Out Error");
		}
	}

	private void getRefresh() throws Exception
	{
		driver.navigate().refresh();
		Home_Page.lnk_SytemManagement(driver).click();
		PasswordMgmt_Page.lnk_passwordMgmt(driver).click();
		Thread.sleep(3000);
	}
}
