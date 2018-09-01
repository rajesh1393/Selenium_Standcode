package com.randtronics.dpm.filemanager.executionEngine;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action;
import com.randtronics.dpm.filemanager.appModules.Audit_Action;
import com.randtronics.dpm.filemanager.appModules.Audit_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.EventConfig_Page;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemUser_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class EventConfig_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,EARuleName,EARuleDesc,EventType,EventUser,EventRiskLevel,EventOperation,EventOperationDeselect,EventEmail,Prerequisites;
	public static RepositoryParser parser=new RepositoryParser();
	String getTablePath="";

	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password) throws Exception
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_Audit(driver).click();
			EventConfig_Page.lnk_EventConfig(driver).click();
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_EventConfig);
			parser.RepositoryParser(Constants.path_ObjectRepo);
			getTablePath=parser.prop.getProperty("EventActionRule_TablePath");
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}
	
	@Test(priority=1)
	public void addEventActionRule_Valid() throws Exception
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
				EARuleName=ExcelUtils.getCellData(iRow, 4);
				EARuleDesc=ExcelUtils.getCellData(iRow, 5);
				EventType=ExcelUtils.getCellData(iRow, 6);	
				EventUser=ExcelUtils.getCellData(iRow, 7);	
				EventRiskLevel=ExcelUtils.getCellData(iRow, 8);	
				EventOperation=ExcelUtils.getCellData(iRow, 9);	
				EventOperationDeselect=ExcelUtils.getCellData(iRow, 10);
				EventEmail=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("addEventActionRule_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Audit_Action.execute_addEventActionRule(driver, EARuleName, EARuleDesc, EventType, EventUser, EventRiskLevel, EventOperation, EventEmail, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
							if(a[0]==1)
							{
								TEST_RESULT result=Audit_Action.execute_AfterAddEventActionRule(driver,a[1],getTablePath,EARuleName, EARuleDesc, EventType, EventUser, EventRiskLevel, EventOperation, EventEmail);
								Thread.sleep(5000);
								if(result==TEST_RESULT.RESULT_SUCCESS)
								{
									Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "Event Action Rule Added Successfully",iRow, 13,14);
								}
								else
								{
									Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Event Action Rule Not Added Successfully",iRow, 13,14);
									Assert.fail();
								}
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Event Action Rule not found in the list",iRow, 13,14);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
					if(a[0]==1)
					{
						Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
					}
					Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				System.out.println(e);
				Assert.fail("Failed " + iRow + "With Parameters:" + EARuleName + "," + EARuleDesc + "," + EventType + "," + EventUser + "," + EventRiskLevel + "," + EventOperation + "," + Prerequisites);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=2)
	public void addEventActionRule_Invalid()
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
				EARuleName=ExcelUtils.getCellData(iRow, 4);
				EARuleDesc=ExcelUtils.getCellData(iRow, 5);
				EventType=ExcelUtils.getCellData(iRow, 6);	
				EventUser=ExcelUtils.getCellData(iRow, 7);	
				EventRiskLevel=ExcelUtils.getCellData(iRow, 8);	
				EventOperation=ExcelUtils.getCellData(iRow, 9);	
				EventOperationDeselect=ExcelUtils.getCellData(iRow, 10);
				EventEmail=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("addEventActionRule_Invalid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh(); 
						int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",EARuleName,0);
						System.out.println("i[0]:"+i[0]);
						System.out.println("i[1]:"+i[1]);
						TEST_RESULT testResult =  Audit_Action.execute_addEventActionRule(driver, EARuleName, EARuleDesc, EventType, EventUser, EventRiskLevel, EventOperation, EventEmail, Prerequisites);
						switch (testResult) 
						{
						case RESULT_SUCCESS:
							int b[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
							System.out.println("b[0]:"+b[0]);
							if(b[0]==1)
							{
								Audit_Action.execute_deleteEventActionRule(driver,getTablePath,b[1]);
								Thread.sleep(3000);
								Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Event Action Rule Added",iRow, 13,14);
								didAnyMethodFail = true;
							}
							break;
						case RESULT_ERROR:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_FAILURE:
							int i1[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",EARuleName,0);
							System.out.println("i1[0]:"+i1[0]);
							System.out.println("i1[1]:"+i1[1]);
							if(i[0]==i1[0])
							{
								Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Pass", "Event Action Rule Not Added",iRow, 13,14);
							}
							else
							{
								for(int k=1;k<=i[0];k++)
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);

									if(a2[0]==1)
									{
										Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a2[1]);
									}
									else
									{
										System.out.println("No Event Action Rule found to delete");
									}
								}
								Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
								Thread.sleep(4000);
								ExcelUtils.setCellData("Fail", "Event Action Rule Added",iRow, 13,14);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
					if(a[0]==1)
					{
						Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
					}
					Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + EARuleName + "," + EARuleDesc + "," + EventType + "," + EventUser + "," + EventRiskLevel + "," + EventOperation + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=3)
	public void deleteEventActionRule_CheckDialog() 
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
				EARuleName=ExcelUtils.getCellData(iRow, 4);
				EARuleDesc=ExcelUtils.getCellData(iRow, 5);
				EventType=ExcelUtils.getCellData(iRow, 6);	
				EventUser=ExcelUtils.getCellData(iRow, 7);	
				EventRiskLevel=ExcelUtils.getCellData(iRow, 8);	
				EventOperation=ExcelUtils.getCellData(iRow, 9);	
				EventOperationDeselect=ExcelUtils.getCellData(iRow, 10);
				EventEmail=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("deleteEventActionRule_CheckDialog"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Audit_Action.execute_addEventActionRule(driver, EARuleName, EARuleDesc, EventType, EventUser, EventRiskLevel, EventOperation, EventEmail, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
							if(a[0]==1)
							{
								driver.findElement(By.xpath(getTablePath+"/tr["+(a[1]+1)+"]/td[17]/div")).click();						
								boolean checkDialog=driver.findElements(By.xpath("html/body/div[44]")).size() != 0;
								if(checkDialog==true)
								{
									driver.findElement(By.linkText("Yes")).click();
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "Delete Confirmation Dialog Present",iRow, 13,14);
								}
								else
								{
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Delete Confirmation Dialog Not Found",iRow, 13,14);
									Assert.fail();
								}	  
							}
							else
							{
								Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Event Action Rule Not Found",iRow, 13,14);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
					if(a[0]==1)
					{
						Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
					}
					Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + EARuleName + "," + EARuleDesc + "," + EventType + "," + EventUser + "," + EventRiskLevel + "," + EventOperation + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=4)
	public void deleteEventActionRule_Yes()
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
				EARuleName=ExcelUtils.getCellData(iRow, 4);
				EARuleDesc=ExcelUtils.getCellData(iRow, 5);
				EventType=ExcelUtils.getCellData(iRow, 6);	
				EventUser=ExcelUtils.getCellData(iRow, 7);	
				EventRiskLevel=ExcelUtils.getCellData(iRow, 8);	
				EventOperation=ExcelUtils.getCellData(iRow, 9);	
				EventOperationDeselect=ExcelUtils.getCellData(iRow, 10);
				EventEmail=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("deleteEventActionRule_Yes"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Audit_Action.execute_addEventActionRule(driver, EARuleName, EARuleDesc, EventType, EventUser, EventRiskLevel, EventOperation, EventEmail, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
							if(a[0]==1)
							{
								Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
								if(a1[0]==0)
								{
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "Event Action Rule Deleted",iRow, 13,14);
								}
								else
								{
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Event Action Rule Not Deleted",iRow, 13,14);
									Assert.fail();
								}					
							}
							else
							{
								Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Event Action Rule Not Found",iRow, 13,14);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
					if(a[0]==1)
					{
						Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
					}
					Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + EARuleName + "," + EARuleDesc + "," + EventType + "," + EventUser + "," + EventRiskLevel + "," + EventOperation + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=5)
	public void deleteEventActionRule_No() 
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
				EARuleName=ExcelUtils.getCellData(iRow, 4);
				EARuleDesc=ExcelUtils.getCellData(iRow, 5);
				EventType=ExcelUtils.getCellData(iRow, 6);	
				EventUser=ExcelUtils.getCellData(iRow, 7);	
				EventRiskLevel=ExcelUtils.getCellData(iRow, 8);	
				EventOperation=ExcelUtils.getCellData(iRow, 9);	
				EventOperationDeselect=ExcelUtils.getCellData(iRow, 10);
				EventEmail=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("deleteEventActionRule_No"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Audit_Action.execute_addEventActionRule(driver, EARuleName, EARuleDesc, EventType, EventUser, EventRiskLevel, EventOperation, EventEmail, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
							if(a[0]==1)
							{
								driver.findElement(By.xpath(getTablePath+"/tr["+(a[1]+1)+"]/td[17]/div")).click();						
								driver.findElement(By.linkText("No")).click();
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
								if(a1[0]==1)
								{
									Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", "Event Action Rule Not Deleted",iRow, 13,14);
								}
								else
								{
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "Event Action Rule Deleted",iRow, 13,14);
									Assert.fail();
								}					
							}
							else
							{
								Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
								ExcelUtils.setCellData("Fail", "Event Action Rule Not Found",iRow, 13,14);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
					if(a[0]==1)
					{
						Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
					}
					Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + EARuleName + "," + EARuleDesc + "," + EventType + "," + EventUser + "," + EventRiskLevel + "," + EventOperation + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}


	@Test(priority=6)
	public void viewEventActionRule() 
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
				EARuleName=ExcelUtils.getCellData(iRow, 4);
				EARuleDesc=ExcelUtils.getCellData(iRow, 5);
				EventType=ExcelUtils.getCellData(iRow, 6);	
				EventUser=ExcelUtils.getCellData(iRow, 7);	
				EventRiskLevel=ExcelUtils.getCellData(iRow, 8);	
				EventOperation=ExcelUtils.getCellData(iRow, 9);	
				EventOperationDeselect=ExcelUtils.getCellData(iRow, 10);
				EventEmail=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);
				
				if(MethodName.equalsIgnoreCase("viewEventActionRule"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Audit_Action.execute_addEventActionRule(driver, EARuleName, EARuleDesc, EventType, EventUser, EventRiskLevel, EventOperation, EventEmail, Prerequisites);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:						        	
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
							if(a[0]==1)
							{
								TEST_RESULT getResult=Audit_Action.execute_viewSysUser(driver,a[1],getTablePath,EARuleName, EARuleDesc, EventType, EventUser, EventRiskLevel, EventOperation, EventEmail);
								if(getResult==TEST_RESULT.RESULT_SUCCESS)
								{
									Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Pass", getResult.toString(),iRow, 13,14);
								}
								else
								{
									Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
									ExcelUtils.setCellData("Fail", "",iRow, 13,14);
									Assert.fail();
								}
							}
							else
							{
								Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,EARuleName,0);
					if(a[0]==1)
					{
						Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
					}
					Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + EARuleName + "," + EARuleDesc + "," + EventType + "," + EventUser + "," + EventRiskLevel + "," + EventOperation + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();				
	}
	
	@Test(priority=7)
	public void modifyEventActionRule_Valid() 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		String[] sptEARuleName=null;
		
		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				EARuleName=ExcelUtils.getCellData(iRow, 4);
				EARuleDesc=ExcelUtils.getCellData(iRow, 5);
				EventType=ExcelUtils.getCellData(iRow, 6);	
				EventUser=ExcelUtils.getCellData(iRow, 7);	
				EventRiskLevel=ExcelUtils.getCellData(iRow, 8);	
				EventOperation=ExcelUtils.getCellData(iRow, 9);	
				EventOperationDeselect=ExcelUtils.getCellData(iRow, 10);
				EventEmail=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("modifyEventActionRule_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptEARuleName = EARuleName.split(",");
						String[] sptEARuleDesc = EARuleDesc.split(",");
						String[] sptEARuleType = EventType.split(",");
						String[] sptEARuleUser = EventUser.split(",");
						String[] sptEARuleRiskLevel = EventRiskLevel.split(",");
						String[] sptEARuleOpe= EventOperation.split(",");
						String[] sptEARuleEmail = EventEmail.split(",");

						getRefresh();
						TEST_RESULT testResult =   Audit_Action.execute_addEventActionRule(driver, sptEARuleName[0], sptEARuleDesc[0], sptEARuleType[0], sptEARuleUser[0], sptEARuleRiskLevel[0], sptEARuleOpe[0], sptEARuleEmail[0], Prerequisites);
						System.out.println("Add test result is:"+testResult);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptEARuleName[0],0);

							if(a[0]==1)
							{
								TEST_RESULT getRes=Audit_Action.execute_modifyEventActionRule(driver, a[1], getTablePath, sptEARuleName[1], sptEARuleDesc[1], sptEARuleType[1], sptEARuleUser[1], sptEARuleRiskLevel[1], sptEARuleOpe[1], sptEARuleEmail[1], EventOperationDeselect);
								Thread.sleep(5000);
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_SUCCESS)
								{
									int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,sptEARuleName[1],0);

									if(a1[0]==1)
									{
										TEST_RESULT chkModify=Audit_Action.execute_AfterModifyEventActionRule(driver, a1[1], getTablePath, sptEARuleName[1], sptEARuleDesc[1], sptEARuleType[1], sptEARuleUser[1], sptEARuleRiskLevel[1], sptEARuleOpe[1], sptEARuleEmail[1], EventOperationDeselect);
										Thread.sleep(3000);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a1[1]);
											Thread.sleep(3000);
											Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Pass", "Modify - Success",iRow, 13,14);
										}
										else
										{
											Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a1[1]);
											Thread.sleep(3000);
											Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 13,14);
										}
									}
									else
									{
										int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptEARuleName[0],0);
										if(a2[0]==1)
										{
											Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a2[1]);
										}
										Thread.sleep(3000);
										Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
										ExcelUtils.setCellData("Fail", "After modify client not found in list",iRow, 13,14);
										Assert.fail();
									}
								}
								else
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptEARuleName[0],0);
									if(a2[0]==1)
									{
										Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a2[1]);
									}
									Thread.sleep(3000);
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);

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
					for(int i=0;i<sptEARuleName.length;i++)
					{
						int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptEARuleName[i],1);
						if(a[0]==1)
						{
							Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
						}
					}
					Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);

					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + EARuleName + "," + EARuleDesc + "," +EventType + "," + EventUser + "," + EventRiskLevel + "," +EventOperation + "," + EventEmail + "," + Prerequisites);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=8)
	public void modifyEventActionRule_Invalid() 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		String[] sptEARuleName=null;
		
		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				EARuleName=ExcelUtils.getCellData(iRow, 4);
				EARuleDesc=ExcelUtils.getCellData(iRow, 5);
				EventType=ExcelUtils.getCellData(iRow, 6);	
				EventUser=ExcelUtils.getCellData(iRow, 7);	
				EventRiskLevel=ExcelUtils.getCellData(iRow, 8);	
				EventOperation=ExcelUtils.getCellData(iRow, 9);	
				EventOperationDeselect=ExcelUtils.getCellData(iRow, 10);
				EventEmail=ExcelUtils.getCellData(iRow, 11);
				Prerequisites=ExcelUtils.getCellData(iRow, 12);

				if(MethodName.equalsIgnoreCase("modifyEventActionRule_Invalid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptEARuleName = EARuleName.split(",");
						String[] sptEARuleDesc = EARuleDesc.split(",");
						String[] sptEARuleType = EventType.split(",");
						String[] sptEARuleUser = EventUser.split(",");
						String[] sptEARuleRiskLevel = EventRiskLevel.split(",");
						String[] sptEARuleOpe= EventOperation.split(",");
						String[] sptEARuleEmail = EventEmail.split(",");

						getRefresh();
						TEST_RESULT testResult =   Audit_Action.execute_addEventActionRule(driver, sptEARuleName[0], sptEARuleDesc[0], sptEARuleType[0], sptEARuleUser[0], sptEARuleRiskLevel[0], sptEARuleOpe[0], sptEARuleEmail[0], Prerequisites);
						System.out.println("Add test result is:"+testResult);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 13,14);
							didAnyMethodFail = true;
							break;

						case RESULT_SUCCESS:

							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptEARuleName[0],0);

							if(a[0]==1)
							{
								TEST_RESULT getRes=Audit_Action.execute_modifyEventActionRule(driver, a[1], getTablePath, sptEARuleName[1], sptEARuleDesc[1], sptEARuleType[1], sptEARuleUser[1], sptEARuleRiskLevel[1], sptEARuleOpe[1], sptEARuleEmail[1],EventOperationDeselect);
								Thread.sleep(5000);	 
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_FAILURE)
								{	 
									int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,sptEARuleName[0],0);
									if(a1[0]==1)
									{

										TEST_RESULT chkModify=Audit_Action.execute_AfterModifyEventActionRule(driver, a1[1], getTablePath, sptEARuleName[0], sptEARuleDesc[0], sptEARuleType[0], sptEARuleUser[0], sptEARuleRiskLevel[0], sptEARuleOpe[0], sptEARuleEmail[0],EventOperationDeselect);
										Thread.sleep(3000);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a1[1]);
											Thread.sleep(3000);
											Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Pass", "Modify Invalid - Success",iRow, 13,14);
										}
										else
										{
											Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a1[1]);
											Thread.sleep(3000);
											Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 13,14);
											Assert.fail();
										}
									}
								}
								else
								{
									int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath,sptEARuleName[1],0);

									for(int k=1;k<=i[0];k++)
									{
										getRefresh();
										int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptEARuleName[1],0);

										if(a2[0]==1)
										{
											Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a2[1]);
										}
										else
										{
											System.out.println("No System User found to delete");
										}
									}													 
									Thread.sleep(3000);
									Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);
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
					for(int i=0;i<sptEARuleName.length;i++)
					{
						int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptEARuleName[i],1);
						if(a[0]==1)
						{
							Audit_Action.execute_deleteEventActionRule(driver,getTablePath,a[1]);
						}
					}
					Audit_Action.execute_deleteEventActionRulePrerequisites(driver, Prerequisites);

					ExcelUtils.setCellData("Error","",iRow, 13,14);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + EARuleName + "," + EARuleDesc + "," +EventType + "," + EventUser + "," + EventRiskLevel + "," +EventOperation + "," + EventEmail + "," + Prerequisites);
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
		Home_Page.lnk_Audit(driver).click();
		EventConfig_Page.lnk_EventConfig(driver).click();
		Thread.sleep(3000);
	}


}
