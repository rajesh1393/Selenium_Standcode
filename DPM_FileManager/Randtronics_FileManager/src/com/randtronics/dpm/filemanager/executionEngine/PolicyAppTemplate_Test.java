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
import com.randtronics.dpm.filemanager.pageObjects.PolicyAppTemplate_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;


public class PolicyAppTemplate_Test extends ChromeTest
{
    public static int noofRow;
    public String MethodName,ExecutionMode,AppTemplateNme, AppTemplateProcess,AppTemplateOS,AppTemplateHashCode,AppTemplateOperation,AppTemplateEncryptFletype;
    public static RepositoryParser parser=new RepositoryParser();
    String getTablePath_AppTemp="";

    @Test(priority=0)
    @Parameters({"username","password"})
    public void signIn(String username,String password) 
    {
          try
          {
                Signin_Action.execute_Login(driver, username, password);
                Home_Page.lnk_Policy(driver).click();
                PolicyAppTemplate_Page.lnk_AppTemplate(driver).click();
                noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_PolicyAppTemplate);
                parser.RepositoryParser(Constants.path_ObjectRepo);
                getTablePath_AppTemp=parser.prop.getProperty("PolicyAppTemp_TablePath");
          }
          catch(Exception e)
          {
                Assert.fail("Sign In Error");
          }
    }

    @Test(priority=1)
    public void addPolicyAppTemplate_Valid() 
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
    			AppTemplateNme=ExcelUtils.getCellData(iRow, 4);
    			AppTemplateProcess=ExcelUtils.getCellData(iRow, 5);
    			AppTemplateOS=ExcelUtils.getCellData(iRow, 6);
    			AppTemplateHashCode=ExcelUtils.getCellData(iRow,7 );
    			AppTemplateOperation=ExcelUtils.getCellData(iRow, 8);
    			AppTemplateEncryptFletype=ExcelUtils.getCellData(iRow,9);

    			if(MethodName.equalsIgnoreCase("addPolicyAppTemplate_Valid"))
    			{
    				if(ExecutionMode.equalsIgnoreCase("Y"))
    				{
    					getRefresh(); 

    					Policy_Action.TEST_RESULT testResult =Policy_Action.execute_PolicyApptemplate(driver, AppTemplateNme, AppTemplateProcess,AppTemplateOS,AppTemplateHashCode,AppTemplateOperation,AppTemplateEncryptFletype);

    					switch (testResult) 
    					{                                   
	    					case RESULT_FAILURE:
	    						ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 10,11);
	    						didAnyMethodFail = true;
	    						break;
	    					case RESULT_ERROR:
	    						ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 10,11);
	    						didAnyMethodFail = true;
	    						break;
	    					case RESULT_SUCCESS:
                                int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                                if(a[0]==1)
                                {
                                      TEST_RESULT result=Policy_Action.execute_AfterAddPolicyApptemplate(driver,a[1],AppTemplateNme,AppTemplateProcess,AppTemplateOS,AppTemplateHashCode);
                                      Thread.sleep(3000);
                                      if(result==TEST_RESULT.RESULT_SUCCESS)
                                      {
                                            Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                                            Thread.sleep(3000);
                                            ExcelUtils.setCellData("Pass", "App Name Added Successfully",iRow, 10,11);
                                      }
                                      else
                                      {
                                            Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                                            Thread.sleep(3000);
                                            ExcelUtils.setCellData("Fail", "App Name Not Added Successfully",iRow, 10,11);
                                            Assert.fail();
                                      }
                                }
                                else
                                {
                                      ExcelUtils.setCellData("Fail", "App Name not found in the list",iRow, 10,11);
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
                      int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                      if(a[0]==1)
                      {
                            Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                      }
                      ExcelUtils.setCellData("Error","",iRow, 10,11);
                }
                catch(Exception ex)
                {
                      System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
                }
                Assert.fail("Failed " + iRow + "With Parameters:" + AppTemplateNme + "," + AppTemplateProcess + AppTemplateOS + "," + AppTemplateHashCode);

    		}
    		if(didAnyMethodFail == true)
    			Assert.fail();
    	}
    }
    
	@Test(priority=2)
	public void addPolicyAppTemplate_Invalid()
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
    			AppTemplateNme=ExcelUtils.getCellData(iRow, 4);
    			AppTemplateProcess=ExcelUtils.getCellData(iRow, 5);
    			AppTemplateOS=ExcelUtils.getCellData(iRow, 6);
    			AppTemplateHashCode=ExcelUtils.getCellData(iRow,7 );
    			AppTemplateOperation=ExcelUtils.getCellData(iRow, 8);
    			AppTemplateEncryptFletype=ExcelUtils.getCellData(iRow,9);

    			if(MethodName.equalsIgnoreCase("addPolicyAppTemplate_Invalid"))
    			{
    				if(ExecutionMode.equalsIgnoreCase("Y"))
    				{
						getRefresh(); 
						int i[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath_AppTemp+"/tr/td[1]",AppTemplateNme,0);
    					Policy_Action.TEST_RESULT testResult =Policy_Action.execute_PolicyApptemplate(driver, AppTemplateNme, AppTemplateProcess,AppTemplateOS,AppTemplateHashCode,AppTemplateOperation,AppTemplateEncryptFletype);
						switch (testResult) 
						{
						case RESULT_SUCCESS:
							int b[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
							if(b[0]==1)
							{
	                            Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,b[1]);
								Thread.sleep(3000);
								ExcelUtils.setCellData("Fail", "Policy App Template Added",iRow, 10,11);
								didAnyMethodFail = true;
							}
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 10,11);
							didAnyMethodFail = true;
							break;
						case RESULT_FAILURE:
							int i1[]=SysMgmt_Action.searchForDuplicateData(driver,getTablePath_AppTemp+"/tr/td[1]",AppTemplateNme,0);
							System.out.println("i1[0]:"+i1[0]);
							System.out.println("i1[1]:"+i1[1]);
							if(i[0]==i1[0])
							{
								ExcelUtils.setCellData("Pass", "Policy App Template Not Added",iRow, 10,11);
							}
							else
							{
								for(int k=1;k<=i[0];k++)
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);

									if(a2[0]==1)
									{
			                            Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a2[1]);
									}
									else
									{
										System.out.println("No Policy App Template found to delete");
									}
								}
								Thread.sleep(4000);
								ExcelUtils.setCellData("Fail", "Policy App Template Added",iRow, 10,11);
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
                    int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                    if(a[0]==1)
                    {
                          Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                    }
                    ExcelUtils.setCellData("Error","",iRow, 10,11);
              }
              catch(Exception ex)
              {
                    System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
              }
              Assert.fail("Failed " + iRow + "With Parameters:" + AppTemplateNme + "," + AppTemplateProcess + AppTemplateOS + "," + AppTemplateHashCode);
			}
		}
		if(didAnyMethodFail == true)
			Assert.fail();
	}
	
	@Test(priority=3)
    public void deletePolicyAppTemp_CheckDialog() 
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
                      AppTemplateNme=ExcelUtils.getCellData(iRow, 4);
                      AppTemplateProcess=ExcelUtils.getCellData(iRow, 5);
                      AppTemplateOS=ExcelUtils.getCellData(iRow, 6);
                      AppTemplateHashCode=ExcelUtils.getCellData(iRow,7 );
                      AppTemplateOperation=ExcelUtils.getCellData(iRow, 8);
                      AppTemplateEncryptFletype=ExcelUtils.getCellData(iRow,9);

                      if(MethodName.equalsIgnoreCase("deletePolicyAppTemp_CheckDialog"))
                      {
                            if(ExecutionMode.equalsIgnoreCase("Y"))
                            {
                                  getRefresh();
                                  TEST_RESULT testResult = Policy_Action.execute_PolicyApptemplate(driver, AppTemplateNme, AppTemplateProcess,AppTemplateOS,AppTemplateHashCode,AppTemplateOperation,AppTemplateEncryptFletype);
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
                                        int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                                        if(a[0]==1)
                                        {
                                              driver.findElement(By.xpath(getTablePath_AppTemp+"/tr["+(a[1]+1)+"]/td[13]/div")).click();                                    
                                              boolean checkDialog=driver.findElements(By.xpath("html/body/div[48]")).size() != 0;
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
                                              ExcelUtils.setCellData("Fail", "App Name Not Found",iRow, 10,11);
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
                            int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                            if(a[0]==1)
                            {
                                  Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                            }
                            ExcelUtils.setCellData("Error","",iRow, 10,11);
                      }
                      catch(Exception ex)
                      {
                            System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
                      }
                      Assert.fail("Failed " + iRow + "With Parameters:" + AppTemplateNme + "," + AppTemplateProcess + AppTemplateOS + "," + AppTemplateHashCode+ ","+ AppTemplateOperation+ ","+AppTemplateEncryptFletype);
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
                      AppTemplateNme=ExcelUtils.getCellData(iRow, 4);
                      AppTemplateProcess=ExcelUtils.getCellData(iRow, 5);
                      AppTemplateOS=ExcelUtils.getCellData(iRow, 6);
                      AppTemplateHashCode=ExcelUtils.getCellData(iRow,7 );
                      AppTemplateOperation=ExcelUtils.getCellData(iRow, 8);
                      AppTemplateEncryptFletype=ExcelUtils.getCellData(iRow,9);

                      if(MethodName.equalsIgnoreCase("deletePolicyAppTemp_Yes"))
                      {
                            if(ExecutionMode.equalsIgnoreCase("Y"))
                            {
                                  getRefresh();
                                  TEST_RESULT testResult =  Policy_Action.execute_PolicyApptemplate(driver, AppTemplateNme, AppTemplateProcess,AppTemplateOS,AppTemplateHashCode,AppTemplateOperation,AppTemplateEncryptFletype);
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

                                        int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                                        if(a[0]==1)
                                        {
                                              Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                                              Thread.sleep(5000);
                                              int a1[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                                              if(a1[0]==0)
                                              {
                                                    ExcelUtils.setCellData("Pass", "App Name Deleted",iRow, 10,11);
                                              }
                                              else
                                              {
                                                    ExcelUtils.setCellData("Fail", "App Name Not Deleted",iRow, 10,11);
                                                    Assert.fail();
                                              }                             
                                        }
                                        else
                                        {
                                              ExcelUtils.setCellData("Fail", "App Name Not Found",iRow, 10,11);
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
                            int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                            if(a[0]==1)
                            {
                                  Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                            }
                            ExcelUtils.setCellData("Error","",iRow, 10,11);
                      }
                      catch(Exception ex)
                      {
                            System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
                      }
                      Assert.fail("Failed " + iRow + "With Parameters:" + AppTemplateNme + "," + AppTemplateProcess + AppTemplateOS + "," + AppTemplateHashCode+ ","+ AppTemplateOperation+ ","+AppTemplateEncryptFletype);
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
                      AppTemplateNme=ExcelUtils.getCellData(iRow, 4);
                      AppTemplateProcess=ExcelUtils.getCellData(iRow, 5);
                      AppTemplateOS=ExcelUtils.getCellData(iRow, 6);
                      AppTemplateHashCode=ExcelUtils.getCellData(iRow,7 );
                      AppTemplateOperation=ExcelUtils.getCellData(iRow, 8);
                      AppTemplateEncryptFletype=ExcelUtils.getCellData(iRow,9);

                      if(MethodName.equalsIgnoreCase("deletePolicyAppTemp_No"))
                      {
                            if(ExecutionMode.equalsIgnoreCase("Y"))
                            {
                                  getRefresh();
                                  TEST_RESULT testResult =  Policy_Action.execute_PolicyApptemplate(driver, AppTemplateNme, AppTemplateProcess,AppTemplateOS,AppTemplateHashCode,AppTemplateOperation,AppTemplateEncryptFletype);
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
                                        int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                                        if(a[0]==1)
                                        {
                                              driver.findElement(By.xpath(getTablePath_AppTemp+"/tr["+(a[1]+1)+"]/td[13]/div")).click();                                    
                                              driver.findElement(By.linkText("No")).click();
                                              Thread.sleep(5000);
                                              int a1[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                                              if(a1[0]==1)
                                              {
                                                    Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a1[1]);
                                                    ExcelUtils.setCellData("Pass", "App Name Not Deleted",iRow, 10,11);
                                              }
                                              else
                                              {
                                                    ExcelUtils.setCellData("Fail", "App Name Deleted",iRow, 10,11);
                                                    Assert.fail();
                                              }                             
                                        }
                                        else
                                        {
                                              ExcelUtils.setCellData("Fail", "App Name Not Found",iRow, 10,11);
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
                            int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                            if(a[0]==1)
                            {
                                  Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                            }
                            ExcelUtils.setCellData("Error","",iRow, 10,11);
                      }
                      catch(Exception ex)
                      {
                            System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
                      }
                      Assert.fail("Failed " + iRow + "With Parameters:" + AppTemplateNme + "," + AppTemplateProcess + AppTemplateOS + "," + AppTemplateHashCode+ ","+ AppTemplateOperation+ ","+AppTemplateEncryptFletype);
                }
          }
          if(didAnyMethodFail == true)
                Assert.fail();
    }
    

	@Test(priority=6)
      public void viewPolicyAppTemplate_Valid() 
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
                        AppTemplateNme=ExcelUtils.getCellData(iRow, 4);
                        AppTemplateProcess=ExcelUtils.getCellData(iRow, 5);
                        AppTemplateOS=ExcelUtils.getCellData(iRow, 6);
                        AppTemplateHashCode=ExcelUtils.getCellData(iRow,7 );
                        AppTemplateOperation=ExcelUtils.getCellData(iRow, 8);
                        AppTemplateEncryptFletype=ExcelUtils.getCellData(iRow,9);                         

                        if(MethodName.equalsIgnoreCase("viewPolicyAppTemplate_Valid"))
                        {
                              if(ExecutionMode.equalsIgnoreCase("Y"))
                              {                       
                                    getRefresh();
                                    TEST_RESULT testResult = Policy_Action.execute_PolicyApptemplate(driver, AppTemplateNme, AppTemplateProcess,AppTemplateOS,AppTemplateHashCode,AppTemplateOperation,AppTemplateEncryptFletype);
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
                                          int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                                          if(a[0]==1)
                                          {
                                                TEST_RESULT getResult=Policy_Action.execute_ViewPolicyApptemplate(driver,a[1],getTablePath_AppTemp,AppTemplateNme,AppTemplateProcess,AppTemplateOS,AppTemplateHashCode,AppTemplateOperation,AppTemplateEncryptFletype);
                                                if(getResult==TEST_RESULT.RESULT_SUCCESS)
                                                {
                                                      Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                                                      Thread.sleep(3000);
                                                      ExcelUtils.setCellData("Pass", "View - Success",iRow, 10,11);
                                                }
                                                else
                                                {
                                                      Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
                                                      Thread.sleep(3000);
                                                      ExcelUtils.setCellData("Fail", "View - Failed",iRow, 10,11);
                                                      Assert.fail();
                                                }
                                          }
                                          else
                                          {
                                                ExcelUtils.setCellData("Fail", "App Name not found in the list",iRow, 10,11);
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
                              int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
                              if(a[0]==1)
                              {
                                    Policy_Action.execute_deletePolicyRole(driver,getTablePath_AppTemp,a[1]);
                              }
                              ExcelUtils.setCellData("Error","",iRow, 10,11);
                        }
                        catch(Exception ex)
                        {
                              System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
                        }
                        Assert.fail("Failed " + iRow + "With Parameters:" + AppTemplateNme + "," + AppTemplateProcess + AppTemplateOS + "," + AppTemplateHashCode);
                  }
            }
            if(didAnyMethodFail == true)
                  Assert.fail();                      
      }
	

	@Test(priority=6)
	public void modifyPolicyAppTemp_Valid() 
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
				AppTemplateNme=ExcelUtils.getCellData(iRow, 4);
				AppTemplateProcess=ExcelUtils.getCellData(iRow, 5);
				AppTemplateOS=ExcelUtils.getCellData(iRow, 6);
				AppTemplateHashCode=ExcelUtils.getCellData(iRow,7 );
				AppTemplateOperation=ExcelUtils.getCellData(iRow, 8);
				AppTemplateEncryptFletype=ExcelUtils.getCellData(iRow,9);

				if(MethodName.equalsIgnoreCase("modifyPolicyAppTemp_Valid"))
				{
					if(ExecutionMode.equalsIgnoreCase("Y"))
					{
						String[] sptAppTemplateNme = AppTemplateNme.split(",");
						String[] sptAppTemplateProcess = AppTemplateProcess.split(",");
						String[] sptAppTemplateOS = AppTemplateOS.split(",");
						String[] sptAppTemplateHashCode = AppTemplateHashCode.split(",");
						String[] sptAppTemplateOperation = AppTemplateOperation.split(",");
						String[] sptAppTemplateEncryptFletype = AppTemplateEncryptFletype.split(",");
						
						getRefresh();
						TEST_RESULT testResult =  Policy_Action.execute_PolicyApptemplate(driver, sptAppTemplateNme[0], sptAppTemplateProcess[0],sptAppTemplateOS[0],sptAppTemplateHashCode[0],sptAppTemplateOperation[0],sptAppTemplateEncryptFletype[0]);
						switch (testResult) 
						{
						case RESULT_FAILURE:
							ExcelUtils.setCellData("Fail", TEST_RESULT.RESULT_FAILURE.toString(),iRow, 10,11);
							didAnyMethodFail = true;
							break;
						case RESULT_ERROR:
							ExcelUtils.setCellData("Error", TEST_RESULT.RESULT_ERROR.toString(),iRow, 10,11);
							didAnyMethodFail = true;
							break;
						case RESULT_SUCCESS:
							getRefresh();
							int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,sptAppTemplateNme[0],0);
							if(a[0]==1)
							{
								TEST_RESULT getRes=Policy_Action.execute_modifyPolicyAppTemp(driver, a[1], sptAppTemplateNme[1], sptAppTemplateProcess[1],sptAppTemplateOS[1], sptAppTemplateHashCode[1],sptAppTemplateOperation[1],sptAppTemplateEncryptFletype[1]);
								Thread.sleep(5000);
								getRefresh();
								if(getRes==TEST_RESULT.RESULT_SUCCESS)
								{
									int a1[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,sptAppTemplateNme[1],0);

									if(a1[0]==1)
									{
										TEST_RESULT chkModify=Policy_Action.execute_AftermodifyPolicyAppTemp(driver, a1[1], getTablePath_AppTemp, sptAppTemplateNme[1], sptAppTemplateProcess[1],sptAppTemplateOS[1], sptAppTemplateHashCode[1],sptAppTemplateOperation[1],sptAppTemplateEncryptFletype[1]);
										Thread.sleep(3000);
										getRefresh();
										if(chkModify==TEST_RESULT.RESULT_SUCCESS)
										{
											Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a1[1]);
											Thread.sleep(3000);
											//getRefresh();
											ExcelUtils.setCellData("Pass", "Modify - Success",iRow, 9,10);
										}
										else
										{
											Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a1[1]);
											Thread.sleep(3000);
											ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 10,11);
										}
									}
									else
									{
										int a2[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
										if(a2[0]==1)
										{
											Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a1[1]);
										}
										Thread.sleep(3000);
										ExcelUtils.setCellData("Fail", "After modify App Name not found in list",iRow, 10,11);
										Assert.fail();
									}
								}
								else
								{
									getRefresh();
									int a2[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
									if(a2[0]==1)
									{
										Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a2[1]);
									}
									Thread.sleep(3000);
									ExcelUtils.setCellData("Fail", "Modify - Failed",iRow, 10,11);
								}
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
				System.out.println(e);
				try
				{
					int a[]=SysMgmt_Action.searchForData(driver,getTablePath_AppTemp,AppTemplateNme,0);
					if(a[0]==1)
					{
						Policy_Action.execute_deletePolicyAppTemplate(driver,getTablePath_AppTemp,a[1]);
					}
					ExcelUtils.setCellData("Error","",iRow, 10,11);
				}
				catch(Exception ex)
				{
					System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
				}
				Assert.fail("Failed " + iRow + "With Parameters:" + AppTemplateNme + "," + AppTemplateProcess + AppTemplateOS + "," + AppTemplateHashCode+ ","+ AppTemplateOperation+ ","+AppTemplateEncryptFletype);
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
                PolicyAppTemplate_Page.lnk_AppTemplate(driver).click();
                Thread.sleep(3000);
          }
          catch(Exception e)
          {
                
          }
    }
}
