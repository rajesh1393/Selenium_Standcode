package com.randtronics.dpm.filemanager.appModules;

import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Device_Page;
import com.randtronics.dpm.filemanager.pageObjects.Email_Page;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.License_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemGroup_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemRole_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemUser_Page;
import com.randtronics.dpm.filemanager.pageObjects.Systeminfo_Page;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class SysMgmt_Action 
{
	public static RepositoryParser parser=new RepositoryParser();
	public static WebElement element;
	public static boolean elestat;
	public static Properties prop=new Properties();

	public enum TEST_RESULT 
	{
		RESULT_SUCCESS
		{
			@Override
			public String toString()
			{
				return "Success";
			}
		},	
		RESULT_FAILURE
		{
			@Override
			public String toString()
			{
				return "Failure";
			}
		},
		RESULT_ERROR
		{
			@Override
			public String toString()
			{
				return "Error";
			}
		}, 
	}


	public static TEST_RESULT execute_addSystemUser(WebDriver driver,
			String sysUserName, String sysUserDesc, String sysUserTelNo,
			String sysUserEmail, String sysUserPwd, String sysUserCnfPwd,
			String allRoles, String prerequisites) throws Exception
	{
		TEST_RESULT testResult;

		if(allRoles=="")
		{
			testResult=addSystemUserAfterPrerequisite(driver, sysUserName, sysUserDesc, sysUserTelNo,sysUserEmail, sysUserPwd, sysUserCnfPwd, allRoles );
		}
		else
		{
			driver.navigate().refresh();
			Home_Page.lnk_SytemManagement(driver).click();
			SystemRole_Page.lnk_SytemManagement_SystemRole(driver).click();
			Thread.sleep(3000);

			String[] splitRole=prerequisites.split(";");
			int splitRole_Length=splitRole.length;
			for(int i=0; i<splitRole_Length; i++)
			{
				driver.navigate().refresh();
				Home_Page.lnk_SytemManagement(driver).click();
				SystemRole_Page.lnk_SytemManagement_SystemRole(driver).click();
				Thread.sleep(3000);

				int a[]=SysMgmt_Action.searchForData(driver, "html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody", splitRole[i], 0);
				if(!(a[0]==1))
				{
					SystemRole_Page.btn_AddSystemRole(driver).click();
					SystemRole_Page.txtbx_RoleName(driver).sendKeys(splitRole[i]);
					SystemRole_Page.btn_SaveRole(driver).click();
				}
			}
			driver.navigate().refresh();
			Home_Page.lnk_SytemManagement(driver).click();
			SystemUser_Page.lnk_SytemManagement_SystemUser(driver).click();
			testResult=addSystemUserAfterPrerequisite(driver, sysUserName, sysUserDesc, sysUserTelNo,sysUserEmail, sysUserPwd, sysUserCnfPwd, allRoles );
		}
		return testResult;
	}

	private static TEST_RESULT addSystemUserAfterPrerequisite(
			WebDriver driver, String sysUserName, String sysUserDesc,
			String sysUserTelNo, String sysUserEmail, String sysUserPwd,
			String sysUserCnfPwd, String allRoles) throws Exception
	{
		SystemUser_Page.btn_AddSystemUser(driver).click();
		Thread.sleep(3000);
		SystemUser_Page.txtbx_SytemUserName(driver).sendKeys(sysUserName);
		SystemUser_Page.txtbx_SytemUserDescription(driver).sendKeys(sysUserDesc);
		SystemUser_Page.txtbx_SytemUserTelephoneNo(driver).sendKeys(sysUserTelNo);
		SystemUser_Page.txtbx_SytemUserEmail(driver).sendKeys(sysUserEmail);
		SystemUser_Page.txtbx_SytemUserPassword(driver).sendKeys(sysUserPwd);
		SystemUser_Page.txtbx_SytemUserConfirmPassword(driver).sendKeys(sysUserCnfPwd);

		if(allRoles!="")
		{
			Select role=new Select(SystemUser_Page.txtbx_SytemUserRole(driver));
			List<WebElement> roleList = role.getOptions();
			int roleList_Size =roleList.size();
			if(roleList_Size!=0)
			{
				String[] selectRole=allRoles.split(";");
				for(int i=0;i<selectRole.length;i++)
				{
					boolean matchFound = false;
					roleList = role.getOptions();
					roleList_Size = roleList.size();

					for(int j=0;j<roleList_Size;j++)
					{
						String getValueFromRoleList=roleList.get(j).getText();
						if(selectRole[i].contains(getValueFromRoleList))
						{
							role.selectByVisibleText(selectRole[i]);
							if(SystemUser_Page.Arw_SytemUserSelectRole(driver).isEnabled())
							{
								SystemUser_Page.Arw_SytemUserSelectRole(driver).click();
								matchFound = true;
								break;
							}
						}
					}
					if(matchFound == false)
					{
						return TEST_RESULT.RESULT_ERROR;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_ERROR;
			}
		}
		SystemUser_Page.btn_SystemUserSave(driver).click();
		Thread.sleep(2000);
		boolean alt_Success=SystemUser_Page.Alt_SystemUser(driver).isDisplayed();
		System.out.println("success alert status:"+alt_Success);
		Thread.sleep(3000);
		boolean chkDialog=driver.findElement(By.id("add-user-dialog")).isDisplayed();
		System.out.println("Add dialog status:"+chkDialog);

		if(alt_Success==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else if(chkDialog==true)
		{
			SystemUser_Page.btn_SystemUserCancel(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
		else 
		{
			return TEST_RESULT.RESULT_ERROR;
		}
	}


	public static void execute_deleteSysUserPrerequisites(WebDriver driver,String allRoles) throws Exception
	{
		driver.navigate().refresh();
		Home_Page.lnk_SytemManagement(driver).click();
		SystemRole_Page.lnk_SytemManagement_SystemRole(driver).click();
		Thread.sleep(3000);

		String[] splitRole = allRoles.split(";");
		int splitRoleLen=splitRole.length;
		for(int i=0;i<splitRoleLen;i++)
		{
			int a[]=SysMgmt_Action.searchForData(driver,"html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody",splitRole[i],0);
			if((a[0]==1))
			{
				execute_deleteSysRole(driver, a[1]);
				driver.navigate().refresh();
				Home_Page.lnk_SytemManagement(driver).click();
				SystemRole_Page.lnk_SytemManagement_SystemRole(driver).click();
				Thread.sleep(3000);
			}
		}
	}

	private static void execute_deleteSysRole(WebDriver driver, int rowNo) 
	{
		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[7]/div")).click();
		driver.findElement(By.linkText("Yes")).click();
	}

	public static void execute_deleteSysUser(WebDriver driver,int rowNo)throws Exception
	{
		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[18]/div")).click();
		driver.findElement(By.linkText("Yes")).click();
	}

	public static TEST_RESULT execute_AfterAddSysUser(
			WebDriver driver, int rowNo, String sysUserName,
			String sysUserDesc, String sysUserTelNo, String sysUserEmail,
			String sysUserPwd, String sysUserCnfPwd, String allRoles)throws Exception
	{
		String getSysUserName=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getSysUserDesc=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[8]/div")).getText();
		String getTelPhoneNo=driver.findElement(By.id("user_telephone")).getAttribute("value");
		System.out.println(getTelPhoneNo);
		String getEmail=driver.findElement(By.id("user_email")).getAttribute("value");
		System.out.println(getEmail);
		String getPwd=driver.findElement(By.id("user_password")).getAttribute("value");
		System.out.println(getPwd);
		String getCnfPwd=driver.findElement(By.id("re_user_password")).getAttribute("value");
		System.out.println(getCnfPwd);


		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[17]/div")).click();

		if(allRoles!="")
		{
			Select role=new Select(SystemUser_Page.txtbx_SytemUserSelectedRole(driver));
			List<WebElement> roleList=role.getOptions();
			int roleList_Size=roleList.size();

			if(roleList_Size!=0)
			{
				String[] selectRole=allRoles.split(";");
				for(int i=0;i<selectRole.length;i++)
				{
					boolean matchFound = false;

					for(int j=0;j<roleList_Size;j++)
					{
						String getValueFromRoleList=roleList.get(j).getText();
						if(selectRole[i].contains(getValueFromRoleList))
						{
							matchFound = true;
							break;
						}
					}
					if(matchFound == false)
					{
						//altmsg="Given role not matched with the list";
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_ERROR;
			}
		}

		SystemUser_Page.btn_SystemUserCancel(driver).click();

		if(getSysUserName.equals(sysUserName) && getSysUserDesc.equals(sysUserDesc) && getTelPhoneNo.equals(sysUserTelNo)
				&& getEmail.equals(sysUserEmail) && getPwd.equals(sysUserPwd) && getCnfPwd.equals(sysUserCnfPwd))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}		
	}


	public static TEST_RESULT execute_viewSysUser(
			WebDriver driver, int rowNo, String sysUserName,
			String sysUserDesc, String sysUserTelNo, String sysUserEmail,
			String sysUserPwd, String sysUserCnfPwd, String allRoles) throws Exception
	{
		String getSysUserName=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getSysUserDesc=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[8]/div")).getText();
		String getTelPhoneNo=driver.findElement(By.id("user_telephone")).getAttribute("value");
		System.out.println(getTelPhoneNo);
		String getEmail=driver.findElement(By.id("user_email")).getAttribute("value");
		System.out.println(getEmail);
		String getPwd=driver.findElement(By.id("user_password")).getAttribute("value");
		System.out.println(getPwd);
		String getCnfPwd=driver.findElement(By.id("re_user_password")).getAttribute("value");
		System.out.println(getCnfPwd);

		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[16]/div")).click();

		if(allRoles!="")
		{
			Select role=new Select(SystemUser_Page.txtbx_SytemUserSelectedRole(driver));
			List<WebElement> roleList=role.getOptions();
			int roleList_Size=roleList.size();

			if(roleList_Size!=0)
			{
				String[] selectRole=allRoles.split(";");
				for(int i=0;i<selectRole.length;i++)
				{
					boolean matchFound = false;

					for(int j=0;j<roleList_Size;j++)
					{
						String getValueFromRoleList=roleList.get(j).getText();
						if(selectRole[i].contains(getValueFromRoleList))
						{
							matchFound = true;
							break;
						}
					}
					if(matchFound == false)
					{
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_FAILURE;
			}
		}


		boolean chkSysUserName=SystemUser_Page.txtbx_SytemUserName(driver).isEnabled();
		System.out.println(chkSysUserName);
		boolean chkSysUserDesc=SystemUser_Page.txtbx_SytemUserDescription(driver).isEnabled();
		System.out.println(chkSysUserDesc);
		boolean chkSysUserTelNo=SystemUser_Page.txtbx_SytemUserTelephoneNo(driver).isEnabled();
		System.out.println(chkSysUserTelNo);
		boolean chkSysUserEmail=SystemUser_Page.txtbx_SytemUserEmail(driver).isEnabled();
		System.out.println(chkSysUserEmail);
		boolean chkSysUserPwd=SystemUser_Page.txtbx_SytemUserPassword(driver).isEnabled();
		System.out.println(chkSysUserPwd);
		boolean chkSysUserConfPwd=SystemUser_Page.txtbx_SytemUserConfirmPassword(driver).isEnabled();
		System.out.println(chkSysUserConfPwd);
		boolean chkAvlRoles=SystemUser_Page.txtbx_SytemUserRole(driver).isEnabled();
		System.out.println(chkAvlRoles);
		boolean chkSelRoles=SystemUser_Page.txtbx_SytemUserSelectedRole(driver).isEnabled();
		System.out.println(chkSelRoles);
		boolean chkSaveBtn=SystemUser_Page.btn_SystemUserSave(driver).isDisplayed();
		System.out.println(chkSaveBtn);
		boolean chkCloseBtn=SystemUser_Page.btn_SystemUserCancel(driver).isDisplayed();
		System.out.println(chkCloseBtn);

		SystemUser_Page.btn_SystemUserCancel(driver).click();

		if(getSysUserName.equals(sysUserName) && getSysUserDesc.equals(sysUserDesc) && getTelPhoneNo.equals(sysUserTelNo)
				&& getEmail.equals(sysUserEmail) && getPwd.equals(sysUserPwd) && getCnfPwd.equals(sysUserCnfPwd)
				&& chkSysUserName==false && chkSysUserDesc==false && chkSysUserTelNo==false && chkSysUserEmail==false
				&& chkSysUserPwd==false && chkSysUserConfPwd==false && chkAvlRoles==false && chkSelRoles==false
				&& chkSaveBtn==false && chkCloseBtn==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}		
	}
	
	public static TEST_RESULT execute_modifySysUser(WebDriver driver,
			int rowNo, String sysUserName,
			String sysUserDesc, String sysUserTelNo, String sysUserEmail,
			String sysUserPwd, String sysUserCnfPwd, String allRoles, String selectedRoles, String allRolesAdd) throws Exception
	{
		TEST_RESULT getResult=execute_beforeModifySysUser(driver, rowNo, allRoles, selectedRoles,allRolesAdd);

		driver.navigate().refresh();

		if(getResult==TEST_RESULT.RESULT_SUCCESS)
		{
			Home_Page.lnk_SytemManagement(driver).click();
			SystemUser_Page.lnk_SytemManagement_SystemUser(driver).click(); 
			Thread.sleep(3000);

			//Click modify icon and edit the records
			driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[17]/div")).click();

			SystemUser_Page.txtbx_SytemUserName(driver).clear();
			SystemUser_Page.txtbx_SytemUserName(driver).sendKeys(sysUserName);
			SystemUser_Page.txtbx_SytemUserDescription(driver).clear();
			SystemUser_Page.txtbx_SytemUserDescription(driver).sendKeys(sysUserDesc);
			SystemUser_Page.txtbx_SytemUserTelephoneNo(driver).clear();
			SystemUser_Page.txtbx_SytemUserTelephoneNo(driver).sendKeys(sysUserTelNo);
			SystemUser_Page.txtbx_SytemUserEmail(driver).clear();
			SystemUser_Page.txtbx_SytemUserEmail(driver).sendKeys(sysUserEmail);
			
			if(sysUserPwd!="")
			{
				SystemUser_Page.txtbx_SytemUserPassword(driver).clear();
				SystemUser_Page.txtbx_SytemUserPassword(driver).sendKeys(sysUserPwd);
			}
			if(sysUserCnfPwd!="")
			{
				SystemUser_Page.txtbx_SytemUserConfirmPassword(driver).clear();
				SystemUser_Page.txtbx_SytemUserConfirmPassword(driver).sendKeys(sysUserCnfPwd);
			}
			
			if(allRoles!="")
			{
				Select role=new Select(SystemUser_Page.txtbx_SytemUserRole(driver));
				List<WebElement> roleList = role.getOptions();
				int roleList_Size =roleList.size();
				if(roleList_Size!=0)
				{
					String[] selectRole=allRoles.split(";");
					for(int i=0;i<selectRole.length;i++)
					{
						boolean matchFound = false;
						roleList = role.getOptions();
						roleList_Size = roleList.size();

						for(int j=0;j<roleList_Size;j++)
						{
							String getValueFromRoleList=roleList.get(j).getText();
							if(selectRole[i].contains(getValueFromRoleList))
							{
								role.selectByVisibleText(selectRole[i]);
								if(SystemUser_Page.Arw_SytemUserSelectRole(driver).isEnabled())
								{
									SystemUser_Page.Arw_SytemUserSelectRole(driver).click();
									matchFound = true;
									break;
								}
							}
						}
						if(matchFound == false)
						{
							return TEST_RESULT.RESULT_ERROR;
						}
					}
				}
				else
				{
					return TEST_RESULT.RESULT_ERROR;
				}
			}

			if(selectedRoles!="")
			{
				Select role=new Select(SystemUser_Page.txtbx_SytemUserSelectedRole(driver));
				List<WebElement> roleList = role.getOptions();
				int roleList_Size =roleList.size();
				
				if(roleList_Size!=0)
				{
					String[] deselectRole=selectedRoles.split(";");
					for(int i=0;i<deselectRole.length;i++)
					{
						boolean matchFound = false;
						roleList = role.getOptions();
						roleList_Size =roleList.size();

						for(int j=0;j<roleList_Size;j++)
						{
							String getValueFromRoleList=roleList.get(j).getText();
							if(deselectRole[i].contains(getValueFromRoleList))
							{
								role.selectByVisibleText(deselectRole[i]);
								Thread.sleep(3000);
								if(SystemUser_Page.Arw_SytemUserUnselectRole(driver).isEnabled())
								{
									SystemUser_Page.Arw_SytemUserUnselectRole(driver).click();
									matchFound = true;
									break;
								}
							}
						}
						if(matchFound == false)
						{
							return TEST_RESULT.RESULT_ERROR;
						}
					}
				}
				else
				{
					return TEST_RESULT.RESULT_ERROR;
				}
			}

			SystemUser_Page.btn_SystemUserSave(driver).click();

			boolean chkSuccess=SystemUser_Page.Alt_SystemUser(driver).isDisplayed();
			System.out.println("Success Message"+chkSuccess);
			Thread.sleep(5000);
			boolean chkDialog=driver.findElement(By.id("add-user-dialog")).isDisplayed();
			System.out.println("System User Dialog"+chkDialog);
			if(chkSuccess==true)
			{
				return TEST_RESULT.RESULT_SUCCESS;
			}
			else if(chkDialog==true)
			{
				SystemUser_Page.btn_SystemUserCancel(driver).click();
				return TEST_RESULT.RESULT_FAILURE;
			}
			else
			{
				return TEST_RESULT.RESULT_ERROR;
			}
		}
		return getResult;
	}

	private static TEST_RESULT execute_beforeModifySysUser(WebDriver driver,
			int rowNo, String allRoles, String selectedRoles, String allRolesAdd) throws Exception
	{
		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[17]/div")).click();

		Select role=new Select(SystemUser_Page.txtbx_SytemUserRole(driver));
		List<WebElement> roleList = role.getOptions();
		int roleList_Size =roleList.size();
		String[] selectRole=allRoles.split(";");

		Select deselectrole=new Select(SystemUser_Page.txtbx_SytemUserSelectedRole(driver));
		List<WebElement> roleList1 = role.getOptions();
		int roleList_Size1 =roleList.size();
		String[] deselectRole=selectedRoles.split(";");

		String[] selectAddRole=allRolesAdd.split(";");

		for(int i=0;i<selectRole.length;i++)
		{
			for(int j=0;j<selectAddRole.length;j++)
			{
				if(selectRole[i].equalsIgnoreCase(selectAddRole[j]))
				{
					SystemUser_Page.btn_SystemUserCancel(driver).click();
					return TEST_RESULT.RESULT_FAILURE;
				}
			}
		}

		for(int i=0;i<deselectRole.length;i++)
		{
			for(int j=0;j<selectRole.length;j++)
			{
				if(deselectRole[i].equalsIgnoreCase(selectRole[j]))
				{
					SystemUser_Page.btn_SystemUserCancel(driver).click();
					return TEST_RESULT.RESULT_FAILURE;
				}
			}
		}


		if(allRoles!="")
		{
			if(roleList_Size!=0)
			{
				for(int i=0;i<selectRole.length;i++)
				{
					boolean matchFoundSelect=false;

					for(int j=0;j<roleList_Size;j++)
					{
						String getValueFromRoleList=roleList.get(j).getText();
						if(selectRole[i].contains(getValueFromRoleList))
						{
							matchFoundSelect=true;
							break;
						}				
					}
					if(matchFoundSelect==false)
					{
						roleList1 = role.getOptions();
						roleList_Size1 =roleList.size();

						for(int k=0;k<roleList_Size1;k++)
						{
							String getValueFromRoleList=roleList1.get(k).getText();
							if(selectRole[i].contains(getValueFromRoleList))
							{						
								deselectrole.selectByVisibleText(selectRole[i]);
								Thread.sleep(3000);

								if(SystemUser_Page.Arw_SytemUserUnselectRole(driver).isEnabled())
								{
									SystemUser_Page.Arw_SytemUserUnselectRole(driver).click();
								}
							}
						}
					}
				}
			}
		}
		if(selectedRoles!="")
		{
			if(roleList_Size1!=0)
			{
				/*String[] deselectRole=userRoleDeselect.split(";");*/
				for(int l=0;l<deselectRole.length;l++)
				{
					boolean matchFoundDeselect=false;

					for(int j=0;j<roleList_Size1;j++)
					{
						String getValueFromRoleList=roleList1.get(j).getText();
						if(deselectRole[l].contains(getValueFromRoleList))
						{
							matchFoundDeselect=true;
							break;
						}				
					}
					if(matchFoundDeselect==false)
					{
						//String[] deselectRole=userRoleDeselect.split(";");
						for(int m=0;m<deselectRole.length;m++)
						{
							roleList = role.getOptions();
							roleList_Size =roleList.size();

							for(int k=0;k<roleList_Size;k++)
							{
								String getValueFromRoleList=roleList.get(k).getText();
								if(deselectRole[m].contains(getValueFromRoleList))
								{
									role.selectByVisibleText(deselectRole[m]);
									Thread.sleep(3000);
									if(SystemUser_Page.Arw_SytemUserSelectRole(driver).isEnabled())
									{
										SystemUser_Page.Arw_SytemUserSelectRole(driver).click();
										break;
									}
								}
							}
						}
					}
				}	
			}
		}
		SystemUser_Page.btn_SystemUserCancel(driver).click();
		return TEST_RESULT.RESULT_SUCCESS;
	}

	public static TEST_RESULT execute_AfterModifyClient(
			WebDriver driver, int rowNo, String sysUserName,
			String sysUserDesc, String sysUserTelNo, String sysUserEmail,
			String sysUserPwd, String sysUserCnfPwd, String allRoles, String selectedRoles) throws Exception
	{
		String getSysUserName=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getSysUserDesc=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[8]/div")).getText();
		
		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[17]/div")).click();

		String getTelPhoneNo=driver.findElement(By.id("user_telephone")).getAttribute("value");
		System.out.println(getTelPhoneNo);
		String getEmail=driver.findElement(By.id("user_email")).getAttribute("value");
		System.out.println(getEmail);
		String getPwd=driver.findElement(By.id("user_password")).getAttribute("value");
		System.out.println(getPwd);
		String getCnfPwd=driver.findElement(By.id("re_user_password")).getAttribute("value");
		System.out.println(getCnfPwd);

		if(allRoles!="")
		{
			Select role=new Select(SystemUser_Page.txtbx_SytemUserSelectedRole(driver));
			List<WebElement> roleList=role.getOptions();
			int roleList_Size=roleList.size();

			if(roleList_Size!=0)
			{
				String[] selectRole=allRoles.split(";");
				for(int i=0;i<selectRole.length;i++)
				{
					boolean matchFound = false;

					for(int j=0;j<roleList_Size;j++)
					{
						String getValueFromRoleList=roleList.get(j).getText();
						if(selectRole[i].contains(getValueFromRoleList))
						{
							matchFound = true;
							break;
						}
					}
					if(matchFound == false)
					{
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_ERROR;
			}
		}


		if(selectedRoles!="")
		{
			Select selRoles=new Select(SystemUser_Page.txtbx_SytemUserRole(driver));
			List<WebElement> roleList = selRoles.getOptions();
			int roleList_Size =roleList.size();
			
			if(roleList_Size!=0)
			{
				String[] deselectRole=selectedRoles.split(";");
				for(int i=0;i<deselectRole.length;i++)
				{
					boolean matchFound = false;

					for(int j=0;j<roleList_Size;j++)
					{
						String getValueFromRoleList=roleList.get(j).getText();
						if(deselectRole[i].contains(getValueFromRoleList))
						{
							matchFound = true;
							break;
						}
					}
					if(matchFound == false)
					{
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_FAILURE;
			}
		}

		SystemUser_Page.btn_SystemUserCancel(driver).click();

		if(getSysUserName.equals(sysUserName) && getSysUserDesc.equals(sysUserDesc) && getTelPhoneNo.equals(sysUserTelNo)
				&& getEmail.equals(sysUserEmail) && getPwd.equals(sysUserPwd) && getCnfPwd.equals(sysUserCnfPwd))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static int[] searchForData(WebDriver driver,String strXpath, String text,int getValueFrmCol)
	{
		int output[] =new int[2];
		WebElement table = driver.findElement(By.xpath(strXpath));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i=0; i<rows.size(); i++)
		{
			List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
			String value = columns.get(getValueFrmCol).getText();
			if (value.equals(text))
			{
				output[0]=1;
				output[1]=i;
				break;
			}
			else
			{
				output[0]=0;
				output[1]=i;           	  
			}
		} 
		return output;
	}


	public static int[] searchForDuplicateData(WebDriver driver,String locatorPath,String UniqueName,int colno)
	{
		int output[] =new int[2];
		int i=0;
		List<WebElement> tr_collection = driver.findElements(By.xpath(locatorPath));
		for(int j=0;j<tr_collection.size();j++)
		{
			System.out.println("j="+j);
			String str2=tr_collection.get(j).getText();
			if(str2.equals(UniqueName))
			{
				i++;
				output[0]=i;
				output[1]=j; 
			}
		}
		return output;  
	}

	public static TEST_RESULT execute_addSystemRole(WebDriver driver,
			String sysRoleName, String sysRoleDesc, String allRights,
			String prerequisites) throws Exception
	{
		SystemRole_Page.btn_AddSystemRole(driver).click();
		Thread.sleep(3000);
		SystemRole_Page.txtbx_RoleName(driver).sendKeys(sysRoleName);
		SystemRole_Page.txtbx_RoleDescription(driver).sendKeys(sysRoleDesc);

		if(allRights!="")
		{
			Select rights=new Select(SystemRole_Page.txtbx_RoleRights(driver));
			List<WebElement> rightsList = rights.getOptions();
			int rightsList_Size =rightsList.size();
			if(rightsList_Size!=0)
			{
				String[] selectRights=allRights.split(";");
				for(int i=0;i<selectRights.length;i++)
				{
					boolean matchFound = false;
					rightsList = rights.getOptions();
					rightsList_Size = rightsList.size();

					for(int j=0;j<rightsList_Size;j++)
					{
						String getValueFromRightsList=rightsList.get(j).getText();
						if(selectRights[i].contains(getValueFromRightsList))
						{
							rights.selectByVisibleText(selectRights[i]);
							Thread.sleep(3000);
							if(SystemRole_Page.Arw_SytemSelectRole(driver).isEnabled())
							{
								SystemRole_Page.Arw_SytemSelectRole(driver).click();
								matchFound = true;
								break;
							}
						}
					}
					if(matchFound == false)
					{
						return TEST_RESULT.RESULT_ERROR;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_ERROR;
			}
		}
		SystemRole_Page.btn_SaveRole(driver).click();
		Thread.sleep(2000);
		boolean alt_Success=SystemRole_Page.Alt_SystemRoleSuccess(driver).isDisplayed();
		System.out.println("success alert status:"+alt_Success);
		Thread.sleep(3000);
		boolean chkDialog=driver.findElement(By.id("add-role-dialog")).isDisplayed();
		System.out.println("Add dialog status:"+chkDialog);

		if(alt_Success==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else if(chkDialog==true)
		{
			SystemRole_Page.btn_CancelRole(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
		else 
		{
			return TEST_RESULT.RESULT_ERROR;
		}
	}

	public static TEST_RESULT execute_AfterAddSysRole(WebDriver driver,
			int rowNo, String sysRoleName, String sysRoleDesc,
			String allRights) throws Exception
	{
		String getSysRoleName=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getSysRoleDesc=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[4]/div")).getText();

		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[5]/div")).click();

		if(allRights!="")
		{
			Select rights=new Select(SystemRole_Page.txtbx_RoleRightsRight(driver));
			List<WebElement> rightsList=rights.getOptions();
			int rightsList_Size=rightsList.size();

			if(rightsList_Size!=0)
			{
				String[] selectRights=allRights.split(";");
				for(int i=0;i<selectRights.length;i++)
				{
					boolean matchFound = false;

					for(int j=0;j<rightsList_Size;j++)
					{
						String getValueFromRightsList=rightsList.get(j).getText();
						if(selectRights[i].contains(getValueFromRightsList))
						{
							matchFound = true;
							break;
						}
					}
					if(matchFound == false)
					{
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_FAILURE;
			}
		}

		SystemRole_Page.btn_CancelRole(driver).click();

		if(getSysRoleName.equals(sysRoleName) && getSysRoleDesc.equals(sysRoleDesc))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}

	}

	public static void execute_deleteSystemRole(WebDriver driver, int rowNo) 
	{
		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[7]/div")).click();
		driver.findElement(By.linkText("Yes")).click();
	}

	public static TEST_RESULT execute_viewSysRole(WebDriver driver,
			int rowNo, String sysRoleName, String sysRoleDesc,
			String allRights) throws Exception
	{
		String getSysRoleName=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getSysRoleDesc=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[4]/div")).getText();

		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[5]/div")).click();

		if(allRights!="")
		{
			Select rights=new Select(SystemRole_Page.txtbx_RoleRightsRight(driver));
			List<WebElement> rightsList=rights.getOptions();
			int rightsList_Size=rightsList.size();

			if(rightsList_Size!=0)
			{
				String[] selectRights=allRights.split(";");
				for(int i=0;i<selectRights.length;i++)
				{
					boolean matchFound = false;

					for(int j=0;j<rightsList_Size;j++)
					{
						String getValueFromRightsList=rightsList.get(j).getText();
						if(selectRights[i].contains(getValueFromRightsList))
						{
							matchFound = true;
							break;
						}
					}
					if(matchFound == false)
					{
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_FAILURE;
			}
		}


		boolean chkSysRoleName=SystemRole_Page.txtbx_RoleName(driver).isEnabled();
		System.out.println(chkSysRoleName);
		boolean chkSysRoleDesc=SystemRole_Page.txtbx_RoleDescription(driver).isEnabled();
		System.out.println(chkSysRoleDesc);
		boolean chkAllRights=SystemRole_Page.txtbx_RoleRights(driver).isEnabled();
		System.out.println(chkAllRights);
		boolean chkAvlRights=SystemRole_Page.txtbx_RoleRightsRight(driver).isEnabled();
		System.out.println(chkAvlRights);
		boolean chkSaveBtn=SystemRole_Page.btn_SaveRole(driver).isDisplayed();
		System.out.println(chkSaveBtn);
		boolean chkCloseBtn=SystemRole_Page.btn_CancelRole(driver).isDisplayed();
		System.out.println(chkCloseBtn);

		SystemRole_Page.btn_CancelRole(driver).click();

		if(chkSysRoleName==false && chkSysRoleDesc==false && chkAllRights==false && chkAvlRights==false 
				&& chkSaveBtn==false && chkCloseBtn==true
				&& getSysRoleName.equals(sysRoleName) && getSysRoleDesc.equals(sysRoleDesc))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}

	}

	public static TEST_RESULT execute_modifySysRole(WebDriver driver,
			int rowNo, String sRoleName, String sRoleDesc, String allRights,
			String selectedRights, String allRightsadd) throws Exception
	{
		TEST_RESULT getResult=execute_beforeModifyClient(driver, rowNo, allRights, selectedRights, allRightsadd);

		driver.navigate().refresh();

		if(getResult==TEST_RESULT.RESULT_SUCCESS)
		{
			Home_Page.lnk_SytemManagement(driver).click();
			SystemRole_Page.lnk_SytemManagement_SystemRole(driver).click(); 
			Thread.sleep(3000);

			//Click modify icon and edit the records
			driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[6]/div")).click();

			SystemRole_Page.txtbx_RoleName(driver).clear();
			SystemRole_Page.txtbx_RoleName(driver).sendKeys(sRoleName);
			SystemRole_Page.txtbx_RoleDescription(driver).clear();
			SystemRole_Page.txtbx_RoleDescription(driver).sendKeys(sRoleDesc);

			if(allRights!="")
			{
				Select rights=new Select(SystemRole_Page.txtbx_RoleRights(driver));
				List<WebElement> rightsList = rights.getOptions();
				int rightsList_Size =rightsList.size();
				if(rightsList_Size!=0)
				{
					String[] selectRights=allRights.split(";");
					for(int i=0;i<selectRights.length;i++)
					{
						boolean matchFound = false;
						rightsList = rights.getOptions();
						rightsList_Size = rightsList.size();

						for(int j=0;j<rightsList_Size;j++)
						{
							String getValueFromRightsList=rightsList.get(j).getText();
							if(selectRights[i].contains(getValueFromRightsList))
							{
								rights.selectByVisibleText(selectRights[i]);
								Thread.sleep(3000);
								if(SystemRole_Page.Arw_SytemSelectRole(driver).isEnabled())
								{
									SystemRole_Page.Arw_SytemSelectRole(driver).click();
									matchFound = true;
									break;
								}
							}
						}
						if(matchFound == false)
						{
							return TEST_RESULT.RESULT_ERROR;
						}
					}
				}
				else
				{
					return TEST_RESULT.RESULT_ERROR;
				}
			}

			if(selectedRights!="")
			{
				Select rights=new Select(SystemRole_Page.txtbx_RoleRightsRight(driver));
				List<WebElement> rightsList = rights.getOptions();
				int rightsList_Size =rightsList.size();
				if(rightsList_Size!=0)
				{
					String[] deselectRights=selectedRights.split(";");
					for(int i=0;i<deselectRights.length;i++)
					{
						boolean matchFound = false;
						rightsList = rights.getOptions();
						rightsList_Size = rightsList.size();

						for(int j=0;j<rightsList_Size;j++)
						{
							String getValueFromRightsList=rightsList.get(j).getText();
							if(deselectRights[i].contains(getValueFromRightsList))
							{
								rights.selectByVisibleText(deselectRights[i]);
								Thread.sleep(3000);
								if(SystemRole_Page.Arw_SytemUnselectRole(driver).isEnabled())
								{
									SystemRole_Page.Arw_SytemUnselectRole(driver).click();
									matchFound = true;
									break;
								}
							}
						}
						if(matchFound == false)
						{
							return TEST_RESULT.RESULT_ERROR;
						}
					}
				}
				else
				{
					return TEST_RESULT.RESULT_ERROR;
				}
			}


			SystemRole_Page.btn_SaveRole(driver).click();

			boolean chkSuccess=SystemRole_Page.Alt_SystemRoleSuccess(driver).isDisplayed();
			System.out.println("Success Message"+chkSuccess);
			Thread.sleep(5000);
			boolean chkDialog=driver.findElement(By.id("add-role-dialog")).isDisplayed();
			System.out.println("System Role Dialog"+chkDialog);
			if(chkSuccess==true)
			{
				return TEST_RESULT.RESULT_SUCCESS;
			}
			else if(chkDialog==true)
			{
				SystemRole_Page.btn_CancelRole(driver).click();
				return TEST_RESULT.RESULT_FAILURE;
			}
			else
			{
				return TEST_RESULT.RESULT_ERROR;
			}
		}
		return getResult;

	}

	private static TEST_RESULT execute_beforeModifyClient(
			WebDriver driver, int rowNo, String allRights,
			String selectedRights, String allRightsadd) throws Exception
	{
		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[6]/div")).click();

		Select rights=new Select(SystemRole_Page.txtbx_RoleRights(driver));
		List<WebElement> rightsList = rights.getOptions();
		int rightsList_Size =rightsList.size();
		String[] selectRights=allRights.split(";");

		Select derights=new Select(SystemRole_Page.txtbx_RoleRightsRight(driver));
		List<WebElement> derightsList = derights.getOptions();
		int derightsList_Size =derightsList.size();
		String[] deselectRights=selectedRights.split(";");

		String[] selectAddRights=allRightsadd.split(";");

		for(int i=0;i<selectRights.length;i++)
		{
			for(int j=0;j<selectAddRights.length;j++)
			{
				if(selectRights[i].equalsIgnoreCase(selectAddRights[j]))
				{
					SystemRole_Page.btn_CancelRole(driver).click();
					return TEST_RESULT.RESULT_FAILURE;
				}
			}
		}

		for(int i=0;i<deselectRights.length;i++)
		{
			for(int j=0;j<selectRights.length;j++)
			{
				if(deselectRights[i].equalsIgnoreCase(selectRights[j]))
				{
					SystemRole_Page.btn_CancelRole(driver).click();
					return TEST_RESULT.RESULT_FAILURE;
				}
			}
		}


		if(allRights!="")
		{
			if(rightsList_Size!=0)
			{
				for(int i=0;i<selectRights.length;i++)
				{
					boolean matchFoundSelect=false;

					for(int j=0;j<rightsList_Size;j++)
					{
						String getValueFromRightsList=rightsList.get(j).getText();
						if(selectRights[i].contains(getValueFromRightsList))
						{
							matchFoundSelect=true;
							break;
						}				
					}
					if(matchFoundSelect==false)
					{
						derightsList = derights.getOptions();
						derightsList_Size =derightsList.size();

						for(int k=0;k<derightsList_Size;k++)
						{
							String getValueFromRightsList=derightsList.get(k).getText();
							if(selectRights[i].contains(getValueFromRightsList))
							{						
								derights.selectByVisibleText(selectRights[i]);

								Thread.sleep(3000);

								if(SystemRole_Page.Arw_SytemUnselectRole(driver).isEnabled())
								{
									SystemRole_Page.Arw_SytemUnselectRole(driver).click();
								}
							}
						}
					}
				}
			}
		}
		if(selectedRights!="")
		{
			if(derightsList_Size!=0)
			{
				/*String[] deselectRole=userRoleDeselect.split(";");*/
				for(int l=0;l<deselectRights.length;l++)
				{
					boolean matchFoundDeselect=false;

					for(int j=0;j<derightsList_Size;j++)
					{
						String getValueFromRightsList=derightsList.get(j).getText();
						if(deselectRights[l].contains(getValueFromRightsList))
						{
							matchFoundDeselect=true;
							break;
						}				
					}
					if(matchFoundDeselect==false)
					{
						//String[] deselectRole=userRoleDeselect.split(";");
						for(int m=0;m<deselectRights.length;m++)
						{
							rightsList = rights.getOptions();
							rightsList_Size =rightsList.size();

							for(int k=0;k<rightsList_Size;k++)
							{
								String getValueFromRightsList=rightsList.get(k).getText();
								if(deselectRights[m].contains(getValueFromRightsList))
								{
									rights.selectByVisibleText(deselectRights[m]);
									Thread.sleep(3000);
									if(SystemRole_Page.Arw_SytemSelectRole(driver).isEnabled())
									{
										SystemRole_Page.Arw_SytemSelectRole(driver).click();
										break;
									}
								}
							}
						}
					}
				}	
			}
		}
		SystemRole_Page.btn_CancelRole(driver).click();
		return TEST_RESULT.RESULT_SUCCESS;
	}

	public static TEST_RESULT execute_AfterModifySysRole(
			WebDriver driver, int rowNo, String sRoleName, String sRoleDesc, String allRights,
			String selectedRights) throws Exception
	{
		String getSysRoleName=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getSysRoleDesc=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[4]/div")).getText();

		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[4]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[6]/div")).click();

		if(allRights!="")
		{
			Select rights=new Select(SystemRole_Page.txtbx_RoleRightsRight(driver));
			List<WebElement> rightsList=rights.getOptions();
			int rightsList_Size=rightsList.size();

			if(rightsList_Size!=0)
			{
				String[] selectRights=allRights.split(";");
				for(int i=0;i<selectRights.length;i++)
				{
					boolean matchFound = false;

					for(int j=0;j<rightsList_Size;j++)
					{
						String getValueFromRightsList=rightsList.get(j).getText();
						if(selectRights[i].contains(getValueFromRightsList))
						{
							matchFound = true;
							break;
						}
					}
					if(matchFound == false)
					{
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_FAILURE;
			}
		}

		if(selectedRights!="")
		{
			Select rights=new Select(SystemRole_Page.txtbx_RoleRights(driver));
			List<WebElement> rightsList=rights.getOptions();
			int rightsList_Size=rightsList.size();

			if(rightsList_Size!=0)
			{
				String[] selectRights=selectedRights.split(";");
				for(int i=0;i<selectRights.length;i++)
				{
					boolean matchFound = false;

					for(int j=0;j<rightsList_Size;j++)
					{
						String getValueFromRightsList=rightsList.get(j).getText();
						if(selectRights[i].contains(getValueFromRightsList))
						{
							matchFound = true;
							break;
						}
					}
					if(matchFound == false)
					{
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
			else
			{
				return TEST_RESULT.RESULT_FAILURE;
			}
		}

		SystemRole_Page.btn_CancelRole(driver).click();

		if(getSysRoleName.equals(sRoleName) && getSysRoleDesc.equals(sRoleDesc))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static TEST_RESULT execute_addSysGroup(WebDriver driver, 
			String sysGroupName, String sysGroupDesc) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		
		SystemGroup_Page.Btn_Add(driver).click();
		Thread.sleep(3000);
		SystemGroup_Page.NameTxtbx_AddPage(driver).sendKeys(sysGroupName);
		SystemGroup_Page.DescTxtbx_AddPage(driver).sendKeys(sysGroupDesc);

		SystemGroup_Page.SaveBtn_AddPage(driver).click();
		Thread.sleep(2000);
		
		boolean alt_Success=SystemGroup_Page.Alt_GroupSuccess(driver).isDisplayed();
		Thread.sleep(3000);
		boolean chkDialog=driver.findElement(By.id(parser.prop.getProperty("Add_Dialog_Group"))).isDisplayed();

		if(alt_Success==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		
		else if(alt_Success==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		else if(chkDialog==true)
		{
			SystemGroup_Page.CancelBtn_AddPage(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
		else 
		{
			return TEST_RESULT.RESULT_ERROR;
		}
	}

	public static void execute_deleteSystemGroup(WebDriver driver, String getTablePath, int rowNo) 
	{
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[10]/div")).click();
		driver.findElement(By.linkText("Yes")).click();
	}

	public static TEST_RESULT execute_AfterAddSysGroup(WebDriver driver, int rowNo, String getTablePath,
			String sysGroupName, String sysGroupDesc) 
	{
		String getSysGroupName=driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[2]/div")).getText();
		String getSysGroupDesc=driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[7]/div")).getText();

		if(getSysGroupName.equals(sysGroupName) && getSysGroupDesc.equals(sysGroupDesc))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	 public static void execute_moveGroup(WebDriver driver, int rowNo, String getTablePath,String toGroup)throws Exception
	 {
		 driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[1]/div")).click();
		 SystemGroup_Page.Btn_Move(driver).click();
		 WebElement test=SystemGroup_Page.drpdwn_Move(driver);
		 selectElement_Group(driver, test, toGroup, "option");
		 SystemGroup_Page.btn_SaveMove(driver).click(); 
	 }
	 
	 public static void selectElement_Group(WebDriver driver,WebElement ele,String textToFind, String tagName) throws Exception
	 {
		 Thread.sleep(5000);
		 List<WebElement> optionsToSelect = ele.findElements(By.tagName(tagName));
		 int tagLength=optionsToSelect.size();
		 System.out.println("tag length is : "+tagLength);
		 if(tagLength!=0)
		 {
			 for(WebElement option : optionsToSelect)
			 {
				 String str=option.getText();
				 String sptPath=str.substring(str.lastIndexOf("/")+1,str.length());
				 if(sptPath.equals(textToFind))
				 {
					 Thread.sleep(3000);
					 Actions action = new Actions(driver);
					 action.moveToElement(option).doubleClick().build().perform();
					 Thread.sleep(5000);
					 break;
				 }
			 }
		 }
	 }

	public static TEST_RESULT execute_modifySysGroup(WebDriver driver, int rowNo, String getTablePath,
			String sysGroupName, String sysGroupDesc) throws Exception
	{
		//Click modify icon and edit the records
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[9]/div")).click();

		SystemGroup_Page.NameTxtbx_AddPage(driver).clear();
		SystemGroup_Page.NameTxtbx_AddPage(driver).sendKeys(sysGroupName);

		SystemGroup_Page.DescTxtbx_AddPage(driver).clear();
		SystemGroup_Page.DescTxtbx_AddPage(driver).sendKeys(sysGroupDesc);

		SystemGroup_Page.SaveBtn_AddPage(driver).click();

		boolean alt_Success=SystemGroup_Page.Alt_GroupSuccess(driver).isDisplayed();
		System.out.println("success alert status:"+alt_Success);
		Thread.sleep(3000);
		boolean chkDialog=driver.findElement(By.id(parser.prop.getProperty("Add_Dialog_Group"))).isDisplayed();
		System.out.println("Dialog status:"+chkDialog);
		
		if(alt_Success==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else if(alt_Success==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		else if(chkDialog==true)
		{
			SystemGroup_Page.CancelBtn_AddPage(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
		else
		{
			return TEST_RESULT.RESULT_ERROR;
		}
	
	}

	public static TEST_RESULT execute_AfterModifySysGroup(WebDriver driver,
			int rowNo, String getTablePath, String sysGroupName, String sysGroupDesc) 
	{
		String getSysGroupName=driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[2]/div")).getText();
		String getSysGroupDesc=driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[7]/div")).getText();

		if(getSysGroupName.equals(sysGroupName) && getSysGroupDesc.equals(sysGroupDesc))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}
	
	public static TEST_RESULT execute_Email(WebDriver driver, String IPAddress,
			String port, String email_Sent_From, String Authentication_Req,
			String Email_Username, String Email_Password,
			String Password_Authentication, String encryption) throws Exception
	{
		TEST_RESULT result=null;
		parser.RepositoryParser(Constants.path_ObjectRepo);

		Email_Page.txt_IPaddress(driver).clear();
		Email_Page.txt_IPaddress(driver).sendKeys(IPAddress);
		Thread.sleep(2000);

		Email_Page.txt_Port(driver).clear();
		Email_Page.txt_Port(driver).sendKeys(port);
		Thread.sleep(2000);

		Email_Page.txt_sent_from(driver).clear(); 
		Email_Page.txt_sent_from(driver).sendKeys(email_Sent_From); 
		Thread.sleep(2000);

		if(Authentication_Req.equals("Yes"))
		{
			Email_Page.list_Authentication(driver).click();
			Select se =new Select(Email_Page.list_Authentication(driver));
			se.selectByValue("1");
			Thread.sleep(2000);

			Email_Page.txt_Email_Username(driver).clear();
			Email_Page.txt_Email_Username(driver).sendKeys(Email_Username);	
			Thread.sleep(2000);

			Email_Page.txt_Email_Password(driver).clear();
			Email_Page.txt_Email_Password(driver).sendKeys(Email_Password);
			Thread.sleep(2000);

			Email_Page.list_Encryption(driver).sendKeys(encryption);
			Thread.sleep(2000);

			Email_Page.lnk_Email_Save(driver).click();
			Thread.sleep(2000);

			result=Result(driver);
		}
		else 
		{
			Email_Page.list_Authentication(driver).click();
			Select se =new Select(Email_Page.list_Authentication(driver));
			se.selectByValue("0");
			Thread.sleep(2000);
			boolean ChkEmailUsername = Email_Page.txt_Email_Username(driver).isEnabled();
			boolean ChkEmailPassword = Email_Page.txt_Email_Password(driver).isEnabled();
			if(ChkEmailUsername == true && ChkEmailPassword == true)
			{
				return TEST_RESULT.RESULT_FAILURE;

			}
			else
			{
				Email_Page.list_Encryption(driver).sendKeys(encryption);
				Thread.sleep(2000);

				Email_Page.lnk_Email_Save(driver).click();
				Thread.sleep(2000);
				result=Result(driver);	
			}
		}
		return result;
	}
	
	private static TEST_RESULT Result(WebDriver driver)throws Exception 
	{
		String v_msg_in_alert= Email_Page.alt_message(driver).getText();      
		String v_actualTitle = "The email server config information saved successfully.";

		if(v_actualTitle.equals(v_msg_in_alert))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static TEST_RESULT execute_moveDevice(WebDriver driver, int rowNo,
			String getTablePath, String deviceName,
			String moveDeviceTo, String prerequisites) throws Exception
	{
		TEST_RESULT result=null;
		if(prerequisites=="")
		{
			result=moveDeviceAfterPre(driver,rowNo,getTablePath,deviceName,moveDeviceTo);
		}
		else
		{
			driver.navigate().refresh();
			Home_Page.lnk_SytemManagement(driver).click();
			SystemGroup_Page.lnk_SytemManagement_Group(driver).click();
			Thread.sleep(3000);
			int a[]=SysMgmt_Action.searchForData(driver, "html/body/div[2]/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[2]/div[6]/div/div/div/div[2]/div[2]/div[2]/table/tbody", prerequisites,1);
			if(!(a[0]==1))
			{
				SystemGroup_Page.Btn_Add(driver).click();
				SystemGroup_Page.NameTxtbx_AddPage(driver).sendKeys(prerequisites);
				SystemGroup_Page.SaveBtn_AddPage(driver).click();
			}
			driver.navigate().refresh();
			Home_Page.lnk_SytemManagement(driver).click();
			Device_Page.lnk_Device(driver).click();

			result=moveDeviceAfterPre(driver,rowNo,getTablePath,deviceName,moveDeviceTo);
		}
		return result;
	}
	
	private static TEST_RESULT moveDeviceAfterPre(WebDriver driver, int rowNo,
			String getTablePath, String deviceName, String moveDeviceTo) throws Exception
	{
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[1]/div")).click();
		Device_Page.btn_Move(driver).click();
		WebElement test=Device_Page.lst_Select(driver);
		selectElement_Group(driver, test, moveDeviceTo, "option");
		Device_Page.btn_Save(driver).click(); 
		Thread.sleep(2000);
		boolean alt_Success=Device_Page.alt_devicemove(driver).isDisplayed();
		System.out.println("success alert status:"+alt_Success);
        boolean altpresent=isAlertPresent(driver);
        System.out.println(altpresent);
        
		if(alt_Success==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else if(isAlertPresent(driver))
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			Device_Page.btn_Cancel(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
		else 
		{
			return TEST_RESULT.RESULT_ERROR;
		}	
	}

	public static boolean isAlertPresent(WebDriver driver)
	{
		try
		{
			driver.switchTo().alert();
			return true;
		}
		catch(NoAlertPresentException ex)
		{
			return false;
		}
	}

	public static void deleteDevicePrerequisites(WebDriver driver,
			String prerequisites) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		String getUserTablePath=parser.prop.getProperty("Group_Table_Path");
		driver.navigate().refresh();
		Home_Page.lnk_SytemManagement(driver).click();
		SystemGroup_Page.lnk_SytemManagement_Group(driver).click();
		Thread.sleep(3000);

		if(prerequisites!="")
		{			
			int a[]=SysMgmt_Action.searchForData(driver, getUserTablePath, prerequisites, 1);
			if(a[0]==1)
			{
				driver.findElement(By.xpath(getUserTablePath+"/tr["+(a[1]+1)+"]/td[10]/div")).click();
				driver.findElement(By.linkText("Yes")).click();			
			}
		}
	}

	public static void deleteMovedDevice(WebDriver driver, String getTablePath,
			int rowNo) 
	{
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[7]/div")).click();
		driver.findElement(By.linkText("OK")).click();
	}
	
	
	public static TEST_RESULT execute_requestLicense(WebDriver driver,String EmailAddress)throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);

		Pattern pattern;
		Matcher matcher;
		boolean chkemail;

		String EMAIL_PATTERN =
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		License_Page.lnk_License(driver).click();
		Thread.sleep(3000);
		License_Page.txt_LicenseEmail(driver).sendKeys(EmailAddress);
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(EmailAddress);
		if(matcher.matches())
		{
			chkemail=true;
		}
		else
		{
			chkemail=false;
		}

		License_Page.btn_generateCode(driver).click();

		Boolean chk=License_Page.btn_saveasFile(driver).isEnabled();
		if((chk==true) && (chkemail==true))
		{
			//Click on Download File link to download file.
			License_Page.btn_saveasFile(driver).click();			
			//Execute Script To Download File.exe file to run AutoIt script. File location = C:\\AutoIT\\
			Runtime.getRuntime().exec(parser.prop.getProperty("filedownload"));
			Thread.sleep(2000);
//			License_Page.btn_LicenseClose(driver).click();
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			License_Page.btn_LicenseClose(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static TEST_RESULT execute_importLicense(WebDriver driver)throws Exception 
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);

		License_Page.btn_importLicense(driver).click();
		License_Page.btn_licenseUploadBrowse(driver).click();
		Thread.sleep(5000);
		Runtime.getRuntime().exec(parser.prop.getProperty("fileupload"));
		Thread.sleep(10000);
		License_Page.btn_uploadLicense(driver).click();
		Thread.sleep(5000);
		String getText=License_Page.alt_License(driver).getText();
		if(getText.equals("License imported successfully."))
		{
			License_Page.btn_reLogin(driver).click();
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			License_Page.btn_closeUploadLicense(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
	}
	
	
	public static TEST_RESULT execute_backupsystem(WebDriver driver,String ProtectPwd,String ConfirmPwd)throws Exception
	{
		TEST_RESULT testResult=null;
		parser.RepositoryParser(Constants.path_ObjectRepo);
		String getfiledownload=parser.prop.getProperty("filedownload");
		Systeminfo_Page.lnk_Backup_Tab(driver).click();
		Systeminfo_Page.txtbx_Protect_pwd(driver).clear();
		Systeminfo_Page.txtbx_Protect_pwd(driver).sendKeys(ProtectPwd);
		Systeminfo_Page.txtbx_Confirm_pwd(driver).clear();
		Systeminfo_Page.txtbx_Confirm_pwd(driver).sendKeys(ConfirmPwd);

		Boolean chk=Systeminfo_Page.btn_BackupRestore(driver).isEnabled();
		if(chk==true)
		{
			
			//Click on Backup File link to download file.
			Systeminfo_Page.btn_BackupRestore(driver).click();			
			//Execute Script To Download File.exe file to run AutoIt script. File location = C:\\AutoIT\\
			Runtime.getRuntime().exec(getfiledownload);
			Thread.sleep(2000);
			//Systeminfo_Page.btn_BackupRestoreClose(driver).click();
			boolean chkdialog=driver.findElement(By.id("backup_dialog")).isDisplayed();
			if(chkdialog==false)
			{
				testResult= TEST_RESULT.RESULT_SUCCESS;
			}
			else
			{
				Systeminfo_Page.btn_BackupRestoreClose(driver).click();
				testResult= TEST_RESULT.RESULT_FAILURE;
			}
		}
		return testResult;
	}
	
	public static TEST_RESULT execute_Restoresystem(WebDriver driver, String ProtectPwd)throws Exception 
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);			
		String getfileupload=parser.prop.getProperty("fileupload");
		Systeminfo_Page.lnk_Restore(driver).click();
		Thread.sleep(5000);
		Systeminfo_Page.lnk_Restore_Browse(driver).click();
		Thread.sleep(10000);
		Runtime.getRuntime().exec(getfileupload);
		Thread.sleep(10000);
		Systeminfo_Page.txtbx_Restore_Protectpwd(driver).sendKeys(ProtectPwd);
		Thread.sleep(5000);
		Systeminfo_Page.btn_BackupRestore(driver).click();
		boolean getdialog=Systeminfo_Page.Restore_dialog(driver).isDisplayed();
		if(getdialog==false)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			Systeminfo_Page.btn_BackupRestoreClose(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
	}


}


