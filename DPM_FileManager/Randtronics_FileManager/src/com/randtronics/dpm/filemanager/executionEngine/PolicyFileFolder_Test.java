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
import com.randtronics.dpm.filemanager.pageObjects.PolicyFileFolder_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class PolicyFileFolder_Test extends ChromeTest
{
	public static int noofRow;
	public String MethodName,ExecutionMode,DeviceName,DeviceDesc,IdentityType,IdentityName,cFileFolder,cFileFolderList,
	acFileFolder,acFileFolderList,FileRightsName,FileRightsDesc,frIdentityType,frfrIdentityname,frApplication,frOperation,
	EncryptKey,EncryptIdentityType,EncryptIdentityName,TrustApplicationList,Offline,AgentPasswordModification,OfflinePeriod,
	Agentpassword,ConfirmAgentPassword,UserPolicyLock,StrictOnlineMode,MigrationTool,MigrationToolPwd,MigrationToolCnfPwd,
	Prerequisite1,Prerequisite2,Prerequisite3;
	
	public static RepositoryParser parser=new RepositoryParser();
	String getTablePath="";

	  @Test(priority=0)
	  @Parameters({"username","password"})
	  public void signIn(String username,String password) 
	  {
		  try
		  {
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_Policy(driver).click();
			PolicyFileFolder_Page.lnk_FileFolderPolicyTab(driver).click();
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_PolicyFileFolder);
			parser.RepositoryParser(Constants.path_ObjectRepo);
			getTablePath=parser.prop.getProperty("FileFolder_TablePath");
		  }
		  catch(Exception e)
		  {
			  Assert.fail("Sign In Error");
		  }
	  }
	  
	  @Test(priority=1)
	  public void addPolicyFileFolder_Valid() throws Exception 
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
				  DeviceDesc=ExcelUtils.getCellData(iRow, 5);
				  IdentityType=ExcelUtils.getCellData(iRow, 6);
				  IdentityName=ExcelUtils.getCellData(iRow, 7);
				  cFileFolder=ExcelUtils.getCellData(iRow, 8);
				  cFileFolderList=ExcelUtils.getCellData(iRow, 9);
				  acFileFolder=ExcelUtils.getCellData(iRow, 10);
				  acFileFolderList=ExcelUtils.getCellData(iRow, 11);
				  FileRightsName=ExcelUtils.getCellData(iRow, 12);
				  FileRightsDesc=ExcelUtils.getCellData(iRow, 13);
				  frIdentityType=ExcelUtils.getCellData(iRow, 14);
				  frfrIdentityname=ExcelUtils.getCellData(iRow, 15);
				  frApplication=ExcelUtils.getCellData(iRow, 16);
				  frOperation=ExcelUtils.getCellData(iRow, 17);
				  EncryptKey=ExcelUtils.getCellData(iRow, 18);
				  EncryptIdentityType=ExcelUtils.getCellData(iRow, 19);
				  EncryptIdentityName=ExcelUtils.getCellData(iRow, 20);
				  TrustApplicationList=ExcelUtils.getCellData(iRow, 21);
				  Offline=ExcelUtils.getCellData(iRow, 22);
				  AgentPasswordModification=ExcelUtils.getCellData(iRow, 23);
				  OfflinePeriod=ExcelUtils.getCellData(iRow, 24);
				  Agentpassword=ExcelUtils.getCellData(iRow, 25);
				  ConfirmAgentPassword=ExcelUtils.getCellData(iRow, 26);
				  UserPolicyLock=ExcelUtils.getCellData(iRow, 27);
				  StrictOnlineMode=ExcelUtils.getCellData(iRow, 28);
				  MigrationTool=ExcelUtils.getCellData(iRow, 29);
				  MigrationToolPwd=ExcelUtils.getCellData(iRow, 30);
				  MigrationToolCnfPwd=ExcelUtils.getCellData(iRow, 31);
				  Prerequisite1=ExcelUtils.getCellData(iRow, 32);
				  Prerequisite2=ExcelUtils.getCellData(iRow, 33);
				  Prerequisite3=ExcelUtils.getCellData(iRow, 34);

				  if(MethodName.equalsIgnoreCase("addPolicyFileFolder_Valid"))
				  {
					  if(ExecutionMode.equalsIgnoreCase("Y"))
					  {
						  getRefresh();
						  TEST_RESULT testResult = Policy_Action.execute_addFileFolder(driver, DeviceName, DeviceDesc,Prerequisite1,Prerequisite2,Prerequisite3);
						  switch (testResult) 
						  {
							  case RESULT_FAILURE:
								  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
								  ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 35,36);
								  didAnyMethodFail = true;
								  break;
							  case RESULT_ERROR:
								  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
								  ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 35,36);
								  didAnyMethodFail = true;
								  break;
							  case RESULT_SUCCESS:
								 Boolean chk=PolicyFileFolder_Page.btn_AddEncryption(driver).isEnabled();
								 if(chk==true)
								 {
									 boolean chkEncryption=Policy_Action.execute_addEncryption(driver, IdentityType, IdentityName, cFileFolder, cFileFolderList);
									 if(chkEncryption==true)
									 {
										 PolicyFileFolder_Page.lnk_AccessControl(driver).click();
										 boolean chkAccessControl=Policy_Action.execute_addAccessControl(driver, acFileFolder, acFileFolderList);
										 if(chkAccessControl==true)
										 {
											 boolean chkacFileRights=Policy_Action.execute_addFileRights(driver, FileRightsName,FileRightsDesc,frIdentityType,frfrIdentityname,frApplication,frOperation);
											 if(chkacFileRights==true)
											 {
												 PolicyFileFolder_Page.lnk_KeySharing(driver).click();
												 boolean chkKeySharing=Policy_Action.execute_addKeySharing(driver,EncryptKey,EncryptIdentityType,EncryptIdentityName);
												 if(chkKeySharing==true)
												 {
													 PolicyFileFolder_Page.lnk_Application(driver).click();
													 boolean chkApplication=Policy_Action.execute_addApplication(driver,TrustApplicationList);
													 if(chkApplication==true)
													 {
														 PolicyFileFolder_Page.lnk_Offline(driver).click();
														 boolean chkOfflineSafeguard=Policy_Action.execute_addOfflineSafeguard(driver,Offline,AgentPasswordModification,OfflinePeriod,
																	Agentpassword,ConfirmAgentPassword,UserPolicyLock,StrictOnlineMode,MigrationTool,MigrationToolPwd,MigrationToolCnfPwd);
														 if(chkOfflineSafeguard==true)
														 {
															 PolicyFileFolder_Page.btn_Cancel(driver).click();
															 int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
															 if(a[0]==1)
															 {
																 Policy_Action.execute_deleteFileFolder(driver, a[1],getTablePath);
																 Thread.sleep(5000);
																 Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
																 ExcelUtils.setCellData("Pass", "Device Details Updated",iRow, 35,36);
															 }
															 else
															 {
																 ExcelUtils.setCellData("Fail", "Device not found in the list",iRow, 35,36);
															 }
														 }
														 else
														 {
															 ExcelUtils.setCellData("Fail", "Device Details Not Updated",iRow, 35,36);
														 }
													 }
													 else
													 {
														 ExcelUtils.setCellData("Fail", "Application Not Added",iRow, 35,36);
													 }
												 }
												 else
												 {
													 ExcelUtils.setCellData("Fail", "Encryption Key Sharing Not Added",iRow, 35,36);
												 }
											 }
											 else
											 {
												 ExcelUtils.setCellData("Fail", "Access Control File Rights Rule Not Added",iRow, 35,36);
											 }
										 }
										 else
										 {
											 ExcelUtils.setCellData("Fail", "Access Control Security File Folder List Not Added",iRow, 35,36);
										 }
									 }
									 else
									 {
										 ExcelUtils.setCellData("Fail", "Encryption Security File Folder List Not Added",iRow, 35,36);
									 }
								 }
								 else
								 {
									 ExcelUtils.setCellData("Fail", "Encryption Add Button Disabled",iRow, 35,36);
								 }
								 /* int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
								  if(a[0]==1)
								  {
									  boolean getResult=Policy_Action.execute_AfterAddFileFolder(driver, a[1], getTablePath, DeviceName, DeviceDesc);
									  Thread.sleep(5000);
									  if(getResult==true)
									  {
										  Policy_Action.execute_deleteFileFolder(driver, a[1],getTablePath);
										  ExcelUtils.setCellData("Pass", "File Folder added successfully",iRow, 35,36);
									  }
									  else
									  {
										  ExcelUtils.setCellData("Fail", "Added details not matched",iRow, 35,36);
										  Assert.fail();
									  }
								  }
								  else
								  {
									  ExcelUtils.setCellData("Fail", "After add, Device name not listed",iRow, 35,36);
									  Assert.fail();
								  }
								  break;*/
						  }
					  }
					  else
					  {
						  ExcelUtils.setCellData("Skipped", "",iRow, 35,36);
					  }
				  }
			  }
			  catch(Exception e)
			  {
				  try
				  {
					  int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
					  if(a[0]==1)
					  {
						  Policy_Action.execute_deleteFileFolder(driver, a[1],getTablePath);
					  }
					  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);

					  ExcelUtils.setCellData("Error","",iRow, 35,36);
				  }
				  catch(Exception ex)
				  {
					  System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				  }
				  Assert.fail("Failed " + iRow + "With Parameters:" + DeviceName + "," + DeviceDesc);
			  }
		  }
		  if(didAnyMethodFail == true)
			  Assert.fail();
	  }
	  
	  
		@Test(priority=2)
		public void addPolicyFileFolder_Invalid()
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
					  DeviceDesc=ExcelUtils.getCellData(iRow, 5);
					  IdentityType=ExcelUtils.getCellData(iRow, 6);
					  IdentityName=ExcelUtils.getCellData(iRow, 7);
					  cFileFolder=ExcelUtils.getCellData(iRow, 8);
					  cFileFolderList=ExcelUtils.getCellData(iRow, 9);
					  acFileFolder=ExcelUtils.getCellData(iRow, 10);
					  acFileFolderList=ExcelUtils.getCellData(iRow, 11);
					  FileRightsName=ExcelUtils.getCellData(iRow, 12);
					  FileRightsDesc=ExcelUtils.getCellData(iRow, 13);
					  frIdentityType=ExcelUtils.getCellData(iRow, 14);
					  frfrIdentityname=ExcelUtils.getCellData(iRow, 15);
					  frApplication=ExcelUtils.getCellData(iRow, 16);
					  frOperation=ExcelUtils.getCellData(iRow, 17);
					  EncryptKey=ExcelUtils.getCellData(iRow, 18);
					  EncryptIdentityType=ExcelUtils.getCellData(iRow, 19);
					  EncryptIdentityName=ExcelUtils.getCellData(iRow, 20);
					  TrustApplicationList=ExcelUtils.getCellData(iRow, 21);
					  Offline=ExcelUtils.getCellData(iRow, 22);
					  AgentPasswordModification=ExcelUtils.getCellData(iRow, 23);
					  OfflinePeriod=ExcelUtils.getCellData(iRow, 24);
					  Agentpassword=ExcelUtils.getCellData(iRow, 25);
					  ConfirmAgentPassword=ExcelUtils.getCellData(iRow, 26);
					  UserPolicyLock=ExcelUtils.getCellData(iRow, 27);
					  StrictOnlineMode=ExcelUtils.getCellData(iRow, 28);
					  MigrationTool=ExcelUtils.getCellData(iRow, 29);
					  MigrationToolPwd=ExcelUtils.getCellData(iRow, 30);
					  MigrationToolCnfPwd=ExcelUtils.getCellData(iRow, 31);
					  Prerequisite1=ExcelUtils.getCellData(iRow, 32);
					  Prerequisite2=ExcelUtils.getCellData(iRow, 33);
					  Prerequisite3=ExcelUtils.getCellData(iRow, 34);

					  if(MethodName.equalsIgnoreCase("addPolicyFileFolder_Invalid"))
					  {
						  if(ExecutionMode.equalsIgnoreCase("Y"))
						  {	
							getRefresh(); 
							int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",DeviceName,0);
							System.out.println("i[0]:"+i[0]);
							System.out.println("i[1]:"+i[1]);
							TEST_RESULT testResult = Policy_Action.execute_addFileFolder(driver, DeviceName, DeviceDesc,Prerequisite1,Prerequisite2,Prerequisite3);
							switch (testResult) 
							{
							case RESULT_SUCCESS:
								int b[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
								System.out.println("b[0]:"+b[0]);
								if(b[0]==1)
								{
								   Policy_Action.execute_deleteFileFolder(driver, b[1],getTablePath);
								   Thread.sleep(5000);
								   Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);

								    ExcelUtils.setCellData("Fail", "FileFolder/DB Policy Added",iRow, 35,36);
								    didAnyMethodFail = true;
								}
								break;
							case RESULT_ERROR:
								Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
								ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 35,36);
								didAnyMethodFail = true;
								break;
							case RESULT_FAILURE:
								int i1[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",DeviceName,0);
								System.out.println("i1[0]:"+i1[0]);
								System.out.println("i1[1]:"+i1[1]);
								if(i[0]==i1[0])
								{
									Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
									ExcelUtils.setCellData("Pass", "FileFolder/DB Policy not added",iRow, 35,36);
								}
								else
								{
									for(int k=1;k<=i[0];k++)
									{
										getRefresh();
										int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);

										if(a2[0]==1)
										{
											   Policy_Action.execute_deleteFileFolder(driver, a2[1],getTablePath);
										}
										else
										{
											System.out.println("No FileFolder/DB Policy found to delete");
										}
									}
									Thread.sleep(4000);
									Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
									ExcelUtils.setCellData("Fail", "FileFolder/DB Policy Added",iRow, 35,36);
									Assert.fail();
								}
								break;
							}
						}
						else
						{
							ExcelUtils.setCellData("Skipped", "",iRow, 35,36);
						}
					}
				}
				catch(Exception e)
				{
					try
					{
						  int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
						  if(a[0]==1)
						  {
							  Policy_Action.execute_deleteFileFolder(driver, a[1],getTablePath);
						  }
						  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);

						  ExcelUtils.setCellData("Error","",iRow, 35,36);
					  }
					  catch(Exception ex)
					  {
						  System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
					  }
					  Assert.fail("Failed " + iRow + "With Parameters:" + DeviceName + "," + DeviceDesc);
				}
			}
			if(didAnyMethodFail == true)
				Assert.fail();
		}
		
		@Test(priority=3)
		public void deletePolicyFileFolder_CheckDialog() 
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
					  DeviceDesc=ExcelUtils.getCellData(iRow, 5);
					  IdentityType=ExcelUtils.getCellData(iRow, 6);
					  IdentityName=ExcelUtils.getCellData(iRow, 7);
					  cFileFolder=ExcelUtils.getCellData(iRow, 8);
					  cFileFolderList=ExcelUtils.getCellData(iRow, 9);
					  acFileFolder=ExcelUtils.getCellData(iRow, 10);
					  acFileFolderList=ExcelUtils.getCellData(iRow, 11);
					  FileRightsName=ExcelUtils.getCellData(iRow, 12);
					  FileRightsDesc=ExcelUtils.getCellData(iRow, 13);
					  frIdentityType=ExcelUtils.getCellData(iRow, 14);
					  frfrIdentityname=ExcelUtils.getCellData(iRow, 15);
					  frApplication=ExcelUtils.getCellData(iRow, 16);
					  frOperation=ExcelUtils.getCellData(iRow, 17);
					  EncryptKey=ExcelUtils.getCellData(iRow, 18);
					  EncryptIdentityType=ExcelUtils.getCellData(iRow, 19);
					  EncryptIdentityName=ExcelUtils.getCellData(iRow, 20);
					  TrustApplicationList=ExcelUtils.getCellData(iRow, 21);
					  Offline=ExcelUtils.getCellData(iRow, 22);
					  AgentPasswordModification=ExcelUtils.getCellData(iRow, 23);
					  OfflinePeriod=ExcelUtils.getCellData(iRow, 24);
					  Agentpassword=ExcelUtils.getCellData(iRow, 25);
					  ConfirmAgentPassword=ExcelUtils.getCellData(iRow, 26);
					  UserPolicyLock=ExcelUtils.getCellData(iRow, 27);
					  StrictOnlineMode=ExcelUtils.getCellData(iRow, 28);
					  MigrationTool=ExcelUtils.getCellData(iRow, 29);
					  MigrationToolPwd=ExcelUtils.getCellData(iRow, 30);
					  MigrationToolCnfPwd=ExcelUtils.getCellData(iRow, 31);
					  Prerequisite1=ExcelUtils.getCellData(iRow, 32);
					  Prerequisite2=ExcelUtils.getCellData(iRow, 33);
					  Prerequisite3=ExcelUtils.getCellData(iRow, 34);

					  if(MethodName.equalsIgnoreCase("deletePolicyFileFolder_CheckDialog"))
					  {
						  if(ExecutionMode.equalsIgnoreCase("Y"))
						  {	
							getRefresh(); 
							
							TEST_RESULT testResult = Policy_Action.execute_addFileFolder(driver, DeviceName, DeviceDesc,Prerequisite1,Prerequisite2,Prerequisite3);
							switch (testResult) 
							{
							  case RESULT_FAILURE:
								  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
								  ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 35,36);
								  didAnyMethodFail = true;
								  break;
							  case RESULT_ERROR:
								  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
								  ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 35,36);
								  didAnyMethodFail = true;
								  break;
							case RESULT_SUCCESS:
								int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
								if(a[0]==1)
								{
									driver.findElement(By.xpath(getTablePath+"/tr["+(a[1]+1)+"]/td[19]/div")).click();						
									boolean checkDialog=driver.findElements(By.xpath("html/body/div[84]")).size() != 0;
									if(checkDialog==true)
									{
										driver.findElement(By.linkText("Yes")).click();
										  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
										ExcelUtils.setCellData("Pass", "Delete Confirmation Dialog Present",iRow, 35,36);
									}
									else
									{
										  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
										ExcelUtils.setCellData("Fail", "Delete Confirmation Dialog Not Found",iRow, 35,36);
										Assert.fail();
									}	  
								}
								else
								{
									  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
									ExcelUtils.setCellData("Fail", "FileFolder/DB Policy Not Found",iRow, 35,36);
									Assert.fail();
								}
								break;
							}
						}
						else
						{
							ExcelUtils.setCellData("Skipped", "",iRow, 35,36);
						}	  
					}
				}
				catch(Exception e)
				{
					try
					{
						  int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
						  if(a[0]==1)
						  {
							  Policy_Action.execute_deleteFileFolder(driver, a[1],getTablePath);
						  }
						  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);

						  ExcelUtils.setCellData("Error","",iRow, 35,36);
					  }
					  catch(Exception ex)
					  {
						  System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
					  }
					  Assert.fail("Failed " + iRow + "With Parameters:" + DeviceName + "," + DeviceDesc);
				}
			}
			if(didAnyMethodFail == true)
				Assert.fail();
		}
		
		@Test(priority=4)
		public void deletePolicyFileFolder_Yes()
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
					  DeviceDesc=ExcelUtils.getCellData(iRow, 5);
					  IdentityType=ExcelUtils.getCellData(iRow, 6);
					  IdentityName=ExcelUtils.getCellData(iRow, 7);
					  cFileFolder=ExcelUtils.getCellData(iRow, 8);
					  cFileFolderList=ExcelUtils.getCellData(iRow, 9);
					  acFileFolder=ExcelUtils.getCellData(iRow, 10);
					  acFileFolderList=ExcelUtils.getCellData(iRow, 11);
					  FileRightsName=ExcelUtils.getCellData(iRow, 12);
					  FileRightsDesc=ExcelUtils.getCellData(iRow, 13);
					  frIdentityType=ExcelUtils.getCellData(iRow, 14);
					  frfrIdentityname=ExcelUtils.getCellData(iRow, 15);
					  frApplication=ExcelUtils.getCellData(iRow, 16);
					  frOperation=ExcelUtils.getCellData(iRow, 17);
					  EncryptKey=ExcelUtils.getCellData(iRow, 18);
					  EncryptIdentityType=ExcelUtils.getCellData(iRow, 19);
					  EncryptIdentityName=ExcelUtils.getCellData(iRow, 20);
					  TrustApplicationList=ExcelUtils.getCellData(iRow, 21);
					  Offline=ExcelUtils.getCellData(iRow, 22);
					  AgentPasswordModification=ExcelUtils.getCellData(iRow, 23);
					  OfflinePeriod=ExcelUtils.getCellData(iRow, 24);
					  Agentpassword=ExcelUtils.getCellData(iRow, 25);
					  ConfirmAgentPassword=ExcelUtils.getCellData(iRow, 26);
					  UserPolicyLock=ExcelUtils.getCellData(iRow, 27);
					  StrictOnlineMode=ExcelUtils.getCellData(iRow, 28);
					  MigrationTool=ExcelUtils.getCellData(iRow, 29);
					  MigrationToolPwd=ExcelUtils.getCellData(iRow, 30);
					  MigrationToolCnfPwd=ExcelUtils.getCellData(iRow, 31);
					  Prerequisite1=ExcelUtils.getCellData(iRow, 32);
					  Prerequisite2=ExcelUtils.getCellData(iRow, 33);
					  Prerequisite3=ExcelUtils.getCellData(iRow, 34);

					  if(MethodName.equalsIgnoreCase("deletePolicyFileFolder_Yes"))
					  {
						  if(ExecutionMode.equalsIgnoreCase("Y"))
						  {	
							getRefresh(); 
							
							TEST_RESULT testResult = Policy_Action.execute_addFileFolder(driver, DeviceName, DeviceDesc,Prerequisite1,Prerequisite2,Prerequisite3);
							switch (testResult) 
							{
							  case RESULT_FAILURE:
								  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
								  ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 35,36);
								  didAnyMethodFail = true;
								  break;
							  case RESULT_ERROR:
								  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
								  ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 35,36);
								  didAnyMethodFail = true;
								  break;
							case RESULT_SUCCESS:

								int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
								if(a[0]==1)
								{
									Policy_Action.execute_deleteFileFolder(driver, a[1],getTablePath);
									Thread.sleep(5000);
									int a1[]=SysMgmt_Action.searchForData(driver, getTablePath,DeviceName,0);
									if(a1[0]==0)
									{
										  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
										ExcelUtils.setCellData("Pass", "FileFolder/DB Policy Deleted",iRow, 35,36);
									}
									else
									{
										Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
										ExcelUtils.setCellData("Fail", "FileFolder/DB Policy Not Deleted",iRow, 35,36);
										Assert.fail();
									}					
								}
								else
								{
									Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
									ExcelUtils.setCellData("Fail", "FileFolder/DB Policy Not Found",iRow, 35,36);
									Assert.fail();
								}
								break;
							}
						}
						else
						{
							ExcelUtils.setCellData("Skipped", "",iRow, 35,36);
						}	  
					}
				}
				catch(Exception e)
				{
					try
					{
						  int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
						  if(a[0]==1)
						  {
							  Policy_Action.execute_deleteFileFolder(driver, a[1],getTablePath);
						  }
						  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);

						  ExcelUtils.setCellData("Error","",iRow, 35,36);
					  }
					  catch(Exception ex)
					  {
						  System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
					  }
					  Assert.fail("Failed " + iRow + "With Parameters:" + DeviceName + "," + DeviceDesc);
				}
			}
			if(didAnyMethodFail == true)
				Assert.fail();
		}

		@Test(priority=5)
		public void deletePolicyFileFolder_No() 
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
					  DeviceDesc=ExcelUtils.getCellData(iRow, 5);
					  IdentityType=ExcelUtils.getCellData(iRow, 6);
					  IdentityName=ExcelUtils.getCellData(iRow, 7);
					  cFileFolder=ExcelUtils.getCellData(iRow, 8);
					  cFileFolderList=ExcelUtils.getCellData(iRow, 9);
					  acFileFolder=ExcelUtils.getCellData(iRow, 10);
					  acFileFolderList=ExcelUtils.getCellData(iRow, 11);
					  FileRightsName=ExcelUtils.getCellData(iRow, 12);
					  FileRightsDesc=ExcelUtils.getCellData(iRow, 13);
					  frIdentityType=ExcelUtils.getCellData(iRow, 14);
					  frfrIdentityname=ExcelUtils.getCellData(iRow, 15);
					  frApplication=ExcelUtils.getCellData(iRow, 16);
					  frOperation=ExcelUtils.getCellData(iRow, 17);
					  EncryptKey=ExcelUtils.getCellData(iRow, 18);
					  EncryptIdentityType=ExcelUtils.getCellData(iRow, 19);
					  EncryptIdentityName=ExcelUtils.getCellData(iRow, 20);
					  TrustApplicationList=ExcelUtils.getCellData(iRow, 21);
					  Offline=ExcelUtils.getCellData(iRow, 22);
					  AgentPasswordModification=ExcelUtils.getCellData(iRow, 23);
					  OfflinePeriod=ExcelUtils.getCellData(iRow, 24);
					  Agentpassword=ExcelUtils.getCellData(iRow, 25);
					  ConfirmAgentPassword=ExcelUtils.getCellData(iRow, 26);
					  UserPolicyLock=ExcelUtils.getCellData(iRow, 27);
					  StrictOnlineMode=ExcelUtils.getCellData(iRow, 28);
					  MigrationTool=ExcelUtils.getCellData(iRow, 29);
					  MigrationToolPwd=ExcelUtils.getCellData(iRow, 30);
					  MigrationToolCnfPwd=ExcelUtils.getCellData(iRow, 31);
					  Prerequisite1=ExcelUtils.getCellData(iRow, 32);
					  Prerequisite2=ExcelUtils.getCellData(iRow, 33);
					  Prerequisite3=ExcelUtils.getCellData(iRow, 34);

					  if(MethodName.equalsIgnoreCase("deletePolicyFileFolder_No"))
					  {
						  if(ExecutionMode.equalsIgnoreCase("Y"))
						  {	
							getRefresh(); 
							
							TEST_RESULT testResult = Policy_Action.execute_addFileFolder(driver, DeviceName, DeviceDesc,Prerequisite1,Prerequisite2,Prerequisite3);
							switch (testResult) 
							{
							  case RESULT_FAILURE:
								  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
								  ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 35,36);
								  didAnyMethodFail = true;
								  break;
							  case RESULT_ERROR:
								  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
								  ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 35,36);
								  didAnyMethodFail = true;
								  break;
							case RESULT_SUCCESS:

								int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
								if(a[0]==1)
								{
									driver.findElement(By.xpath(getTablePath+"/tr["+(a[1]+1)+"]/td[19]/div")).click();						
									driver.findElement(By.linkText("No")).click();
									Thread.sleep(5000);
									int a1[]=SysMgmt_Action.searchForData(driver, getTablePath,DeviceName,0);
									if(a1[0]==1)
									{
										  Policy_Action.execute_deleteFileFolder(driver, a[1],getTablePath);
										Thread.sleep(5000);
										  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
										ExcelUtils.setCellData("Pass", "FileFolder/DB Policy Not Deleted",iRow, 35,36);
									}
									else
									{
										  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
										ExcelUtils.setCellData("Fail", "FileFolder/DB Policy Deleted",iRow, 35,36);
										Assert.fail();
									}					
								}
								else
								{
									  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);
									ExcelUtils.setCellData("Fail", "FileFolder/DB Policy Not Found",iRow, 35,36);
									Assert.fail();
								}
								break;
							}
						}
						else
						{
							ExcelUtils.setCellData("Skipped", "",iRow, 35,36);
						}	  
					}
				}
				catch(Exception e)
				{
					try
					{
						  int a[]=SysMgmt_Action.searchForData(driver,getTablePath,DeviceName,0);
						  if(a[0]==1)
						  {
							  Policy_Action.execute_deleteFileFolder(driver, a[1],getTablePath);
						  }
						  Policy_Action.execute_deletePolicyFileFolderPrerequisites(driver, Prerequisite1,Prerequisite2,Prerequisite3);

						  ExcelUtils.setCellData("Error","",iRow, 35,36);
					  }
					  catch(Exception ex)
					  {
						  System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
					  }
					  Assert.fail("Failed " + iRow + "With Parameters:" + DeviceName + "," + DeviceDesc);
				}
			}
			if(didAnyMethodFail == true)
				Assert.fail();
		}



	  
	  @Test(priority=11)
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
			  Home_Page.lnk_Policy(driver).click();
			  PolicyFileFolder_Page.lnk_FileFolderPolicyTab(driver).click();
			  Thread.sleep(3000);
		  }
		  catch(Exception e)
		  {
			  
		  }
	  }
}
