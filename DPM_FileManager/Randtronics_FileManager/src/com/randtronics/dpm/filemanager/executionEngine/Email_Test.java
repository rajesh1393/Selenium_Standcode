package com.randtronics.dpm.filemanager.executionEngine;

import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Email_Page;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;

public class Email_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,IPAddress,Port,Email_Sent_From,Authentication_Req,Email_Username,Email_Password,Password_Authentication,Encryption;

	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password)
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_SytemManagement(driver).click();
			Email_Page.lnk_Email(driver).click();
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_Email);
		}
		catch(Exception e)
		{
			Assert.fail("Signin Error");
		}
	}

	@Test(priority=1)
	public void Email_Valid() throws Exception 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e) {Assert.fail();}

		boolean didAnyMethodFail = false;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				IPAddress=ExcelUtils.getCellData(iRow, 4);
				Port=ExcelUtils.getCellData(iRow, 5);
				Email_Sent_From=ExcelUtils.getCellData(iRow,6 );
				Authentication_Req=ExcelUtils.getCellData(iRow, 7);
				Email_Username=ExcelUtils.getCellData(iRow,8 );
				Email_Password=ExcelUtils.getCellData(iRow,9 );
				// Password_Authentication=ExcelUtils.getCellData(iRow, 10);
				Encryption=ExcelUtils.getCellData(iRow, 10);

				if(MethodName.equalsIgnoreCase("Email_Valid"))
				{
					if(ExecutionMode.equals("Y"))
					{
						getRefresh(); 
						TEST_RESULT testResult = SysMgmt_Action.execute_Email(driver,IPAddress,"465",Email_Sent_From,Authentication_Req,Email_Username,Email_Password,Password_Authentication,Encryption);

						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 11,12);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 11,12);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							ExcelUtils.setCellData("Pass", TEST_RESULT.RESULT_SUCCESS.toString(),iRow, 11,12);
							break;
						}
					}	  
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 12,13);
					}	  
				}
			}
			catch(Exception e)
			{
				try {
					ExcelUtils.setCellData("Error", "",iRow, 12,13);
				}
				catch (Exception ex) {
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Test" + iRow + " failed with parameters: " + IPAddress+ "," + Port+ "," + Email_Sent_From + "," + Authentication_Req + "," + Email_Username + "," + Email_Password + "," +Password_Authentication + "," +Encryption);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}


	@Test(priority=2)
	public void signOut()
	{
		try
		{
			driver.navigate().refresh();
			Home_Page.lnk_LogOut(driver).click();
		}
		catch(Exception e)
		{
			Assert.fail("Signout Error");
		}
	}

	public void getRefresh()
	{
		try
		{
			driver.navigate().refresh();
			Home_Page.lnk_SytemManagement(driver).click();
			Email_Page.lnk_Email(driver).click();
			Thread.sleep(3000);
		}
		catch(Exception e)
		{

		}
	}
}
