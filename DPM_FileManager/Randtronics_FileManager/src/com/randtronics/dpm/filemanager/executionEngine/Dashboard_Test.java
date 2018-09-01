package com.randtronics.dpm.filemanager.executionEngine;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.randtronics.dpm.filemanager.appModules.Signin_Action;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Dashboard_Page;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.utility.ExcelUtils;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Dashboard_Test extends ChromeTest
{
	public static int noofRow;
	String MethodName,ExecutionMode,OptionToSelect;
	public static RepositoryParser parser=new RepositoryParser();
	
	@Test(priority=0)
	@Parameters({"username","password"})
	public void signIn(String username,String password) throws Exception
	{
		try
		{
			Signin_Action.execute_Login(driver, username, password);
			//noofRow=ExcelUtils.getRowCount(Constants.File_TestSheet_SystemUser);
		}
		catch(Exception e)
		{
			Assert.fail("Sign In Error");
		}
	}
	

	  @Test(priority=1)
	  public void DashboardOptions_Save() throws Exception
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
				  OptionToSelect=ExcelUtils.getCellData(iRow, 4);

				  if(MethodName.equalsIgnoreCase("DashboardOptions_Save"))
				  {
					  if(ExecutionMode.equalsIgnoreCase("Y"))
					  {
						  parser.RepositoryParser(Constants.path_ObjectRepo);

						  Dashboard_Page.lnk_DisplayOptions(driver).click();
						  Thread.sleep(3000);

						  WebElement ele1=Dashboard_Page.AllDashboardList_DBDialog(driver);
						  WebElement ele2=Dashboard_Page.RightArrow_DBDialog(driver);
						  WebElement ele3=Dashboard_Page.SelectedDashboardList_DBDialog(driver);
						  boolean chk=multiSelectListBox(ele1,"Global Status;Illegal File Activity by User",ele2);
						  if(chk==true)
						  {
							  Dashboard_Page.SaveBtn_DBDialog(driver).click();
							  Thread.sleep(3000);
							  Dashboard_Page.lnk_DisplayOptions(driver).click();
							  boolean chk1=checkMultiSelListBox(ele3,"Global Status;Illegal File Activity by User");
							  Thread.sleep(3000);
							  if(chk1==true)
							  {
									ExcelUtils.setCellData("Pass","",iRow, 5,6);
							  }
							  else
							  {
									ExcelUtils.setCellData("Fail","",iRow, 5,6);
							  }
						  }
						  else
						  {
							  Dashboard_Page.CancelBtn_DBDialog(driver).click();
								ExcelUtils.setCellData("Fail","",iRow, 5,6);
						  }
					  }
				  }
			  }
			  catch(Exception e)
			  {
					try
					{
						ExcelUtils.setCellData("Error","",iRow, 5,6);
					}
					catch(Exception ex)
					{
						System.err.println("Unable to save result to report TestSheet: " + ex.getMessage());
					}
					Assert.fail("Failed " + iRow + "With Parameters:" + OptionToSelect);
			  }
		  }
		  if(didAnyMethodFail == true)
				Assert.fail();
	  }

	@Test(priority=2)
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
	
	private static boolean multiSelectListBox(WebElement selectdialog,String texttoselect,WebElement arrow) throws Exception
	{
		boolean matchFound = false;
		if(texttoselect!="")
		{
			Select selDialog=new Select(selectdialog);
			
			List<WebElement> selDialogList = selDialog.getOptions();
			int selDialogList_Size =selDialogList.size();
			
			if(selDialogList_Size!=0)
			{
				String[] sptTextToSelect=texttoselect.split(";");
				int textToSelect_Size=sptTextToSelect.length;
				
				for(int i=0;i<textToSelect_Size;i++)
				{
					selDialogList = selDialog.getOptions();
					selDialogList_Size =selDialogList.size();

					for(int j=0;j<selDialogList_Size;j++)
					{
						String getValueFromList=selDialogList.get(j).getText();
						
						if(sptTextToSelect[i].contains(getValueFromList))
						{
							selDialog.selectByVisibleText(sptTextToSelect[i]);
							
							if(arrow.isEnabled())
							{
								arrow.click();
								matchFound = true;
								break;
							}
						}
					}
				}
			}
		}
		return matchFound;
	}
	
	private static boolean checkMultiSelListBox(WebElement selectdialog,String texttosearch)
	{
		boolean matchFound = false;
		if(texttosearch!="")
		{
			Select selDialog=new Select(selectdialog);

			List<WebElement> selDialogList = selDialog.getOptions();
			int selDialogList_Size =selDialogList.size();

			if(selDialogList_Size!=0)
			{
				String[] sptTextToSearch=texttosearch.split(";");
				int textToSearch_Size=sptTextToSearch.length;
				
				for(int i=0;i<textToSearch_Size;i++)
				{
					for(int j=0;j<selDialogList_Size;j++)
					{
						String getValueFromList=selDialogList.get(j).getText();
						if(sptTextToSearch[i].contains(getValueFromList))
						{
							matchFound = true;
							break;
						}
					}
				}
			}
		}
		return matchFound;
	}
}
