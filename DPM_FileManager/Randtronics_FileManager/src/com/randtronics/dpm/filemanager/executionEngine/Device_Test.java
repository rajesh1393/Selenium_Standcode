package com.randtronics.dpm.filemanager.executionEngine;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Policy_Action;
import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Device_Page;
import com.randtronics.dpm.filemanager.pageObjects.Email_Page;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Device_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,DeviceName,MoveDeviceFrom,MoveDeviceTo,Prerequisites;
	public static RepositoryParser parser=new RepositoryParser();
	String getTablePath="";

	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password)
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_SytemManagement(driver).click();
			Device_Page.lnk_Device(driver).click();
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_Device);
			parser.RepositoryParser(Constants.path_ObjectRepo);
			getTablePath=parser.prop.getProperty("Device_TablePath");
		}
		catch(Exception e)
		{
			Assert.fail("Signin Error");
		}
	}
	
	@Test(priority=1)
	public void moveDevice() 
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
				DeviceName=ExcelUtils.getCellData(iRow, 4);
				MoveDeviceTo=ExcelUtils.getCellData(iRow, 5);
				Prerequisites=ExcelUtils.getCellData(iRow, 6);

				if(MethodName.equalsIgnoreCase("moveDevice"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,2);							
						if(a[0]==1)
						{
							//Create group1 and group2. Move group2 to group1
							getRefresh();
							TEST_RESULT testResult = SysMgmt_Action.execute_moveDevice(driver, a[1], getTablePath,DeviceName,MoveDeviceTo,Prerequisites);
							Thread.sleep(5000);
							getRefresh();
							switch (testResult) 
							{
							case RESULT_FAILURE:
								SysMgmt_Action.deleteDevicePrerequisites(driver,Prerequisites);
								ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 7,8);
								didAnyMethodFail = true;
								break;
							case RESULT_ERROR:
								SysMgmt_Action.deleteDevicePrerequisites(driver,Prerequisites);
								ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 7,8);
								didAnyMethodFail = true;
								break;
							case RESULT_SUCCESS:
								Policy_Action.getDeviceGroup(driver,MoveDeviceTo);
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,2);	
								if(a1[0]==1)
								{
									SysMgmt_Action.deleteMovedDevice(driver,getTablePath,a1[1]);
									Thread.sleep(3000);
									SysMgmt_Action.deleteDevicePrerequisites(driver,Prerequisites);
									ExcelUtils.setCellData("Pass", TEST_RESULT.RESULT_SUCCESS.toString(),iRow, 7,8);
								}
								else
								{
									SysMgmt_Action.deleteDevicePrerequisites(driver,Prerequisites);
									ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 7,8);
								}
								break;
							}
						}
						else
						{
							ExcelUtils.setCellData("Fail", "Device not found in the list",iRow, 7,8);
							Assert.fail();
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 7,8);
					}	 
				}
			}
			catch(Exception e)
			{
				try
				{
					SysMgmt_Action.deleteDevicePrerequisites(driver,Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 7,8);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + DeviceName + "," + MoveDeviceTo + "," + Prerequisites);
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
			Device_Page.lnk_Device(driver).click();
			Device_Page.lnk_devicegroupRoot(driver).click();
			Thread.sleep(3000);
		}
		catch(Exception e)
		{

		}
	}


}
