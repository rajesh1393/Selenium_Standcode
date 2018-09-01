package com.randtronics.dpm.filemanager.executionEngine;

import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.appModules.Signin_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.Profile_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Profile_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,ProfileEmail,OldPwd,NewPwd,ConfirmPwd;
	public static RepositoryParser parser=new RepositoryParser();
	boolean checkName,checkRoles,checkGroups;
  
	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password) throws Exception
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_Profile);
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}
	
	@Test(priority=1)
	public void test_DisabledState()
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}
		try
		{
			Home_Page.lnk_Profile(driver).click();
			checkName=Profile_Page.txtbx_ProfileUserName(driver).isEnabled();
			System.out.println(checkName);
			checkRoles=Profile_Page.txtbx_ProfileRoles(driver).isEnabled();
			System.out.println(checkRoles);
			checkGroups=Profile_Page.txtbx_ProfileGroups(driver).isEnabled();
			System.out.println(checkGroups);
			Thread.sleep(3000);
			if(checkName==false && checkRoles==false && checkGroups==false)
			{
				System.out.println("User Name, Roles and Groups are disabled");
			}  
			else
			{
				Assert.fail("User Name, Roles and Groups are enabled");
			}

		}
		catch(Exception e)
		{
			Assert.fail("test_DisabledState() Failed");
		}
	}
	
	@Test(priority=2)
	public void test_Email_ProfileSave()
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
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
			    ProfileEmail = ExcelUtils.getCellData(iRow, 4);
	
				if(MethodName.equalsIgnoreCase("test_Email_ProfileSave"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						TEST_RESULT testResult=Signin_Action.call_ProfileEmail_Save(driver,ProfileEmail);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", "Invalid Email",iRow, 8,9);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 8,9);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							ExcelUtils.setCellData("Error", "Valid Email",iRow, 8,9);
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 8,9);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 8,9);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + ProfileEmail);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=3)
	public void test_Email_ProfileCancel()
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
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
			    ProfileEmail = ExcelUtils.getCellData(iRow, 4);
	
				if(MethodName.equalsIgnoreCase("test_Email_ProfileCancel"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						TEST_RESULT testResult=Signin_Action.call_ProfileEmail_Cancel(driver,ProfileEmail);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", "Profile Cancel - Failed",iRow, 8,9);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 8,9);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							ExcelUtils.setCellData("Error", "Profile Cancel - Success",iRow, 8,9);
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 8,9);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 8,9);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + ProfileEmail);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=4)
	public void test_Profile_Changepassword_Valid()
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
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
			    ProfileEmail = ExcelUtils.getCellData(iRow, 4);
			    
			    OldPwd = ExcelUtils.getCellData(iRow, 5);
			    NewPwd = ExcelUtils.getCellData(iRow, 6);
			    ConfirmPwd = ExcelUtils.getCellData(iRow, 7);

				if(MethodName.equalsIgnoreCase("test_Profile_Changepassword_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
					    Home_Page.lnk_Profile(driver).click();
						TEST_RESULT testResult=Signin_Action.call_ProfilePassword_Save(driver, OldPwd, NewPwd, ConfirmPwd);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", "Password not Changed",iRow, 8,9);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 8,9);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							ExcelUtils.setCellData("Pass", "Password Changed Successfully",iRow, 8,9);
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 8,9);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 8,9);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + OldPwd + "," + NewPwd + "," + ConfirmPwd);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=4)
	public void test_Profile_Changepassword_InValid()
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
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
			    ProfileEmail = ExcelUtils.getCellData(iRow, 4);
			    
			    OldPwd = ExcelUtils.getCellData(iRow, 5);
			    NewPwd = ExcelUtils.getCellData(iRow, 6);
			    ConfirmPwd = ExcelUtils.getCellData(iRow, 7);

				if(MethodName.equalsIgnoreCase("test_Profile_Changepassword_InValid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
					    Home_Page.lnk_Profile(driver).click();
						TEST_RESULT testResult=Signin_Action.call_ProfilePassword_Save(driver, OldPwd, NewPwd, ConfirmPwd);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Pass", "Password not changed",iRow, 8,9);
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 8,9);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							ExcelUtils.setCellData("Fail", "Password changed",iRow, 8,9);
							didAnyMethodFail = true;
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 8,9);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 8,9);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + OldPwd + "," + NewPwd + "," + ConfirmPwd);
			}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
}
}


