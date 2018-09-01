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
import com.randtronics.dpm.filemanager.pageObjects.SystemGroup_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class SystemGroup_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,SysGroupName,SysGroupDesc;
	public static RepositoryParser parser=new RepositoryParser();
	String getTablePath="";
	
	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password) throws Exception
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			Home_Page.lnk_SytemManagement(driver).click();
			SystemGroup_Page.lnk_SytemManagement_Group(driver).click();
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_SystemGroup);
			parser.RepositoryParser(Constants.path_ObjectRepo);
			getTablePath=parser.prop.getProperty("Group_Table_Path");
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}
	
	@Test(priority=1)
	public void addSysGroup_Valid() throws Exception
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
				SysGroupName=ExcelUtils.getCellData(iRow, 4);
				SysGroupDesc=ExcelUtils.getCellData(iRow, 5);	
			
				if(MethodName.equalsIgnoreCase("addSysGroup_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSysGroup(driver, SysGroupName, SysGroupDesc);
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
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
							if(a[0]==1)
							{
								TEST_RESULT result=SysMgmt_Action.execute_AfterAddSysGroup(driver,a[1],getTablePath,SysGroupName, SysGroupDesc);
								Thread.sleep(5000);
								if(result==TEST_RESULT.RESULT_SUCCESS)
								{
									SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Pass", "Group Added Successfully",iRow, 6,7);
								}
								else
								{
									SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "Group Not Added Successfully",iRow, 6,7);
									Assert.fail();
								}
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Group not found in the list",iRow, 6,7);
								Assert.fail();
							}
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysGroupName + "," + SysGroupDesc);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=2)
	public void addSysGroup_Invalid()
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
				SysGroupName=ExcelUtils.getCellData(iRow, 4);
				SysGroupDesc=ExcelUtils.getCellData(iRow, 5);	
			
				if(MethodName.equalsIgnoreCase("addSysGroup_Invalid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh(); 
						int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[2]",SysGroupName,1);
						System.out.println("i[0]:"+i[0]);
						System.out.println("i[1]:"+i[1]);
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSysGroup(driver, SysGroupName, SysGroupDesc);
						switch (testResult) 
						{
						case RESULT_SUCCESS:
							int b[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
							System.out.println("b[0]:"+b[0]);
							if(b[0]==1)
							{
								SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,b[1]);
								Thread.sleep(3000);
								ExcelUtils.setCellData("Fail", "Group Added",iRow, 6,7);
								didAnyMethodFail = true;
							}
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_FAILURE:

							int i1[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[2]",SysGroupName,1);
							System.out.println("i1[0]:"+i1[0]);
							System.out.println("i1[1]:"+i1[1]);
							if(i[0]==i1[0])
							{
								ExcelUtils.setCellData("Pass", "Group Not Added",iRow, 6,7);
							}
							else
							{
								for(int k=1;k<=i[0];k++)
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);

									if(a2[0]==1)
									{
										SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a2[1]);
									}
									else
									{
										System.out.println("No Group found to delete");
									}
								}
								Thread.sleep(4000);
								ExcelUtils.setCellData("Fail", "Group Added",iRow, 6,7);
								Assert.fail();
							}
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysGroupName + "," + SysGroupDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=3)
	public void deleteSysGroup_CheckDialog() throws Exception
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		parser.RepositoryParser(Constants.path_ObjectRepo);
		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				SysGroupName=ExcelUtils.getCellData(iRow, 4);
				SysGroupDesc=ExcelUtils.getCellData(iRow, 5);	
			
				if(MethodName.equalsIgnoreCase("deleteSysGroup_CheckDialog"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSysGroup(driver, SysGroupName, SysGroupDesc);
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
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
							if(a[0]==1)
							{
								driver.findElement(By.xpath(getTablePath+"/tr["+(a[1]+1)+"]/td[10]/div")).click();						
								boolean checkDialog=driver.findElements(By.xpath(parser.prop.getProperty("Delete_Dialog"))).size() != 0;
								if(checkDialog==true)
								{
									driver.findElement(By.linkText("Yes")).click();
									ExcelUtils.setCellData("Pass", "Delete Confirmation Dialog Present",iRow, 6,7);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "Delete Confirmation Dialog Not Found",iRow, 6,7);
									Assert.fail();
								}	  
							}
							else
							{
								ExcelUtils.setCellData("Fail", "System Role Not Found",iRow, 6,7);
								Assert.fail();
							}
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysGroupName + "," + SysGroupDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=4)
	public void deleteSysGroup_Yes()
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
				SysGroupName=ExcelUtils.getCellData(iRow, 4);
				SysGroupDesc=ExcelUtils.getCellData(iRow, 5);	
			
				if(MethodName.equalsIgnoreCase("deleteSysGroup_Yes"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSysGroup(driver, SysGroupName, SysGroupDesc);
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

							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
							if(a[0]==1)
							{
								SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
								if(a1[0]==0)
								{
									ExcelUtils.setCellData("Pass", "System Role Deleted",iRow, 6,7);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "System Role Not Deleted",iRow, 6,7);
									Assert.fail();
								}					
							}
							else
							{
								ExcelUtils.setCellData("Fail", "System Role Not Found",iRow, 6,7);
								Assert.fail();
							}
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysGroupName + "," + SysGroupDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=5)
	public void deleteSysGroup_No() 
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
				SysGroupName=ExcelUtils.getCellData(iRow, 4);
				SysGroupDesc=ExcelUtils.getCellData(iRow, 5);	
			
				if(MethodName.equalsIgnoreCase("deleteSysGroup_No"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSysGroup(driver, SysGroupName, SysGroupDesc);
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

							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
							if(a[0]==1)
							{
								driver.findElement(By.xpath(getTablePath+"/tr["+(a[1]+1)+"]/td[10]/div")).click();						
								driver.findElement(By.linkText("No")).click();
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
								if(a1[0]==1)
								{
									SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
									ExcelUtils.setCellData("Pass", "System Role Not Deleted",iRow, 6,7);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "System Role Deleted",iRow, 6,7);
									Assert.fail();
								}					
							}
							else
							{
								ExcelUtils.setCellData("Fail", "System Role Not Found",iRow, 6,7);
								Assert.fail();
							}
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,SysGroupName,1);
					if(a[0]==1)
					{
						SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysGroupName + "," + SysGroupDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=6)
	public void viewSysGroup() 
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
				SysGroupName=ExcelUtils.getCellData(iRow, 4);
				SysGroupDesc=ExcelUtils.getCellData(iRow, 5);	

				if(MethodName.equalsIgnoreCase("viewSysGroup"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						String[] sptGroupName = SysGroupName.split(",");
						String[] sptGroupDesc = SysGroupDesc.split(",");
						for(int j=0;j<sptGroupName.length;j++)
						{
							getRefresh();
							TEST_RESULT testResult =  SysMgmt_Action.execute_addSysGroup(driver, sptGroupName[j], sptGroupDesc[j]);
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
								continue;
							}
						}
						int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[1],1);							
						if(a[0]==1)
						{
							//Create group1 and group2. Move group2 to group1
							SysMgmt_Action.execute_moveGroup(driver, a[1], getTablePath,sptGroupName[0]);
							Thread.sleep(5000);
							getRefresh();
							int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[0],1);							

							// Check first added group name is available in the list
							if(a1[0]==1)
							{
								// if group1 is available click view button and check whether the moved group2 is available inside this group1 name or not
								driver.findElement(By.xpath(getTablePath+"/tr["+(a1[1]+1)+"]/td[8]/div")).click();
								Thread.sleep(3000);
								int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[1],1);							
								if(a2[0]==1)                                              
								{
									String getGroupName=driver.findElement(By.xpath(".//*[@id='datagrid-row-r6-2-"+a2[1]+"']/td[2]/div")).getText();
									if(getGroupName.equals(sptGroupName[1]))
									{
										Thread.sleep(3000);
										SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a2[1]);
										Thread.sleep(3000);
										getRefresh();
										driver.findElement(By.xpath(".//*[@id='department_group_uri_text']/a")).click();
										SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a1[1]);
										Thread.sleep(5000);
										ExcelUtils.setCellData("Pass", "Group View and Move Success",iRow, 6,7);	  
									}
									else
									{
										ExcelUtils.setCellData("Fail", "Group View and Move Failed",iRow, 6,7);
									}
								}
								else
								{
									ExcelUtils.setCellData("Fail", "Moved Group not Found in the Group List",iRow, 6,7);
								}
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Parent Group not found in the list",iRow, 6,7);
							}  
						}
						else
						{
							ExcelUtils.setCellData("Fail", "Sub Group to move is not found in the list",iRow, 6,7);
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
					String[] sptSysGroupName=SysGroupName.split(",");
					for(int i=0;i<sptSysGroupName.length;i++)
					{
						int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptSysGroupName[i],1);
						if(a[0]==1)
						{
							SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
						}
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysGroupName + "," + SysGroupDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();				
	}
	
	@Test(priority=7)
	public void modifySysGroup_Valid() 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		String[] sptGroupName=null;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				SysGroupName=ExcelUtils.getCellData(iRow, 4);
				SysGroupDesc=ExcelUtils.getCellData(iRow, 5);

				if(MethodName.equalsIgnoreCase("modifySysGroup_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptGroupName = SysGroupName.split(",");
						String[] sptGroupDesc = SysGroupDesc.split(",");
						
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSysGroup(driver, sptGroupName[0], sptGroupDesc[0]);
						System.out.println("Add test result is:"+testResult);
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
							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[0],1);							

							if(a[0]==1)
							{
								TEST_RESULT getRes=SysMgmt_Action.execute_modifySysGroup(driver, a[1], getTablePath,sptGroupName[1], sptGroupDesc[1]);
								Thread.sleep(5000);
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_SUCCESS)
								{
									int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[1],1);							

									if(a1[0]==1)
									{
										TEST_RESULT chkModify=SysMgmt_Action.execute_AfterModifySysGroup(driver, a1[1], getTablePath, sptGroupName[1], sptGroupDesc[1]);
										Thread.sleep(3000);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a1[1]);
											Thread.sleep(3000);
											ExcelUtils.setCellData("Pass", "Modify - Success",iRow, 6,7);
										}
										else
										{
											SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a1[1]);
											Thread.sleep(3000);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 6,7);
										}
									}
									else
									{
										int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[0],1);							
										if(a2[0]==1)
										{
											SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a2[1]);
										}
										Thread.sleep(3000);
										ExcelUtils.setCellData("Fail", "After modify System Role not found in list",iRow, 6,7);
										Assert.fail();
									}
								}
								else
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[0],1);							
									if(a2[0]==1)
									{
										SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a2[1]);
									}
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 6,7);
								}
							}
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
					String[] sptSysGroupName=SysGroupName.split(",");
					for(int i=0;i<sptSysGroupName.length;i++)
					{
						int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptSysGroupName[i],1);
						if(a[0]==1)
						{
							SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
						}
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysGroupName + "," + SysGroupDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=8)
	public void modifySysGroup_InValid() 
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e){Assert.fail();}

		boolean didAnyMethodFail=false;
		String[] sptGroupName=null;

		for(int iRow=1;iRow<noofRow;iRow++)
		{
			try
			{
				MethodName=ExcelUtils.getCellData(iRow, 2);
				ExecutionMode=ExcelUtils.getCellData(iRow, 3);
				SysGroupName=ExcelUtils.getCellData(iRow, 4);
				SysGroupDesc=ExcelUtils.getCellData(iRow, 5);

				if(MethodName.equalsIgnoreCase("modifySysGroup_InValid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						sptGroupName = SysGroupName.split(",");
						String[] sptGroupDesc = SysGroupDesc.split(",");
						
						getRefresh();
						TEST_RESULT testResult =  SysMgmt_Action.execute_addSysGroup(driver, sptGroupName[0], sptGroupDesc[0]);
						System.out.println("Add test result is:"+testResult);
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
							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[0],1);							
							if(a[0]==1)
							{
								TEST_RESULT getRes=SysMgmt_Action.execute_modifySysGroup(driver, a[1], getTablePath,sptGroupName[1], sptGroupDesc[1]);
								Thread.sleep(5000);	 
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_FAILURE)
								{	 
									int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[0],1);							
									if(a1[0]==1)
									{
										TEST_RESULT chkModify=SysMgmt_Action.execute_AfterModifySysGroup(driver, a1[1], getTablePath, sptGroupName[0], sptGroupDesc[0]);
										Thread.sleep(3000);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a1[1]);
											Thread.sleep(3000);
											ExcelUtils.setCellData("Pass", "Modify Invalid - Success",iRow, 6,7);
										}
										else
										{
											SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a1[1]);
											Thread.sleep(3000);
											ExcelUtils.setCellData("Fail", "Modify Invalid - Failed",iRow, 6,7);
											Assert.fail();
										}
									}
								}
								else
								{
									int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",sptGroupName[1],1);

									for(int k=1;k<=i[0];k++)
									{
										getRefresh();
										int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptGroupName[1],0);

										if(a2[0]==1)
										{
											SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a2[1]);
										}
										else
										{
											System.out.println("No Group found to delete");
										}
									}													 
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "Modify Invalid - Failed",iRow, 6,7);
									Assert.fail();
								}
							}
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
					String[] sptSysGroupName=SysGroupName.split(",");
					for(int i=0;i<sptSysGroupName.length;i++)
					{
						int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptSysGroupName[i],1);
						if(a[0]==1)
						{
							SysMgmt_Action.execute_deleteSystemGroup(driver,getTablePath,a[1]);
						}
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + SysGroupName + "," + SysGroupDesc);
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
		SystemGroup_Page.lnk_SytemManagement_Group(driver).click();
		Thread.sleep(3000);
	}
}
