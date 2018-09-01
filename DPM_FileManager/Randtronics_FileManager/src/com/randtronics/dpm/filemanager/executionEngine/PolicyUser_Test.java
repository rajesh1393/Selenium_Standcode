package com.randtronics.dpm.filemanager.executionEngine;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Policy_Action;
import com.randtronics.dpm.filemanager.appModules.Policy_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.PolicyUser_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class PolicyUser_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,PolicyUserName,PolicyUserDesc,PolicyUserAllRoles,PolicyUserSelRoles,Prerequisites;
	public static RepositoryParser parser=new RepositoryParser();
	String getTablePath="";

	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password) throws Exception
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_Policy(driver).click();
			PolicyUser_Page.lnk_UserTab(driver).click();
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_PolicyUser);
			parser.RepositoryParser(Constants.path_ObjectRepo);
			getTablePath=parser.prop.getProperty("PolicyUser_TablePath");
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}
	
	@Test(priority=1)
	public void addPolicyUser_Valid() throws Exception
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
				PolicyUserName=ExcelUtils.getCellData(iRow, 4);
				PolicyUserDesc=ExcelUtils.getCellData(iRow, 5);
				PolicyUserAllRoles=ExcelUtils.getCellData(iRow, 6);	
				PolicyUserSelRoles=ExcelUtils.getCellData(iRow, 7);	
				Prerequisites=ExcelUtils.getCellData(iRow, 8);	

				if(MethodName.equalsIgnoreCase("addPolicyUser_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyUser(driver, PolicyUserName, PolicyUserDesc, PolicyUserAllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
							if(a[0]==1)
							{
								TEST_RESULT result=Policy_Action.execute_AfterAddPolicyUser(driver,a[1],getTablePath,PolicyUserName, PolicyUserDesc, PolicyUserAllRoles, PolicyUserSelRoles);
								Thread.sleep(5000);
								if(result==TEST_RESULT.RESULT_SUCCESS)
								{
									Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
									Thread.sleep(3000);
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "Policy User Added Successfully",iRow, 9,10);
								}
								else
								{
									Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
									Thread.sleep(3000);
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Policy User Not Added Successfully",iRow, 9,10);
									Assert.fail();
								}
							}
							else
							{
								Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Policy User not found in the list",iRow, 9,10);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
					}
					Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + PolicyUserName + "," + PolicyUserDesc + "," + PolicyUserAllRoles + "," + PolicyUserSelRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=2)
	public void addPolicyUser_Invalid()
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
				PolicyUserName=ExcelUtils.getCellData(iRow, 4);
				PolicyUserDesc=ExcelUtils.getCellData(iRow, 5);
				PolicyUserAllRoles=ExcelUtils.getCellData(iRow, 6);	
				PolicyUserSelRoles=ExcelUtils.getCellData(iRow, 7);	
				Prerequisites=ExcelUtils.getCellData(iRow, 8);	

				if(MethodName.equalsIgnoreCase("addPolicyUser_Invalid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh(); 
						int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",PolicyUserName,0);
						System.out.println("i[0]:"+i[0]);
						System.out.println("i[1]:"+i[1]);
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyUser(driver, PolicyUserName, PolicyUserDesc, PolicyUserAllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_SUCCESS:
							int b[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
							System.out.println("b[0]:"+b[0]);
							if(b[0]==1)
							{
								Policy_Action.execute_deletePolicyUser(driver,b[1],getTablePath);
								Thread.sleep(3000);
								Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Policy User Added",iRow, 9,10);
								didAnyMethodFail = true;
							}
							break;
						case RESULT_ERROR:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_FAILURE:
							int i1[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",PolicyUserName,0);
							System.out.println("i1[0]:"+i1[0]);
							System.out.println("i1[1]:"+i1[1]);
							if(i[0]==i1[0])
							{
								Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Pass", "Policy user not added",iRow, 9,10);
							}
							else
							{
								for(int k=1;k<=i[0];k++)
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);

									if(a2[0]==1)
									{
										Policy_Action.execute_deletePolicyUser(driver,a2[1],getTablePath);
									}
									else
									{
										System.out.println("No Policy User found to delete");
									}
								}
								Thread.sleep(4000);
								Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Policy User Added",iRow, 9,10);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
					}
					Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + PolicyUserName + "," + PolicyUserDesc + "," + PolicyUserAllRoles + "," + PolicyUserSelRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=3)
	public void deletePolicyUser_CheckDialog() 
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
				PolicyUserName=ExcelUtils.getCellData(iRow, 4);
				PolicyUserDesc=ExcelUtils.getCellData(iRow, 5);
				PolicyUserAllRoles=ExcelUtils.getCellData(iRow, 6);	
				PolicyUserSelRoles=ExcelUtils.getCellData(iRow, 7);	
				Prerequisites=ExcelUtils.getCellData(iRow, 8);	

				if(MethodName.equalsIgnoreCase("deletePolicyUser_CheckDialog"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyUser(driver, PolicyUserName, PolicyUserDesc, PolicyUserAllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
							if(a[0]==1)
							{
								driver.findElement(By.xpath(getTablePath+"/tr["+(a[1]+1)+"]/td[18]/div")).click();						
								boolean checkDialog=driver.findElements(By.xpath("html/body/div[57]")).size() != 0;
								if(checkDialog==true)
								{
									driver.findElement(By.linkText("Yes")).click();
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "Delete Confirmation Dialog Present",iRow, 9,10);
								}
								else
								{
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Delete Confirmation Dialog Not Found",iRow, 9,10);
									Assert.fail();
								}	  
							}
							else
							{
								Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Policy User Not Found",iRow, 9,10);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
					}
					Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + PolicyUserName + "," + PolicyUserDesc + "," + PolicyUserAllRoles + "," + PolicyUserSelRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=4)
	public void deletePolicyUser_Yes()
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
				PolicyUserName=ExcelUtils.getCellData(iRow, 4);
				PolicyUserDesc=ExcelUtils.getCellData(iRow, 5);
				PolicyUserAllRoles=ExcelUtils.getCellData(iRow, 6);	
				PolicyUserSelRoles=ExcelUtils.getCellData(iRow, 7);	
				Prerequisites=ExcelUtils.getCellData(iRow, 8);	

				if(MethodName.equalsIgnoreCase("deletePolicyUser_Yes"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyUser(driver, PolicyUserName, PolicyUserDesc, PolicyUserAllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
							if(a[0]==1)
							{
								Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver, getTablePath,PolicyUserName,0);
								if(a1[0]==0)
								{
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "Policy User Deleted",iRow, 9,10);
								}
								else
								{
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Policy User Not Deleted",iRow, 9,10);
									Assert.fail();
								}					
							}
							else
							{
								Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Policy User Not Found",iRow, 9,10);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
					}
					Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + PolicyUserName + "," + PolicyUserDesc + "," + PolicyUserAllRoles + "," + PolicyUserSelRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=5)
	public void deletePolicyUser_No() 
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
				PolicyUserName=ExcelUtils.getCellData(iRow, 4);
				PolicyUserDesc=ExcelUtils.getCellData(iRow, 5);
				PolicyUserAllRoles=ExcelUtils.getCellData(iRow, 6);	
				PolicyUserSelRoles=ExcelUtils.getCellData(iRow, 7);	
				Prerequisites=ExcelUtils.getCellData(iRow, 8);	

				if(MethodName.equalsIgnoreCase("deletePolicyUser_No"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyUser(driver, PolicyUserName, PolicyUserDesc, PolicyUserAllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
							if(a[0]==1)
							{
								driver.findElement(By.xpath(getTablePath+"/tr["+(a[1]+1)+"]/td[18]/div")).click();						
								driver.findElement(By.linkText("No")).click();
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver, getTablePath,PolicyUserName,0);
								if(a1[0]==1)
								{
									Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
									Thread.sleep(5000);
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "Policy User Not Deleted",iRow, 9,10);
								}
								else
								{
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Policy User Deleted",iRow, 9,10);
									Assert.fail();
								}					
							}
							else
							{
								Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Policy User Not Found",iRow, 9,10);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
					}
					Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + PolicyUserName + "," + PolicyUserDesc + "," + PolicyUserAllRoles + "," + PolicyUserSelRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}


	@Test(priority=6)
	public void viewPolicyUser() 
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
				PolicyUserName=ExcelUtils.getCellData(iRow, 4);
				PolicyUserDesc=ExcelUtils.getCellData(iRow, 5);
				PolicyUserAllRoles=ExcelUtils.getCellData(iRow, 6);	
				PolicyUserSelRoles=ExcelUtils.getCellData(iRow, 7);	
				Prerequisites=ExcelUtils.getCellData(iRow, 8);	

				if(MethodName.equalsIgnoreCase("viewPolicyUser"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyUser(driver, PolicyUserName, PolicyUserDesc, PolicyUserAllRoles, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:						        	
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
							if(a[0]==1)
							{
								TEST_RESULT getResult=Policy_Action.execute_viewPolicyUser(driver,a[1],getTablePath,PolicyUserName, PolicyUserDesc, PolicyUserAllRoles);
								if(getResult==TEST_RESULT.RESULT_SUCCESS)
								{
									Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
									Thread.sleep(5000);
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", getResult.toString(),iRow, 9,10);
								}
								else
								{
									Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
									Thread.sleep(5000);
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "",iRow, 9,10);
									Assert.fail();
								}
							}
							else
							{
								Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "",iRow, 9,10);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,PolicyUserName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
					}
					Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + PolicyUserName + "," + PolicyUserDesc + "," + PolicyUserAllRoles + "," + PolicyUserSelRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();				
	}

	@Test(priority=5)
	public void modifyPolicyUser_Valid() throws Exception 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e) {Assert.fail();}

		boolean didAnyMethodFail = false;
		String[] sptPolicyUserName=null;
		for(int iRow=0;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				PolicyUserName=ExcelUtils.getCellData(iRow, 4);
				PolicyUserDesc=ExcelUtils.getCellData(iRow, 5);
				PolicyUserAllRoles=ExcelUtils.getCellData(iRow, 6);	
				PolicyUserSelRoles=ExcelUtils.getCellData(iRow, 7);	
				Prerequisites=ExcelUtils.getCellData(iRow, 8);	

				if(MethodName.equalsIgnoreCase("modifyPolicyUser_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptPolicyUserName = PolicyUserName.split(",");
						String[] sptPolicyUserDesc = PolicyUserDesc.split(",");
						String[] sptPolicyAllRole = PolicyUserAllRoles.split(",");
						
						getRefresh();
						
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyUser(driver, sptPolicyUserName[0], sptPolicyUserDesc[0], sptPolicyAllRole[0], Prerequisites);

						switch (testResult) 
						{
						case RESULT_FAILURE:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptPolicyUserName[0],0);

							if(a[0]==1)
							{
								TEST_RESULT getRes=Policy_Action.execute_modifyPolicyUser(driver, a[1], getTablePath,sptPolicyUserName[1], sptPolicyUserDesc[1],sptPolicyAllRole[1], PolicyUserSelRoles,sptPolicyAllRole[0]);
								Thread.sleep(5000);
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_SUCCESS)
								{
									int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,sptPolicyUserName[1],0);

									if(a1[0]==1)
									{
										TEST_RESULT chkModify=Policy_Action.execute_AfterModifyPolicyUser(driver, a1[1], getTablePath, sptPolicyUserName[1], sptPolicyUserDesc[1],sptPolicyAllRole[1], PolicyUserSelRoles);
										Thread.sleep(3000);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
											Thread.sleep(3000);
											Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Pass", "Modify - Success",iRow, 9,10);
										}
										else
										{
											Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
											Thread.sleep(3000);
											Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 9,10);
										}
									}
									else
									{
										int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptPolicyUserName[0],0);
										if(a2[0]==1)
										{
											Policy_Action.execute_deletePolicyUser(driver,a2[1],getTablePath);
										}
										Thread.sleep(3000);
										Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
										ExcelUtils.setCellData("Fail", "After modify policy user not found in list",iRow, 9,10);
										Assert.fail();
									}
								}
								else
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptPolicyUserName[0],0);
									if(a2[0]==1)
									{
										Policy_Action.execute_deletePolicyUser(driver,a2[1],getTablePath);
									}
									Thread.sleep(3000);
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
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
					for(int i=0;i<sptPolicyUserName.length;i++)
					{
						int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptPolicyUserName[i],0);
						if(a[0]==1)
						{
							Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
						}
					}
					Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + PolicyUserName + "," + PolicyUserDesc + "," + PolicyUserAllRoles + "," + PolicyUserSelRoles + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=8)
	public void modifyPolicyUser_InValid() 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e) {Assert.fail();}

		boolean didAnyMethodFail = false;
		String[] sptPolicyUserName=null;
		for(int iRow=0;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				PolicyUserName=ExcelUtils.getCellData(iRow, 4);
				PolicyUserDesc=ExcelUtils.getCellData(iRow, 5);
				PolicyUserAllRoles=ExcelUtils.getCellData(iRow, 6);	
				PolicyUserSelRoles=ExcelUtils.getCellData(iRow, 7);	
				Prerequisites=ExcelUtils.getCellData(iRow, 8);	

				if(MethodName.equalsIgnoreCase("modifyPolicyUser_InValid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptPolicyUserName = PolicyUserName.split(",");
						String[] sptPolicyUserDesc = PolicyUserDesc.split(",");
						String[] sptPolicyAllRole = PolicyUserAllRoles.split(",");
						
						getRefresh();
						
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyUser(driver, sptPolicyUserName[0], sptPolicyUserDesc[0], sptPolicyAllRole[0], Prerequisites);

						switch (testResult) 
						{
						case RESULT_FAILURE:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 9,10);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptPolicyUserName[0],0);
							
							if(a[0]==1)
							{
								TEST_RESULT getRes=Policy_Action.execute_modifyPolicyUser(driver, a[1], getTablePath,sptPolicyUserName[1], sptPolicyUserDesc[1],sptPolicyAllRole[1], PolicyUserSelRoles,sptPolicyAllRole[0]);
								Thread.sleep(5000);
								getRefresh();
								
								if(getRes==TEST_RESULT.RESULT_FAILURE)
								{	 
									int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,sptPolicyUserName[0],0);

									if(a1[0]==1)
									{
										TEST_RESULT chkModify=Policy_Action.execute_AfterModifyPolicyUser(driver, a1[1], getTablePath, sptPolicyUserName[0], sptPolicyUserDesc[0],sptPolicyAllRole[0], PolicyUserSelRoles);
										Thread.sleep(3000);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
											Thread.sleep(3000);
											Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Pass", "Modify Invalid - Success",iRow, 9,10);
										}
										else
										{
											Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
											Thread.sleep(3000);
											Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 9,10);
											Assert.fail();
										}
									}
								}
								else
								{
									int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",sptPolicyUserName[1],0);

									for(int k=1;k<=i[0];k++)
									{
										getRefresh();
										int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptPolicyUserName[1],0);

										if(a2[0]==1)
										{
											Policy_Action.execute_deletePolicyUser(driver,a2[1],getTablePath);
										}
										else
										{
											System.out.println("No Policy User found to delete");
										}
									}													 
									Thread.sleep(3000);
									Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
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
					for(int i=0;i<sptPolicyUserName.length;i++)
					{
						int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptPolicyUserName[i],0);
						if(a[0]==1)
						{
							Policy_Action.execute_deletePolicyUser(driver,a[1],getTablePath);
						}
					}
					Policy_Action.execute_deletePolicyUserPrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 9,10);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + PolicyUserName + "," + PolicyUserDesc + "," + PolicyUserAllRoles + "," + PolicyUserSelRoles + "," + Prerequisites);
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
		Home_Page.lnk_Policy(driver).click();
		PolicyUser_Page.lnk_UserTab(driver).click();
		Thread.sleep(3000);
	}

}
