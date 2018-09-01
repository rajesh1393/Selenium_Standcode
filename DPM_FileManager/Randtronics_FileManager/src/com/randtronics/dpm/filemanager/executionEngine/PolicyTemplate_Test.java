package com.randtronics.dpm.filemanager.executionEngine;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Policy_Action;
import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.appModules.Policy_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.PolicyTemplate_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class PolicyTemplate_Test extends ChromeTest
{
	public static int noofRow;
	public String MethodName,ExecutionMode,TemplateNme, TemplateDesc,TemplateType,Encrypt_DeviceIP,IdentityType,IdentityName,cFileFolder,cFileFolderList,AC_DeviceIP,FileRightsName,acFileFolder,acFileFolderList,FileRightsDesc,frIdentityType,frfrIdentityname,frApplication,frOperation,UserIdentityName,UserIdentityType,TrustApplicationList,Offline,AgentPasswordModification,OfflinePeriod,Agentpassword,ConfirmAgentPassword,UserPolicyLock,StrictOnlineMode,MigrationTool,MigrationToolPwd,MigrationToolCnfPwd,prerequisite1,prerequisite2,prerequisite3;
	public static RepositoryParser parser=new RepositoryParser();
	String getTablepath_Policytemp="";

	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password) 
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_Policy(driver).click();
			PolicyTemplate_Page.lnk_PolicyTemp(driver).click();
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_PolicyTemplate);
			parser.RepositoryParser(Constants.path_ObjectRepo);
			getTablepath_Policytemp=parser.prop.getProperty("PolicyTemp_TablePath");
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}

	@Test(priority=1)
	public void addPolicyTemp_Valid() 
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
				TemplateNme=ExcelUtils.getCellData(iRow, 4);
				TemplateDesc=ExcelUtils.getCellData(iRow, 5);
				TemplateType=ExcelUtils.getCellData(iRow, 6);
				Encrypt_DeviceIP=ExcelUtils.getCellData(iRow, 7);
				IdentityType=ExcelUtils.getCellData(iRow, 8);
				IdentityName=ExcelUtils.getCellData(iRow, 9);
				cFileFolder=ExcelUtils.getCellData(iRow, 10);
				cFileFolderList=ExcelUtils.getCellData(iRow, 11);
				AC_DeviceIP=ExcelUtils.getCellData(iRow, 12);
				acFileFolder=ExcelUtils.getCellData(iRow, 13);
				acFileFolderList=ExcelUtils.getCellData(iRow, 14);
				FileRightsName=ExcelUtils.getCellData(iRow, 15);
				FileRightsDesc=ExcelUtils.getCellData(iRow, 16);
				frIdentityType=ExcelUtils.getCellData(iRow, 17);
				frfrIdentityname=ExcelUtils.getCellData(iRow, 18);
				frApplication=ExcelUtils.getCellData(iRow, 19);
				frOperation=ExcelUtils.getCellData(iRow, 20);				
				UserIdentityName=ExcelUtils.getCellData(iRow, 21);				
				UserIdentityType=ExcelUtils.getCellData(iRow, 22);
				TrustApplicationList=ExcelUtils.getCellData(iRow, 23);
				Offline=ExcelUtils.getCellData(iRow, 24);
				AgentPasswordModification=ExcelUtils.getCellData(iRow, 25);
				OfflinePeriod=ExcelUtils.getCellData(iRow, 26);
				Agentpassword=ExcelUtils.getCellData(iRow, 27);
				ConfirmAgentPassword=ExcelUtils.getCellData(iRow,28);
				UserPolicyLock=ExcelUtils.getCellData(iRow, 29);
				StrictOnlineMode=ExcelUtils.getCellData(iRow, 30);
				MigrationTool=ExcelUtils.getCellData(iRow, 31);
				MigrationToolPwd=ExcelUtils.getCellData(iRow, 32);
				MigrationToolCnfPwd=ExcelUtils.getCellData(iRow, 33);
				prerequisite1=ExcelUtils.getCellData(iRow, 34);
				prerequisite2=ExcelUtils.getCellData(iRow, 35);
				prerequisite3=ExcelUtils.getCellData(iRow, 36);

				if(MethodName.equalsIgnoreCase("addPolicyTemp_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh(); 
						Policy_Action.TEST_RESULT testResult =Policy_Action.execute_Addpolicytemp(driver, TemplateNme, TemplateDesc, TemplateType, prerequisite1,prerequisite2,prerequisite3);

						switch (testResult) 
						{						
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 37,38);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 37,38);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							Boolean chk=PolicyTemplate_Page.btn_AddEncrypt(driver).isEnabled();
							if(chk==true)
							{
								boolean chkEncryption=Policy_Action.execute_addEncryption(driver, Encrypt_DeviceIP, IdentityType, IdentityName, cFileFolder, cFileFolderList);
								if(chkEncryption==true)
								{
									PolicyTemplate_Page.lnk_AccessControl(driver).click();
									boolean chkAccessControl=Policy_Action.execute_addAccessControl(driver, AC_DeviceIP, acFileFolder, acFileFolderList);
									if(chkAccessControl==true)
									{
										boolean chkacFileRights=Policy_Action.execute_addFileRights(driver, FileRightsName,FileRightsDesc,frIdentityType,frfrIdentityname,frApplication,frOperation);
										if(chkacFileRights==true)
										{
											PolicyTemplate_Page.lnk_EncryptUserlst(driver).click();
											boolean chkUser=Policy_Action.execute_addEncryptUserlst(driver, UserIdentityType, UserIdentityName);
											if(chkUser==true)
											{
												PolicyTemplate_Page.lnk_Application(driver).click();
												boolean chkApplication=Policy_Action.execute_addApplication1(driver,TrustApplicationList);
												if(chkApplication==true)
												{
													PolicyTemplate_Page.lnk_Offline(driver).click();
													boolean chkOfflineSafeguard=Policy_Action.execute_addOfflineSafeguard1(driver,Offline,AgentPasswordModification,OfflinePeriod,
															Agentpassword,ConfirmAgentPassword,UserPolicyLock,StrictOnlineMode,MigrationTool,MigrationToolPwd,MigrationToolCnfPwd);
													if(chkOfflineSafeguard==true)
													{
														ExcelUtils.setCellData("Pass", "Device Details Updated",iRow, 37,38);
													}
													else
													{
														ExcelUtils.setCellData("Fail", "Device Details Not Updated",iRow, 37,38);
													}
												}
												else
												{
													ExcelUtils.setCellData("Fail", "Application Not Added",iRow, 37,38);
												}
											}
											else
											{
												ExcelUtils.setCellData("Fail", "Encryption User Not Added",iRow, 37,38);
											}
										}
										else
										{
											ExcelUtils.setCellData("Fail", "Access Control Security File Folder List Not Added",iRow, 37,38);
										}
									}
									else
									{
										ExcelUtils.setCellData("Fail", "Access Control File Rights Rule Not Added",iRow, 37,38);
									}
								}
								else
								{
									ExcelUtils.setCellData("Fail", "Encryption Security File Folder List Not Added",iRow, 37,38);
								}
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Encryption Add Button Disabled",iRow, 37,38);

							}
						}
					}
				}
				else
				{
					ExcelUtils.setCellData("Skipped", "",iRow, 37,38);
				}
			}

			catch(Exception e)
			{
				try
				{
					ExcelUtils.setCellData("Error","",iRow, 37,38);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + TemplateNme + "," + TemplateDesc + TemplateType);
			}

			if(didAnyMethodFail == true)
				Assert.fail();
		}

	}


	@Test(priority=3)
	public void deletePolicyTemp_CheckDialog() 
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
				TemplateNme=ExcelUtils.getCellData(iRow, 4);
				TemplateDesc=ExcelUtils.getCellData(iRow, 5);
				TemplateType=ExcelUtils.getCellData(iRow, 6);

				if(MethodName.equalsIgnoreCase("deletePolicyTemp_CheckDialog"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult = Policy_Action.execute_AddpolicytempAfterPre(driver, TemplateNme, TemplateDesc,TemplateType);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 10,11);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 10,11);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,getTablepath_Policytemp,TemplateNme,0);
							if(a[0]==1)
							{
								driver.findElement(By.xpath(getTablepath_Policytemp+"/tr["+(a[1]+1)+"]/td[18]/div")).click();						
								boolean checkDialog=driver.findElements(By.xpath("html/body/div[84]")).size() != 0;
								if(checkDialog==true)
								{
									driver.findElement(By.linkText("Yes")).click();
									ExcelUtils.setCellData("Pass", "Delete Confirmation Dialog Present",iRow, 10,11);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "Delete Confirmation Dialog Not Found",iRow, 10,11);
									Assert.fail();
								}	  
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Policy Template Name Not Found",iRow, 10,11);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 10,11);
					}	  
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,getTablepath_Policytemp,TemplateNme,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicytemplate(driver,getTablepath_Policytemp,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 10,11);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + TemplateNme + "," + TemplateDesc + TemplateType);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=4)
	public void deletePolicyAppTemp_Yes()
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
				TemplateNme=ExcelUtils.getCellData(iRow, 4);
				TemplateDesc=ExcelUtils.getCellData(iRow, 5);
				TemplateType=ExcelUtils.getCellData(iRow, 6);

				if(MethodName.equalsIgnoreCase("deletePolicyAppTemp_Yes"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_AddpolicytempAfterPre(driver, TemplateNme,TemplateDesc,TemplateType);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 10,11);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 10,11);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,getTablepath_Policytemp,TemplateNme,0);
							if(a[0]==1)
							{
								Policy_Action.execute_deletePolicytemplate(driver,getTablepath_Policytemp,a[1]);
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver,getTablepath_Policytemp,TemplateNme,0);
								if(a1[0]==0)
								{
									ExcelUtils.setCellData("Pass", "Policy Template Name Deleted",iRow, 10,11);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "Policy Template Not Deleted",iRow, 10,11);
									Assert.fail();
								}					
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Policy Template Name Not Found",iRow, 10,11);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 10,11);
					}	  
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,getTablepath_Policytemp,TemplateNme,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicytemplate(driver,getTablepath_Policytemp,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 10,11);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + TemplateNme + "," + TemplateDesc + TemplateType);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=5)
	public void deletePolicyAppTemp_No() 
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
				TemplateNme=ExcelUtils.getCellData(iRow, 4);
				TemplateDesc=ExcelUtils.getCellData(iRow, 5);
				TemplateType=ExcelUtils.getCellData(iRow, 6);

				if(MethodName.equalsIgnoreCase("deletePolicyAppTemp_No"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_AddpolicytempAfterPre(driver,TemplateNme,TemplateDesc,TemplateType);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 10,11);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 10,11);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,getTablepath_Policytemp,TemplateNme,0);
							if(a[0]==1)
							{
								driver.findElement(By.xpath(getTablepath_Policytemp+"/tr["+(a[1]+1)+"]/td[13]/div")).click();						
								driver.findElement(By.linkText("No")).click();
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver,getTablepath_Policytemp,TemplateNme,0);
								if(a1[0]==1)
								{
									Policy_Action.execute_deletePolicytemplate(driver,getTablepath_Policytemp,a1[1]);
									ExcelUtils.setCellData("Pass", "Policy Template Name Not Deleted",iRow, 10,11);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "Policy Template Name Deleted",iRow, 10,11);
									Assert.fail();
								}					
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Policy Template Name Not Found",iRow, 10,11);
								Assert.fail();
							}
							break;
						}
					}
					else
					{
						ExcelUtils.setCellData("Skipped", "",iRow, 10,11);
					}	  
				}
			}
			catch(Exception e)
			{
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,getTablepath_Policytemp,TemplateNme,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicytemplate(driver,getTablepath_Policytemp,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 10,11);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + TemplateNme + "," + TemplateDesc +  "," +TemplateType);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	public void getRefresh()
	{
		try
		{
			driver.navigate().refresh();
			Home_Page.lnk_Policy(driver).click();
			PolicyTemplate_Page.lnk_PolicyTemp(driver).click();
			Thread.sleep(3000);
		}
		catch(Exception e)
		{

		}
	}

	@Test(priority=8)
	public void signOut()
	{
		try
		{
			driver.navigate().refresh();
			Home_Page.lnk_LogOut(driver).click();
			PolicyTemplate_Page.lnk_PolicyTemp(driver).click();
		}
		catch(Exception e)
		{
			Assert.fail();	
		}
	}
}
