package com.randtronics.dpm.filemanager.executionEngine;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action;
import com.randtronics.dpm.filemanager.appModules.Signin_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;

public class Login_Test extends ChromeTest
{
	public static int noofRow;
	public String MethodName,ExecutionMode,UserName,Password;
	
	@Test(priority=0)
	public void setUpExcel()
	{
	    try
	    {
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_Login);
	    	noofRow=9;
	    }
	    catch(Exception e)
	    {
	    	Assert.fail("Spreadsheet Error");
	    }
	}
	
	@Test(priority=1)
	public void testLoginValidClick()
	{
		try
		{
			Thread.sleep(5000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				UserName=ExcelUtils.getCellData(iRow, 4);
				Password=ExcelUtils.getCellData(iRow, 5);	

				if(MethodName.equalsIgnoreCase("testLoginValidClick"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						TEST_RESULT testResult=Signin_Action.execute_testLoginValidClick(driver, UserName, Password);
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
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + UserName + "," + Password);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=2)
	public void testLoginValidEnter()
	{
		try
		{
			Thread.sleep(5000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				UserName=ExcelUtils.getCellData(iRow, 4);
				Password=ExcelUtils.getCellData(iRow, 5);	

				if(MethodName.equalsIgnoreCase("testLoginValidEnter"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						TEST_RESULT testResult=Signin_Action.execute_Enter(driver, UserName, Password);
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
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + UserName + "," + Password);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=3)
	public void testLoginInvalid()
	{
		
		try
		{
			Thread.sleep(5000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				UserName=ExcelUtils.getCellData(iRow, 4);
				Password=ExcelUtils.getCellData(iRow, 5);	

				if(MethodName.equalsIgnoreCase("testLoginInvalid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						TEST_RESULT testResult=Signin_Action.execute_inValid(driver, UserName, Password);
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
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + UserName + "," + Password);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}
}
