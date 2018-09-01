package com.randtronics.dpm.filemanager.executionEngine;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemUser_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;

public class SystemUser_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,SysUserName,SysUserDesc,SysUserTelNo,SysUserEmail,SysUserPwd,SysUserCnfPwd,AllRoles,SelectedRoles,Prerequisites;

	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password) throws Exception
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_SytemManagement(driver).click();
			SystemUser_Page.lnk_SytemManagement_SystemUser(driver).click();
			ExcelUtils.setExcelFile(Constants.Path_TestData + Constants.File_TestData);
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_SystemUser);
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}

	@Test(priority=1)
	public void addSysUser_Valid() throws Exception
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
				SysUserName=ExcelUtils.getCellData(iRow, 4);
				SysUserDesc=ExcelUtils.getCellData(iRow, 5);
				SysUserTelNo=ExcelUtils.getCellData(iRow, 6);	
				SysUserEmail=ExcelUtils.getCellData(iRow, 7);	
				SysUserPwd=ExcelUtils.getCellData(iRow, 8);	
				SysUserCnfPwd=ExcelUtils.getCellData(iRow, 9);	
				AllRoles=ExcelUtils.getCellData(iRow, 10);
				SelectedRoles=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("addSysUser_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemUser(driver, SysUserName, SysUserDesc, SysUserTelNo, SysUserEmail, SysUserPwd, SysUserCnfPwd, AllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
							if(a[0]==1)
							{
								TEST_RESULT result=SysMgmt_Action.execute_AfterAddSysUser(driver,a[1],SysUserName, SysUserDesc, SysUserTelNo, SysUserEmail, SysUserPwd, SysUserCnfPwd, AllRoles);
								Thread.sleep(5000);
								if(result==TEST_RESULT.RESULT_SUCCESS)
								{
									SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
									Thread.sleep(3000);
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
									ExcelUtils.setCellData("Pass", "System User Added Successfully",iRow, 13,14);
								}
								else
								{
									SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
									Thread.sleep(3000);
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
									ExcelUtils.setCellData("Fail", "System User Not Added Successfully",iRow, 13,14);
									Assert.fail();
								}
							}
							else
							{
								SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
								ExcelUtils.setCellData("Fail", "System User not found in the list",iRow, 13,14);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 13,14);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
					}
					SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysUserName + "," + SysUserDesc + "," + SysUserTelNo + "," + SysUserEmail + "," + SysUserPwd + "," + SysUserCnfPwd + "," + AllRoles + "," + SelectedRoles + "," + Prerequisites);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=2)
	public void addSysUser_Invalid()
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
				SysUserName=ExcelUtils.getCellData(iRow, 4);
				SysUserDesc=ExcelUtils.getCellData(iRow, 5);
				SysUserTelNo=ExcelUtils.getCellData(iRow, 6);	
				SysUserEmail=ExcelUtils.getCellData(iRow, 7);	
				SysUserPwd=ExcelUtils.getCellData(iRow, 8);	
				SysUserCnfPwd=ExcelUtils.getCellData(iRow, 9);	
				AllRoles=ExcelUtils.getCellData(iRow, 10);
				SelectedRoles=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("addSysUser_Invalid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh(); 
						int i[]=SysMgmt_Action.searchForDuplicateData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr/td[1]",SysUserName,0);
						System.out.println("i[0]:"+i[0]);
						System.out.println("i[1]:"+i[1]);
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemUser(driver, SysUserName, SysUserDesc, SysUserTelNo, SysUserEmail, SysUserPwd, SysUserCnfPwd, AllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_SUCCESS:
							int b[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
							System.out.println("b[0]:"+b[0]);
							if(b[0]==1)
							{
								SysMgmt_Action.execute_deleteSysUser(driver,b[1]);
								Thread.sleep(3000);
								SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
								ExcelUtils.setCellData("Fail", "",iRow, 13,14);
								didAnyMethodFail = true;
							}
							break;
						case RESULT_ERROR:

							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_FAILURE:

							int i1[]=SysMgmt_Action.searchForDuplicateData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr/td[1]",SysUserName,0);
							System.out.println("i1[0]:"+i1[0]);
							System.out.println("i1[1]:"+i1[1]);
							if(i[0]==i1[0])
							{
								SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
								ExcelUtils.setCellData("Pass", "",iRow, 13,14);
							}
							else
							{
								for(int k=1;k<=i[0];k++)
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);

									if(a2[0]==1)
									{
										SysMgmt_Action.execute_deleteSysUser(driver, a2[1]);
									}
									else
									{
										System.out.println("No System User found to delete");
									}
								}
								Thread.sleep(4000);
								SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
								ExcelUtils.setCellData("Fail", "System User Added",iRow, 13,14);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 13,14);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
					}
					SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysUserName + "," + SysUserDesc + "," + SysUserTelNo + "," + SysUserEmail + "," + SysUserPwd + "," + SysUserCnfPwd + "," + AllRoles + "," + SelectedRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=3)
	public void deleteSysUser_CheckDialog() 
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
				SysUserName=ExcelUtils.getCellData(iRow, 4);
				SysUserDesc=ExcelUtils.getCellData(iRow, 5);
				SysUserTelNo=ExcelUtils.getCellData(iRow, 6);	
				SysUserEmail=ExcelUtils.getCellData(iRow, 7);	
				SysUserPwd=ExcelUtils.getCellData(iRow, 8);	
				SysUserCnfPwd=ExcelUtils.getCellData(iRow, 9);	
				AllRoles=ExcelUtils.getCellData(iRow, 10);
				SelectedRoles=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("deleteSysUser_CheckDialog"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemUser(driver, SysUserName, SysUserDesc, SysUserTelNo, SysUserEmail, SysUserPwd, SysUserCnfPwd, AllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
							if(a[0]==1)
							{
								driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(a[1]+1)+"]/td[18]/div")).click();						
								boolean checkDialog=driver.findElements(By.xpath("html/body/div[66]")).size() != 0;
								if(checkDialog==true)
								{
									driver.findElement(By.linkText("Yes")).click();
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "Delete Confirmation Dialog Present",iRow, 13,14);
								}
								else
								{
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Delete Confirmation Dialog Not Found",iRow, 13,14);
									Assert.fail();
								}	  
							}
							else
							{
								SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "System User Not Found",iRow, 13,14);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 13,14);
					}	  
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div/div/div[1]/div/div/div[2]/div[1]/div/div/div[2]/div[1]/div/div[1]/div[2]/div[2]/table/tbody",SysUserName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
					}
					SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysUserName + "," + SysUserDesc + "," + "," + SysUserEmail + "," + SysUserPwd + "," + SysUserCnfPwd + "," + AllRoles + "," + SelectedRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=4)
	public void deleteSysUser_Yes()
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
				SysUserName=ExcelUtils.getCellData(iRow, 4);
				SysUserDesc=ExcelUtils.getCellData(iRow, 5);
				SysUserTelNo=ExcelUtils.getCellData(iRow, 6);	
				SysUserEmail=ExcelUtils.getCellData(iRow, 7);	
				SysUserPwd=ExcelUtils.getCellData(iRow, 8);	
				SysUserCnfPwd=ExcelUtils.getCellData(iRow, 9);	
				AllRoles=ExcelUtils.getCellData(iRow, 10);
				SelectedRoles=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("deleteSysUser_Yes"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemUser(driver, SysUserName, SysUserDesc, SysUserTelNo, SysUserEmail, SysUserPwd, SysUserCnfPwd, AllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
							if(a[0]==1)
							{
								SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver, "html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
								if(a1[0]==0)
								{
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "System User Deleted",iRow, 13,14);
								}
								else
								{
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "System User Not Deleted",iRow, 13,14);
									Assert.fail();
								}					
							}
							else
							{
								SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "System User Not Found",iRow, 13,14);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 13,14);
					}	  
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div/div/div[1]/div/div/div[2]/div[1]/div/div/div[2]/div[1]/div/div[1]/div[2]/div[2]/table/tbody",SysUserName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
					}
					SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysUserName + "," + SysUserDesc + "," + "," + SysUserEmail + "," + SysUserPwd + "," + SysUserCnfPwd + "," + AllRoles + "," + SelectedRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=5)
	public void deleteSysUser_No() 
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
				SysUserName=ExcelUtils.getCellData(iRow, 4);
				SysUserDesc=ExcelUtils.getCellData(iRow, 5);
				SysUserTelNo=ExcelUtils.getCellData(iRow, 6);	
				SysUserEmail=ExcelUtils.getCellData(iRow, 7);	
				SysUserPwd=ExcelUtils.getCellData(iRow, 8);	
				SysUserCnfPwd=ExcelUtils.getCellData(iRow, 9);	
				AllRoles=ExcelUtils.getCellData(iRow, 10);
				SelectedRoles=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("deleteSysUser_No"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemUser(driver, SysUserName, SysUserDesc, SysUserTelNo, SysUserEmail, SysUserPwd, SysUserCnfPwd, AllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
							if(a[0]==1)
							{

								driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(a[1]+1)+"]/td[18]/div")).click();						
								driver.findElement(By.linkText("No")).click();
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver, "html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
								if(a1[0]==1)
								{
									SysMgmt_Action.execute_deleteSysUser(driver,a1[1]);
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "System User Not Deleted",iRow, 13,14);
								}
								else
								{
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "System User Deleted",iRow, 13,14);
									Assert.fail();
								}					
							}
							else
							{
								SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "System User Not Found",iRow, 13,14);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 13,14);
					}	  
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[1]/div[1]/div[2]/div[1]/div[2]/div[3]/div/div/div[1]/div/div/div[2]/div[1]/div/div/div[2]/div[1]/div/div[1]/div[2]/div[2]/table/tbody",SysUserName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
					}
					SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysUserName + "," + SysUserDesc + "," + "," + SysUserEmail + "," + SysUserPwd + "," + SysUserCnfPwd + "," + AllRoles + "," + SelectedRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}


	@Test(priority=6)
	public void viewSysUser() 
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
				SysUserName=ExcelUtils.getCellData(iRow, 4);
				SysUserDesc=ExcelUtils.getCellData(iRow, 5);
				SysUserTelNo=ExcelUtils.getCellData(iRow, 6);	
				SysUserEmail=ExcelUtils.getCellData(iRow, 7);	
				SysUserPwd=ExcelUtils.getCellData(iRow, 8);	
				SysUserCnfPwd=ExcelUtils.getCellData(iRow, 9);	
				AllRoles=ExcelUtils.getCellData(iRow, 10);
				SelectedRoles=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("viewSysUser"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh(); 
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemUser(driver, SysUserName, SysUserDesc, SysUserTelNo, SysUserEmail, SysUserPwd, SysUserCnfPwd, AllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:						        	
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
							if(a[0]==1)
							{
								TEST_RESULT getResult=SysMgmt_Action.execute_viewSysUser(driver,a[1],SysUserName, SysUserDesc, SysUserTelNo, SysUserEmail, SysUserPwd, SysUserCnfPwd, AllRoles);
								if(getResult==TEST_RESULT.RESULT_SUCCESS)
								{
									SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
									Thread.sleep(3000);
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
									ExcelUtils.setCellData("Pass", getResult.toString(),iRow, 13,14);
								}
								else
								{
									SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
									Thread.sleep(3000);
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
									ExcelUtils.setCellData("Fail", "",iRow, 13,14);
									Assert.fail();
								}
							}
							else
							{
								SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
								ExcelUtils.setCellData("Fail", "",iRow, 13,14);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 13,14);
					}	 
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
					}
					SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysUserName + "," + SysUserDesc + "," + SysUserTelNo + "," + SysUserEmail + "," + SysUserPwd + "," + SysUserCnfPwd + "," + AllRoles + "," + SelectedRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();				
	}

	@Test(priority=7)
	public void modifySysUser_Valid() 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		String[] sptSysUserName=null;
		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				SysUserName=ExcelUtils.getCellData(iRow, 4);
				SysUserDesc=ExcelUtils.getCellData(iRow, 5);
				SysUserTelNo=ExcelUtils.getCellData(iRow, 6);	
				SysUserEmail=ExcelUtils.getCellData(iRow, 7);	
				SysUserPwd=ExcelUtils.getCellData(iRow, 8);	
				SysUserCnfPwd=ExcelUtils.getCellData(iRow, 9);	
				AllRoles=ExcelUtils.getCellData(iRow, 10);
				SelectedRoles=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("modifySysUser_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptSysUserName = SysUserName.split(",");
						String[] sptSysUserDesc = SysUserDesc.split(",");
						String[] sptSysUserTelNo = SysUserTelNo.split(",");
						String[] sptSysUserEmail = SysUserEmail.split(",");
						String[] sptSysUserPwd = SysUserPwd.split(",");
						String[] sptSysUserCfmPwd = SysUserCnfPwd.split(",");
						String[] sptSysUserAllRoles = AllRoles.split(",");

						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemUser(driver, sptSysUserName[0], sptSysUserDesc[0], sptSysUserTelNo[0], sptSysUserEmail[0], sptSysUserPwd[0], sptSysUserCfmPwd[0], AllRoles, Prerequisites);
						System.out.println("Add test result is:"+testResult);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysUserName[0],0);

							if(a[0]==1)
							{
								TEST_RESULT getRes=SysMgmt_Action.execute_modifySysUser(driver, a[1], sptSysUserName[1], sptSysUserDesc[1],sptSysUserTelNo[1], sptSysUserEmail[1], sptSysUserPwd[1],sptSysUserCfmPwd[1],sptSysUserAllRoles[1], SelectedRoles,sptSysUserAllRoles[0]);
								Thread.sleep(5000);
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_SUCCESS)
								{
									int a1[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysUserName[1],0);

									if(a1[0]==1)
									{
										TEST_RESULT chkModify=SysMgmt_Action.execute_AfterModifyClient(driver, a1[1], sptSysUserName[1], sptSysUserDesc[1],sptSysUserTelNo[1],sptSysUserEmail[1], sptSysUserPwd[1],sptSysUserCfmPwd[1],sptSysUserAllRoles[1], SelectedRoles);
										Thread.sleep(3000);
										//System.out.println("Modify test result:"+chkModify);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											SysMgmt_Action.execute_deleteSysUser(driver, a1[1]);
											Thread.sleep(3000);
											SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);	
											ExcelUtils.setCellData("Pass", "Modify - Success",iRow, 13,14);
										}
										else
										{
											SysMgmt_Action.execute_deleteSysUser(driver, a1[1]);
											Thread.sleep(3000);
											SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 13,14);
										}
									}
									else
									{
										int a2[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysUserName[0],0);
										if(a2[0]==1)
										{
											SysMgmt_Action.execute_deleteSysUser(driver, a2[1]);
										}
										Thread.sleep(3000);
										SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);	
										ExcelUtils.setCellData("Fail", "After modify client not found in list",iRow, 13,14);
										Assert.fail();
									}
								}
								else
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysUserName[0],0);
									if(a2[0]==1)
									{
										SysMgmt_Action.execute_deleteSysUser(driver, a2[1]);
									}
									Thread.sleep(3000);
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);	

									ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 13,14);
								}
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 13,14);
					}
				}
			}
			catch(Exception e)
			{
				try {
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
					}
					SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysUserName + "," + SysUserDesc + "," + SysUserTelNo + "," + SysUserEmail + "," + SysUserPwd + "," + SysUserCnfPwd + "," + AllRoles + "," + SelectedRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=8)
	public void modifySysUser_InValid() 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		String[] sptSysUserName=null;
		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				SysUserName=ExcelUtils.getCellData(iRow, 4);
				SysUserDesc=ExcelUtils.getCellData(iRow, 5);
				SysUserTelNo=ExcelUtils.getCellData(iRow, 6);	
				SysUserEmail=ExcelUtils.getCellData(iRow, 7);	
				SysUserPwd=ExcelUtils.getCellData(iRow, 8);	
				SysUserCnfPwd=ExcelUtils.getCellData(iRow, 9);	
				AllRoles=ExcelUtils.getCellData(iRow, 10);
				SelectedRoles=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("modifySysUser_InValid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptSysUserName = SysUserName.split(",");
						String[] sptSysUserDesc = SysUserDesc.split(",");
						String[] sptSysUserTelNo = SysUserTelNo.split(",");
						String[] sptSysUserEmail = SysUserEmail.split(",");
						String[] sptSysUserPwd = SysUserPwd.split(",");
						String[] sptSysUserCfmPwd = SysUserCnfPwd.split(",");
						String[] sptSysUserAllRoles = AllRoles.split(",");

						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemUser(driver, sptSysUserName[0], sptSysUserDesc[0], sptSysUserTelNo[0], sptSysUserEmail[0], sptSysUserPwd[0], sptSysUserCfmPwd[0], AllRoles, Prerequisites);
						System.out.println("Add test result is:"+testResult);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;

						case RESULT_SUCCESS:

							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysUserName[0],0);

							if(a[0]==1)
							{
								TEST_RESULT getRes=SysMgmt_Action.execute_modifySysUser(driver, a[1], sptSysUserName[1], sptSysUserDesc[1],sptSysUserTelNo[1], sptSysUserEmail[1], sptSysUserPwd[1],sptSysUserCfmPwd[1],sptSysUserAllRoles[1], SelectedRoles,sptSysUserAllRoles[0]);
								Thread.sleep(5000);	 
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_FAILURE)
								{	 


									int a1[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysUserName[0],0);

									if(a1[0]==1)
									{

										TEST_RESULT chkModify=SysMgmt_Action.execute_AfterModifyClient(driver, a1[1], sptSysUserName[0], sptSysUserDesc[0], sptSysUserTelNo[1], sptSysUserEmail[0], sptSysUserPwd[0],sptSysUserCfmPwd[0],sptSysUserAllRoles[0], SelectedRoles);
										Thread.sleep(3000);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											SysMgmt_Action.execute_deleteSysUser(driver, a1[1]);
											Thread.sleep(3000);
											SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);	
											ExcelUtils.setCellData("Pass", "Modify Invalid - Success",iRow, 13,14);
										}
										else
										{
											SysMgmt_Action.execute_deleteSysUser(driver, a1[1]);					 
											Thread.sleep(3000);
											SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 13,14);
											Assert.fail();
										}
									}
								}
								else
								{
									int i[]=SysMgmt_Action.searchForDuplicateData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr/td[1]",sptSysUserName[1],0);

									for(int k=1;k<=i[0];k++)
									{
										getRefresh();
										int a2[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysUserName[1],0);

										if(a2[0]==1)
										{
											SysMgmt_Action.execute_deleteSysUser(driver, a2[1]);
										}
										else
										{
											System.out.println("No System User found to delete");
										}
									}													 
									Thread.sleep(3000);
									SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Modify Invalid - Failed",iRow, 13,14);
									Assert.fail();
								}
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 13,14);
					}
				}
			}
			catch(Exception e)
			{
				try {
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysUserName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSysUser(driver,a[1]);
					}
					SysMgmt_Action.execute_deleteSysUserPrerequisites(driver, AllRoles);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysUserName + "," + SysUserDesc + "," + SysUserTelNo + "," + SysUserEmail + "," + SysUserPwd + "," + SysUserCnfPwd + "," + AllRoles + "," + SelectedRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=9)
	public void signOut() throws Exception
	{
		try
		{
			driver.navigate().refresh();
			Home_Page.lnk_LogOut(driver).click();
		}
		catch(Exception e)
		{
			System.out.println("Exception in signOut()"+e);
			Assert.fail("Sign Out Error");
		}
	}


	private void getRefresh() throws Exception
	{
		driver.navigate().refresh();
		Home_Page.lnk_SytemManagement(driver).click();
		SystemUser_Page.lnk_SytemManagement_SystemUser(driver).click();
		Thread.sleep(3000);
	}

}
