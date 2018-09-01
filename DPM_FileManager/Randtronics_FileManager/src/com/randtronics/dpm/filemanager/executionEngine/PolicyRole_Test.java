package com.randtronics.dpm.filemanager.executionEngine;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Policy_Action;
import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action;
import com.randtronics.dpm.filemanager.appModules.Policy_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class PolicyRole_Test extends ChromeTest
{
	public static int noofRow;
	public String MethodName,ExecutionMode,RoleName, RoleDesc;
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
			noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_PolicyRole);
			parser.RepositoryParser(Constants.path_ObjectRepo);
			getTablePath=parser.prop.getProperty("PolicyRole_TablePath");
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}

	@Test(priority=1)
	public void addPolicyRole_Valid() 
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
				RoleName=ExcelUtils.getCellData(iRow, 4);
				RoleDesc=ExcelUtils.getCellData(iRow, 5);

				if(MethodName.equalsIgnoreCase("addPolicyRole_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh(); 

						Policy_Action.TEST_RESULT testResult =Policy_Action.execute_addPolicyRole(driver, RoleName, RoleDesc);

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
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
							if(a[0]==1)
							{
								TEST_RESULT result=Policy_Action.execute_AfterAddPolicyRole(driver,a[1],getTablePath,RoleName,RoleDesc);
								Thread.sleep(3000);
								if(result==TEST_RESULT.RESULT_SUCCESS)
								{
									Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Pass", "Policy Role Added Successfully",iRow, 6,7);
								}
								else
								{
									Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "Policy Role Not Added Successfully",iRow, 6,7);
									Assert.fail();
								}
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Policy Role not found in the list",iRow, 6,7);
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
				try {
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + RoleName + "," + RoleDesc);
			}
		}

		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=2)
	public void addPolicyRole_Invalid()
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
				RoleName=ExcelUtils.getCellData(iRow, 4);
				RoleDesc=ExcelUtils.getCellData(iRow, 5);

				if(MethodName.equalsIgnoreCase("addPolicyRole_Invalid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh(); 
						int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",RoleName,0);
						System.out.println("i[0]:"+i[0]);
						System.out.println("i[1]:"+i[1]);
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyRole(driver, RoleName, RoleDesc);
						switch (testResult) 
						{
						case RESULT_SUCCESS:
							int b[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
							System.out.println("b[0]:"+b[0]);
							if(b[0]==1)
							{
								Policy_Action.execute_deletePolicyRole(driver,getTablePath,b[1]);
								Thread.sleep(3000);
								ExcelUtils.setCellData("Fail", "Policy Role Added",iRow, 6,7);
								didAnyMethodFail = true;
							}
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_FAILURE:

							int i1[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr/td[1]",RoleName,0);
							System.out.println("i1[0]:"+i1[0]);
							System.out.println("i1[1]:"+i1[1]);
							if(i[0]==i1[0])
							{
								ExcelUtils.setCellData("Pass", "Policy Role Not Added",iRow, 6,7);
							}
							else
							{
								for(int k=1;k<=i[0];k++)
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);

									if(a2[0]==1)
									{
										Policy_Action.execute_deletePolicyRole(driver,getTablePath,a2[1]);
									}
									else
									{
										System.out.println("No Policy Role found to delete");
									}
								}
								Thread.sleep(4000);
								ExcelUtils.setCellData("Fail", "Policy Role Added",iRow, 6,7);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + RoleName + "," + RoleDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=3)
	public void deletePolicyRole_CheckDialog() 
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
				RoleName=ExcelUtils.getCellData(iRow, 4);
				RoleDesc=ExcelUtils.getCellData(iRow, 5);	

				if(MethodName.equalsIgnoreCase("deletePolicyRole_CheckDialog"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyRole(driver, RoleName, RoleDesc);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
							if(a[0]==1)
							{	
								driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[4]/div/div/div[1]/div/div/div[2]/div[1]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(a[1]+1)+"]/td[7]/div")).click();
								boolean checkDialog=driver.findElements(By.xpath("html/body/div[36]/div[2]/div/div")).size()!= 0;
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
								ExcelUtils.setCellData("Fail", "Policy Role Not Found",iRow,6,7);
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
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",RoleName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + RoleName + "," + RoleDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=4)
	public void deletePolicyRole_Yes()
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
				RoleName=ExcelUtils.getCellData(iRow, 4);
				RoleDesc=ExcelUtils.getCellData(iRow, 5);	

				if(MethodName.equalsIgnoreCase("deletePolicyRole_Yes"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyRole(driver, RoleName, RoleDesc);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
							if(a[0]==1)
							{
								Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
								if(a1[0]==0)
								{
									ExcelUtils.setCellData("Pass", "Policy Role Deleted",iRow, 6,7);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "Policy Role Not Deleted",iRow, 6,7);
									Assert.fail();
								}					
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Policy Role Not Found",iRow, 6,7);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + RoleName + "," +RoleDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}

	@Test(priority=5)
	public void deletePolicyRole_No() 
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
				RoleName=ExcelUtils.getCellData(iRow, 4);
				RoleDesc=ExcelUtils.getCellData(iRow, 5);	

				if(MethodName.equalsIgnoreCase("deletePolicyUser_No"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyRole(driver, RoleName, RoleDesc);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:

							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
							if(a[0]==1)
							{
								driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(a[1]+1)+"]/td[7]/div")).click();						
								driver.findElement(By.linkText("No")).click();
								Thread.sleep(5000);
								int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
								if(a1[0]==1)
								{
									Policy_Action.execute_deletePolicyRole(driver,getTablePath,a1[1]);
									ExcelUtils.setCellData("Pass", "Policy Role Not Deleted",iRow, 6,7);
								}
								else
								{
									ExcelUtils.setCellData("Fail", "Policy Role Deleted",iRow, 6,7);
									Assert.fail();
								}					
							}
							else
							{
								ExcelUtils.setCellData("Fail", "Policy Role Not Found",iRow, 6,7);
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
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + RoleName + "," + RoleDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}


	@Test(priority=6)
	public void viewPolicyRole() 
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
				RoleName=ExcelUtils.getCellData(iRow, 4);
				RoleDesc=ExcelUtils.getCellData(iRow, 5);		    		      

				if(MethodName.equalsIgnoreCase("viewPolicyRole"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{		    	  	
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_addPolicyRole(driver,RoleName,RoleDesc);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_ERROR.toString(),iRow, 6,7);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
							if(a[0]==1)
							{
								TEST_RESULT getResult=Policy_Action.execute_viewPolicyRole(driver,a[1],getTablePath,RoleName,RoleDesc);
								if(getResult==TEST_RESULT.RESULT_SUCCESS)
								{
									Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Pass", "View - Success",iRow, 6,7);
								}
								else
								{
									Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "View - Failed",iRow, 6,7);
									Assert.fail();
								}
							}
							else
							{
								ExcelUtils.setCellData("Fail", "System Role not found in the list",iRow, 6,7);
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
					int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",RoleName,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 6,7);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + RoleName + "," + RoleDesc);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();				
	}
	
    @Test(priority=6)
    public void modifyPolicyRole_Valid() 
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
                      RoleName=ExcelUtils.getCellData(iRow, 4);
                      RoleDesc=ExcelUtils.getCellData(iRow, 5);

                      if(MethodName.equalsIgnoreCase("modifyPolicyRole_Valid"))
                      {
                            if(ExecutionMode.equalsIgnoreCase("Y"))
                            {
                                  String[] sptRoleName = RoleName.split(",");
                                  String[] sptRoleDesc = RoleDesc.split(",");
                                  getRefresh();
                                  TEST_RESULT testResult =  Policy_Action.execute_addPolicyRole(driver, sptRoleName[0], sptRoleDesc[0]);
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
                                        int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptRoleName[0],0);
                                        if(a[0]==1)
                                        {
                                              TEST_RESULT getRes=Policy_Action.execute_modifyPolicyRole(driver, a[1], sptRoleName[1], sptRoleName[1]);
                                              Thread.sleep(5000);
                                              getRefresh();
                                              if(getRes==TEST_RESULT.RESULT_SUCCESS)
                                              {
                                                    int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,sptRoleName[1],0);

                                                    if(a1[0]==1)
                                                    {
                                                          TEST_RESULT chkModify=Policy_Action.execute_AftermodifyPolicyRole(driver, a1[1], getTablePath, sptRoleName[1], sptRoleName[1]);
                                                          Thread.sleep(3000);
                                                          getRefresh();
                                                          if(chkModify==TEST_RESULT.RESULT_SUCCESS)
                                                          {
                                                                Policy_Action.execute_deletePolicyRole(driver,getTablePath,a1[1]);
                                                                Thread.sleep(3000);
                                                                //getRefresh();
                                                                ExcelUtils.setCellData("Pass", "Modify - Success",iRow, 6,7);
                                                          }
                                                          else
                                                          {
                                                                Policy_Action.execute_deletePolicyRole(driver,getTablePath,a1[1]);
                                                                Thread.sleep(3000);
                                                                ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 6,7);
                                                          }
                                                    }
                                                    else
                                                    {
                                                          int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
                                                          if(a2[0]==1)
                                                          {
                                                                Policy_Action.execute_deletePolicyRole(driver,getTablePath,a1[1]);
                                                          }
                                                          Thread.sleep(3000);
                                                          ExcelUtils.setCellData("Fail", "After modify Role Name not found in list",iRow, 6,7);
                                                          Assert.fail();
                                                    }
                                              }
                                              else
                                              {
                                                    getRefresh();
                                                    int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
                                                    if(a2[0]==1)
                                                    {
                                                          Policy_Action.execute_deletePolicyRole(driver,getTablePath,a2[1]);
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
                            int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
                            if(a[0]==1)
                            {
                                  Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
                            }
                            ExcelUtils.setCellData("Error","",iRow, 6,7);
                      }
                      catch(Exception ex)
                      {
                            System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
                      }
                      Assert.fail("Failed " + iRow + "With Parameters:" + RoleName + "," + RoleDesc);
                }
          }
          if(didAnyMethodFail == true)
                Assert.fail();
    }


    @Test(priority=7)
    public void modifyPolicyRole_Invalid() 
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
                      RoleName=ExcelUtils.getCellData(iRow, 4);
                      RoleDesc=ExcelUtils.getCellData(iRow, 5);

                      if(MethodName.equalsIgnoreCase("modifyPolicyRole_Invalid"))
                      {
                            if(ExecutionMode.equalsIgnoreCase("Y"))
                            {
                                  String[] sptRoleName = RoleName.split(",");
                                  String[] sptRoleDesc = RoleDesc.split(",");

                                  getRefresh();
                                  TEST_RESULT testResult =  Policy_Action.execute_addPolicyRole(driver, sptRoleName[0], sptRoleDesc[0]);
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
                                        int a[]=SysMgmt_Action.searchForData(driver,getTablePath,sptRoleName[0],0);

                                        if(a[0]==1)
                                        {
                                              TEST_RESULT getRes=Policy_Action.execute_modifyPolicyRole(driver, a[1], sptRoleName[1],sptRoleDesc[1]);
                                              Thread.sleep(5000);     
                                              getRefresh();
                                              if(getRes==TEST_RESULT.RESULT_FAILURE)
                                              {
                                                    int a1[]=SysMgmt_Action.searchForData(driver,getTablePath,sptRoleName[0],0);

                                                    if(a1[0]==1)
                                                    {

                                                          TEST_RESULT chkModify=Policy_Action.execute_AftermodifyPolicyRole(driver, a1[1], getTablePath, sptRoleName[0], sptRoleDesc[0]);
                                                          Thread.sleep(3000);
                                                          getRefresh();
                                                          if(chkModify==TEST_RESULT.RESULT_SUCCESS)
                                                          {
                                                                Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
                                                                Thread.sleep(3000);                                                                  
                                                                ExcelUtils.setCellData("Pass", "Modify Invalid - Success",iRow, 6,7);
                                                          }
                                                          else
                                                          {
                                                              Policy_Action.execute_deletePolicyRole(driver,getTablePath,a1[1]);
                                                                Thread.sleep(3000);
                                                                ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 6,7);
                                                                Assert.fail();
                                                          }
                                                    }
                                              }
                                              else
                                              {
                                                    int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath+"/tr[1]",sptRoleName[1],0);

                                                    for(int k=1;k<=i[0];k++)
                                                    {
                                                          getRefresh();
                                                          int a2[]=SysMgmt_Action.searchForData(driver,getTablePath,sptRoleName[1],0);

                                                          if(a2[0]==1)
                                                          {
                                                              Policy_Action.execute_deletePolicyRole(driver,getTablePath,a2[1]);
                                                          }
                                                          else
                                                          {
                                                                System.out.println("No User found to delete");
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
                      try {
                            int a[]=SysMgmt_Action.searchForData(driver,getTablePath,RoleName,0);
                            if(a[0]==1)
                            {
                                Policy_Action.execute_deletePolicyRole(driver,getTablePath,a[1]);
                            }
                            ExcelUtils.setCellData("Error","",iRow, 6,7);
                      }
                      catch(Exception ex)
                      {
                            System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
                      }
                      Assert.fail("Failed " + iRow + "With Parameters:" + RoleName + "," + RoleDesc);
                }
          }
          if(didAnyMethodFail == true)
                Assert.fail();
    }

	@Test(priority=9)
	public void signOut()
	{
		try
		{
			driver.navigate().refresh();
			Home_Page.lnk_LogOut(driver).click();
		}
		catch(Exception e)
		{
			System.out.println("signOut Exception"+e);
		}
	}

	public void getRefresh()
	{
		try
		{
			driver.navigate().refresh();
			Home_Page.lnk_Policy(driver).click();
			Thread.sleep(3000);
		}
		catch(Exception e)
		{

		}
	}
}