package com.randtronics.dpm.filemanager.executionEngine;

import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.Systeminfo_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;

public class Systeminfo_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,ProtectPwd,ConfirmPwd;

	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password)
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_SytemManagement(driver).click();
			Systeminfo_Page.lnk_SystemInformation(driver).click();
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_SystemInformation);
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}

	@Test(priority=1)
	public void Backupsystem_Valid() throws Exception 
	{
		try
		{
			Thread.sleep(5000);
		}
		catch(Exception e) {Assert.fail();}

		boolean didAnyMethodFail = false;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				ProtectPwd=ExcelUtils.getCellData(iRow, 4);
				ConfirmPwd=ExcelUtils.getCellData(iRow, 5);

				if(MethodName.equalsIgnoreCase("Backupsystem_Valid"))
				{
					if(ExecutionMode.equals("Y"))
					{
						getRefresh(); 
						TEST_RESULT testResult=SysMgmt_Action.execute_backupsystem(driver,ProtectPwd,ConfirmPwd);

						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							ExcelUtils.setCellData("Pass", TEST_RESULT.RESULT_SUCCESS.toString(),iRow, 6,7);
							break;
						}
					}	  
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 6,7);
					}	  
				}
			}
			catch(Exception e)
			{
				try {
					ExcelUtils.setCellData("Error", "",iRow, 6,7);
				}
				catch (Exception ex) {
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Test" + iRow + " failed with parameters: " + ProtectPwd + "," + ConfirmPwd);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}




	@Test(priority=2)
	public void Restoresystem_Valid() throws Exception 
	{
		try
		{
			Thread.sleep(5000);
		}
		catch(Exception e) {Assert.fail();}

		boolean didAnyMethodFail = false;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				ProtectPwd=ExcelUtils.getCellData(iRow, 4);

				if(MethodName.equalsIgnoreCase("Restoresystem_Valid"))
				{
					if(ExecutionMode.equals("Y"))
					{
						getRefresh();  
						TEST_RESULT testResult=SysMgmt_Action.execute_Restoresystem(driver,ProtectPwd);

						switch (testResult) 
				        {
				        case RESULT_FAILURE:
					        ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 6,7);
					        didAnyMethodFail = true;
					        break;
				        case RESULT_ERROR:
					        ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 6,7);
					        didAnyMethodFail = true;
					        break;
				        case RESULT_SUCCESS:
							ExcelUtils.setCellData("Pass", TEST_RESULT.RESULT_SUCCESS.toString(),iRow, 6,7);
							break;
				         }
					  }	  
					  else
				      {
						  ExcelUtils.setCellData("Skipped", "",iRow, 6,7);
				      }	  
				  }
			  }
			catch(Exception e)
			{
				try {
					ExcelUtils.setCellData("Error", "",iRow, 6,7);
				}
				catch (Exception ex) {
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Test" + iRow + " failed with parameters: " + ProtectPwd);
			}
	    }
		
		if(didAnyMethodFail == true)
			Assert.fail();
	 }
	


	@Test(priority=3)
	public void signOut()
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

	public void getRefresh()
	{
		try
		{
			driver.navigate().refresh();
			Home_Page.lnk_SytemManagement(driver).click();
			Systeminfo_Page.lnk_SystemInformation(driver).click();	  
			Thread.sleep(3000);
		}
		catch(Exception e)
		{

		}
	}
}