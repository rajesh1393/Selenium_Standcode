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
import com.randtronics.dpm.filemanager.pageObjects.SystemRole_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemUser_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;

public class SystemRole_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,SysRoleName,SysRoleDesc,AllRights,SelectedRights,Prerequisites;


	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password) throws Exception
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_SytemManagement(driver).click();
			SystemRole_Page.lnk_SytemManagement_SystemRole(driver).click();
			//ExcelUtils.setExcelFile(Constants.Path_TestData + Constants.File_TestData);
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_SystemRole);
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}

	@Test(priority=1)
	public void addSysRole_Valid() throws Exception
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
				SysRoleName=ExcelUtils.getCellData(iRow, 4);
				SysRoleDesc=ExcelUtils.getCellData(iRow, 5);	
				AllRights=ExcelUtils.getCellData(iRow, 6);
				SelectedRights=ExcelUtils.getCellData(iRow, 7);
				Prerequisites=ExcelUtils.getCellData(iRow, 8);

				if(MethodName.equalsIgnoreCase("addSysRole_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemRole(driver, SysRoleName, SysRoleDesc, AllRights, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
							if(a[0]==1)
							{
								TEST_RESULT result=SysMgmt_Action.execute_AfterAddSysRole(driver,a[1],SysRoleName, SysRoleDesc, AllRights);
								Thread.sleep(5000);
								if(result==TEST_RESULT.RESULT_SUCCESS)
								{
									SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Pass", "System Role Added Successfully",iRow, 9,10);
								}
								else
								{
									SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "System Role Not Added Successfully",iRow, 9,10);
									Assert.fail();
								}
							}
							else
							{
								ExcelUtils.setCellData("Fail", "System Role not found in the list",iRow, 9,10);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 9,10);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysRoleName + "," + SysRoleDesc + "," +  AllRights + "," + SelectedRights + "," +Prerequisites);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=2)
	public void addSysRole_Invalid()
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
				SysRoleName=ExcelUtils.getCellData(iRow, 4);
				SysRoleDesc=ExcelUtils.getCellData(iRow, 5);	
				AllRights=ExcelUtils.getCellData(iRow, 6);
				SelectedRights=ExcelUtils.getCellData(iRow, 7);
				Prerequisites=ExcelUtils.getCellData(iRow, 8);

				if(MethodName.equalsIgnoreCase("addSysRole_Invalid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh(); 
						int i[]=SysMgmt_Action.searchForDuplicateData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr/td[1]",SysRoleName,0);
						System.out.println("i[0]:"+i[0]);
						System.out.println("i[1]:"+i[1]);
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemRole(driver, SysRoleName, SysRoleDesc, AllRights, Prerequisites);
						switch (testResult) 
						{
						case RESULT_SUCCESS:
							int b[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
							System.out.println("b[0]:"+b[0]);
							if(b[0]==1)
							{
								SysMgmt_Action.execute_deleteSystemRole(driver,b[1]);
								Thread.sleep(3000);
								ExcelUtils.setCellData("Fail", "System Role Added",iRow, 9,10);
								didAnyMethodFail = true;
							}
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_FAILURE:

							int i1[]=SysMgmt_Action.searchForDuplicateData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr/td[1]",SysRoleName,0);
							System.out.println("i1[0]:"+i1[0]);
							System.out.println("i1[1]:"+i1[1]);
							if(i[0]==i1[0])
							{
								ExcelUtils.setCellData("Pass", "System Role Not Added",iRow, 9,10);
							}
							else
							{
								for(int k=1;k<=i[0];k++)
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);

									if(a2[0]==1)
									{
										SysMgmt_Action.execute_deleteSystemRole(driver, a2[1]);
									}
									else
									{
										System.out.println("No System Role found to delete");
									}
								}
								Thread.sleep(4000);
								ExcelUtils.setCellData("Fail", "System Role Added",iRow, 9,10);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 9,10);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysRoleName + "," + SysRoleDesc + "," +  AllRights + "," + SelectedRights + "," +Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=3)
	public void deleteSysRole_CheckDialog() 
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
				SysRoleName=ExcelUtils.getCellData(iRow, 4);
				SysRoleDesc=ExcelUtils.getCellData(iRow, 5);	
				AllRights=ExcelUtils.getCellData(iRow, 6);
				SelectedRights=ExcelUtils.getCellData(iRow, 7);
				Prerequisites=ExcelUtils.getCellData(iRow, 8);

				if(MethodName.equalsIgnoreCase("deleteSysRole_CheckDialog"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemRole(driver, SysRoleName, SysRoleDesc, AllRights, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
							if(a[0]==1)
							{
								a[1]=a[1]+1;
								driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(a[1]+1)+"]/td[7]/div")).click();						
								boolean checkDialog=driver.findElements(By.xpath("html/body/div[51]")).size() != 0;
								if(checkDialog==true)
								{
									driver.findElement(By.linkText("Yes")).click();
									ExcelUtils.setCellData("Pass", "Delete Confirmation Dialog Present",iRow, 9,10);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "Delete Confirmation Dialog Not Found",iRow, 9,10);
									Assert.fail();
								}	  
							}
							else
							{
								ExcelUtils.setCellData("Fail", "System Role Not Found",iRow, 9,10);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 9,10);
					}	  
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysRoleName + "," + SysRoleDesc + "," +  AllRights + "," + SelectedRights + "," +Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=4)
	public void deleteSysRole_Yes()
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
				SysRoleName=ExcelUtils.getCellData(iRow, 4);
				SysRoleDesc=ExcelUtils.getCellData(iRow, 5);	
				AllRights=ExcelUtils.getCellData(iRow, 6);
				SelectedRights=ExcelUtils.getCellData(iRow, 7);
				Prerequisites=ExcelUtils.getCellData(iRow, 8);

				if(MethodName.equalsIgnoreCase("deleteSysRole_Yes"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemRole(driver, SysRoleName, SysRoleDesc, AllRights, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
							if(a[0]==1)
							{
								SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver, "html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
								if(a1[0]==0)
								{
									ExcelUtils.setCellData("Pass", "System Role Deleted",iRow, 9,10);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "System Role Not Deleted",iRow, 9,10);
									Assert.fail();
								}					
							}
							else
							{
								ExcelUtils.setCellData("Fail", "System Role Not Found",iRow, 9,10);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 9,10);
					}	  
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysRoleName + "," + SysRoleDesc + "," +  AllRights + "," + SelectedRights + "," +Prerequisites);
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
				SysRoleName=ExcelUtils.getCellData(iRow, 4);
				SysRoleDesc=ExcelUtils.getCellData(iRow, 5);	
				AllRights=ExcelUtils.getCellData(iRow, 6);
				SelectedRights=ExcelUtils.getCellData(iRow, 7);
				Prerequisites=ExcelUtils.getCellData(iRow, 8);

				if(MethodName.equalsIgnoreCase("addSysRole_Invalid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemRole(driver, SysRoleName, SysRoleDesc, AllRights, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
							if(a[0]==1)
							{

								driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(a[1]+1)+"]/td[7]/div")).click();						
								driver.findElement(By.linkText("No")).click();
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver, "html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
								if(a1[0]==1)
								{
									SysMgmt_Action.execute_deleteSystemRole(driver,a1[1]);
									ExcelUtils.setCellData("Pass", "System Role Not Deleted",iRow, 9,10);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "System Role Deleted",iRow, 9,10);
									Assert.fail();
								}					
							}
							else
							{
								ExcelUtils.setCellData("Fail", "System Role Not Found",iRow, 9,10);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 9,10);
					}	  
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysRoleName + "," + SysRoleDesc + "," +  AllRights + "," + SelectedRights + "," +Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=6)
	public void viewSysRole() 
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
				SysRoleName=ExcelUtils.getCellData(iRow, 4);
				SysRoleDesc=ExcelUtils.getCellData(iRow, 5);	
				AllRights=ExcelUtils.getCellData(iRow, 6);
				SelectedRights=ExcelUtils.getCellData(iRow, 7);
				Prerequisites=ExcelUtils.getCellData(iRow, 8);

				if(MethodName.equalsIgnoreCase("viewSysRole"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemRole(driver, SysRoleName, SysRoleDesc, AllRights, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:						        	
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
							if(a[0]==1)
							{
								TEST_RESULT getResult=SysMgmt_Action.execute_viewSysRole(driver,a[1],SysRoleName, SysRoleDesc, AllRights);
								if(getResult==TEST_RESULT.RESULT_SUCCESS)
								{
									SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Pass", "View - Success",iRow, 9,10);
								}
								else
								{
									SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "View - Failed",iRow, 9,10);
									Assert.fail();
								}
							}
							else
							{
								ExcelUtils.setCellData("Fail", "System Role not found in the list",iRow, 9,10);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 9,10);
					}	 
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysRoleName + "," + SysRoleDesc + "," +  AllRights + "," + SelectedRights + "," +Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();				
	}

	@Test(priority=7)
	public void modifySysRole_Valid() 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		String[] sptSysRoleName=null;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				SysRoleName=ExcelUtils.getCellData(iRow, 4);
				SysRoleDesc=ExcelUtils.getCellData(iRow, 5);	
				AllRights=ExcelUtils.getCellData(iRow, 6);
				SelectedRights=ExcelUtils.getCellData(iRow, 7);
				Prerequisites=ExcelUtils.getCellData(iRow, 8);

				if(MethodName.equalsIgnoreCase("modifySysRole_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptSysRoleName = SysRoleName.split(",");
						String[] sptSysRoleDesc = SysRoleDesc.split(",");
						String[] sptSysRoleAllRights = AllRights.split(",");

						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemRole(driver, sptSysRoleName[0], sptSysRoleDesc[0],sptSysRoleAllRights[0], Prerequisites);
						System.out.println("Add test result is:"+testResult);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysRoleName[0],0);

							if(a[0]==1)
							{
								TEST_RESULT getRes=SysMgmt_Action.execute_modifySysRole(driver, a[1], sptSysRoleName[1], sptSysRoleDesc[1],sptSysRoleAllRights[1], SelectedRights,sptSysRoleAllRights[0]);
								Thread.sleep(5000);
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_SUCCESS)
								{
									int a1[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysRoleName[1],0);

									if(a1[0]==1)
									{
										TEST_RESULT chkModify=SysMgmt_Action.execute_AfterModifySysRole(driver, a1[1], sptSysRoleName[1], sptSysRoleDesc[1], sptSysRoleAllRights[1], SelectedRights);
										Thread.sleep(3000);
										//System.out.println("Modify test result:"+chkModify);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											SysMgmt_Action.execute_deleteSystemRole(driver, a1[1]);
											Thread.sleep(3000);
											//getRefresh();
											ExcelUtils.setCellData("Pass", "Modify - Success",iRow, 9,10);
										}
										else
										{
											SysMgmt_Action.execute_deleteSystemRole(driver, a1[1]);
											Thread.sleep(3000);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 9,10);
										}
									}
									else
									{
										int a2[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysRoleName[0],0);
										if(a2[0]==1)
										{
											SysMgmt_Action.execute_deleteSystemRole(driver, a1[1]);
										}
										Thread.sleep(3000);
										ExcelUtils.setCellData("Fail", "After modify System Role not found in list",iRow, 9,10);
										Assert.fail();
									}
								}
								else
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysRoleName[0],0);
									if(a2[0]==1)
									{
										SysMgmt_Action.execute_deleteSystemRole(driver, a2[1]);
									}
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 9,10);
								}
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 9,10);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysRoleName + "," + SysRoleDesc + "," +  AllRights + "," + SelectedRights + "," +Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=8)
	public void modifySysRole_InValid() 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		String[] sptSysRoleName=null;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				SysRoleName=ExcelUtils.getCellData(iRow, 4);
				SysRoleDesc=ExcelUtils.getCellData(iRow, 5);	
				AllRights=ExcelUtils.getCellData(iRow, 6);
				SelectedRights=ExcelUtils.getCellData(iRow, 7);
				Prerequisites=ExcelUtils.getCellData(iRow, 8);

				if(MethodName.equalsIgnoreCase("modifySysRole_InValid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptSysRoleName = SysRoleName.split(",");
						String[] sptSysRoleDesc = SysRoleDesc.split(",");
						String[] sptSysRoleAllRights = AllRights.split(",");


						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSystemRole(driver, sptSysRoleName[0], sptSysRoleDesc[0], sptSysRoleAllRights[0], Prerequisites);
						System.out.println("Add test result is:"+testResult);

						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;

						case RESULT_SUCCESS:

							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysRoleName[0],0);

							if(a[0]==1)
							{
								TEST_RESULT getRes=SysMgmt_Action.execute_modifySysRole(driver, a[1], sptSysRoleName[1], sptSysRoleDesc[1], sptSysRoleAllRights[1], SelectedRights,sptSysRoleAllRights[0]);
								Thread.sleep(5000);	 
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_FAILURE)
								{	 
									int a1[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysRoleName[0],0);

									if(a1[0]==1)
									{

										TEST_RESULT chkModify=SysMgmt_Action.execute_AfterModifySysRole(driver, a1[1], sptSysRoleName[0], sptSysRoleDesc[0], sptSysRoleAllRights[0], SelectedRights);
										Thread.sleep(3000);
										//System.out.println("Modify test result:"+chkModify);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											SysMgmt_Action.execute_deleteSystemRole(driver, a1[1]);
											Thread.sleep(3000);
											ExcelUtils.setCellData("Pass", "Modify Invalid - Success",iRow, 9,10);
										}
										else
										{
											SysMgmt_Action.execute_deleteSystemRole(driver, a1[1]);
											Thread.sleep(3000);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 9,10);
											Assert.fail();
										}
									}
								}
								else
								{
									int i[]=SysMgmt_Action.searchForDuplicateData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr/td[1]",sptSysRoleName[1],0);

									for(int k=1;k<=i[0];k++)
									{
										getRefresh();
										int a2[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",sptSysRoleName[1],0);

										if(a2[0]==1)
										{
											SysMgmt_Action.execute_deleteSystemRole(driver, a2[1]);
										}
										else
										{
											System.out.println("No System Role found to delete");
										}
									}													 
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "Modify Invalid - Failed",iRow, 9,10);
									Assert.fail();
								}
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 9,10);
					}
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",SysRoleName,0);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemRole(driver,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysRoleName + "," + SysRoleDesc + "," +  AllRights + "," + SelectedRights + "," +Prerequisites);
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
			Assert.fail("Sign Out Error");
		}
	}

	private void getRefresh() throws Exception
	{
		driver.navigate().refresh();
		Home_Page.lnk_SytemManagement(driver).click();
		SystemRole_Page.lnk_SytemManagement_SystemRole(driver).click();
		Thread.sleep(3000);
	}
}
