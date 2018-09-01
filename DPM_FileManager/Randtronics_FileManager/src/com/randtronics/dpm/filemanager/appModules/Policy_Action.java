package com.randtronics.dpm.filemanager.appModules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.randtronics.dpm.filemanager.appModules.Policy_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.EventConfig_Page;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.PolicyAppTemplate_Page;
import com.randtronics.dpm.filemanager.pageObjects.PolicyFileFolder_Page;
import com.randtronics.dpm.filemanager.pageObjects.PolicyRole_Page;
import com.randtronics.dpm.filemanager.pageObjects.PolicyTemplate_Page;
import com.randtronics.dpm.filemanager.pageObjects.PolicyUser_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemUser_Page;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class Policy_Action 
{
	public static RepositoryParser parser=new RepositoryParser();

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


	public static TEST_RESULT execute_addPolicyUser(WebDriver driver,
			String policyUserName, String policyUserDesc,
			String policyUserAllRoles,
			String prerequisites) throws Exception
	{
		TEST_RESULT testResult;
		parser.RepositoryParser(Constants.path_ObjectRepo);
		String getUserTablePath=parser.prop.getProperty("PolicyRole_TablePath");

		if(policyUserAllRoles=="")
		{
			testResult=addPolicyUserAfterPrerequisite(driver, policyUserName, policyUserDesc, policyUserAllRoles);
		}
		else
		{

			getRefresh_PolicyRole(driver);

			String[] selectRole=prerequisites.split(";");
			for(int i=0;i<selectRole.length;i++)
			{
				getRefresh_PolicyRole(driver);
				int a[]=SysMgmt_Action.searchForData(driver, getUserTablePath, selectRole[i], 0);

				if(!(a[0]==1))
				{
					PolicyRole_Page.btn_AddRole(driver).click();
					PolicyRole_Page.txt_Name(driver).sendKeys(selectRole[i]);
					PolicyRole_Page.btn_Save(driver).click();

				}
			}
			driver.navigate().refresh();
			Home_Page.lnk_Policy(driver).click();
			PolicyUser_Page.lnk_UserTab(driver).click();
			testResult=addPolicyUserAfterPrerequisite(driver, policyUserName, policyUserDesc, policyUserAllRoles);
		}
		return testResult;
	}

	private static TEST_RESULT addPolicyUserAfterPrerequisite(WebDriver driver,
			String policyUserName, String policyUserDesc,
			String policyUserAllRoles) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		Thread.sleep(2000);
		PolicyUser_Page.btn_AddUser(driver).click();
		PolicyUser_Page.txt_Username(driver).sendKeys(policyUserName);
		PolicyUser_Page.txt_UserDesc(driver).sendKeys(policyUserDesc);

		if(policyUserAllRoles!="")
		{
			Select role=new Select(PolicyUser_Page.lst_AllRoles(driver));
			List<WebElement> roleList = role.getOptions();
			int roleList_Size =roleList.size();
			if(roleList_Size!=0)
			{
				String[] selectRole=policyUserAllRoles.split(";");
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
							if(PolicyUser_Page.arw_Select(driver).isEnabled())
							{
								PolicyUser_Page.arw_Select(driver).click();
								matchFound = true;
								break;
							}
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

		PolicyUser_Page.btn_SaveUser(driver).click();
		Thread.sleep(2000);
		boolean alt_Success=PolicyUser_Page.alt_User(driver).isDisplayed();
		System.out.println("success alert status:"+alt_Success);
		Thread.sleep(3000);
		boolean chkDialog=driver.findElement(By.id(parser.prop.getProperty("Add_Dialog"))).isDisplayed();
		System.out.println("Add dialog status:"+chkDialog);

		if(alt_Success==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else if(chkDialog==true)
		{
			PolicyUser_Page.btn_CancelUser(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
		else 
		{
			return TEST_RESULT.RESULT_ERROR;
		}
	}

	private static void getRefresh_PolicyRole(WebDriver driver) throws Exception
	{
		driver.navigate().refresh();
		Home_Page.lnk_Policy(driver).click();
	}

	public static void execute_deletePolicyUserPrerequisites(WebDriver driver,
			String prerequisites) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		String getUserTablePath=parser.prop.getProperty("PolicyRole_TablePath");
		if(prerequisites!="")
		{
			getRefresh_PolicyRole(driver);
			String[] selectRole=prerequisites.split(";");
			for(int i=0;i<selectRole.length;i++)
			{
				int a[]=SysMgmt_Action.searchForData(driver, getUserTablePath, selectRole[i], 0);
				if(a[0]==1)
				{
					execute_deletePolicyRole(driver, a[1],getUserTablePath);
					getRefresh_PolicyRole(driver);
				}
			}
		}
	}

	private static void execute_deletePolicyRole(WebDriver driver, int rowNo,String tableXpath) 
	{
		driver.findElement(By.xpath(tableXpath+"/tr["+rowNo+"]/td[7]/div")).click();
		driver.findElement(By.linkText("Yes")).click();
	}

	public static TEST_RESULT execute_AfterAddPolicyUser(WebDriver driver,
			int rowNo,String tablePath, String policyUserName, String policyUserDesc,
			String policyUserAllRoles, String policyUserSelRoles) throws Exception
	{
		String getPolicyUserName=driver.findElement(By.xpath(tablePath+"/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getPolicyUserDesc=driver.findElement(By.xpath(tablePath+"/tr["+(rowNo+1)+"]/td[8]/div")).getText();

		driver.findElement(By.xpath(tablePath+"/tr["+(rowNo+1)+"]/td[16]/div")).click();

		if(policyUserAllRoles!="")
		{
			Select role=new Select(PolicyUser_Page.lst_SelectedRoles(driver));
			List<WebElement> roleList=role.getOptions();
			int roleList_Size=roleList.size();

			if(roleList_Size!=0)
			{
				String[] selectRole=policyUserAllRoles.split(";");
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

		PolicyUser_Page.btn_CancelUser(driver).click();

		if(getPolicyUserName.equals(policyUserName) && getPolicyUserDesc.equals(policyUserDesc))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}		
	}

	public static void execute_deletePolicyUser(WebDriver driver, int rowNo,
			String getTablePath) 
	{
		driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[18]/div")).click();
		driver.findElement(By.linkText("Yes")).click();
	}

	public static TEST_RESULT execute_modifyPolicyUser(WebDriver driver,int rowNo,String getTablePath,String userName,String userDesc,String userRoleSelect, String userRoleDeselect, String userRoleAddSelect)throws Exception
	{
		TEST_RESULT getResult=execute_beforeModifyPolicyUser(driver, rowNo, getTablePath, userRoleSelect, userRoleDeselect,userRoleAddSelect);
		driver.navigate().refresh();

		if(getResult==TEST_RESULT.RESULT_SUCCESS)
		{
			Home_Page.lnk_Policy(driver).click();
			PolicyUser_Page.lnk_UserTab(driver).click(); Thread.sleep(3000);
			//Click modify icon and edit the records
			driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[17]/div")).click();
			PolicyUser_Page.txt_Username(driver).clear();
			PolicyUser_Page.txt_Username(driver).sendKeys(userName);
			PolicyUser_Page.txt_UserDesc(driver).clear();
			PolicyUser_Page.txt_UserDesc(driver).sendKeys(userDesc);
			if(userRoleSelect!="")
			{
				Select role=new Select(PolicyUser_Page.lst_AllRoles(driver));
				List<WebElement> roleList=role.getOptions();;
				int roleList_Size=roleList.size();
				if(roleList_Size!=0)
				{
					String[] selectRole=userRoleSelect.split(";");
					for(int i=0;i<selectRole.length;i++)
					{
						role.selectByVisibleText(selectRole[i]);
						if(PolicyUser_Page.arw_Select(driver).isEnabled())
						{
							PolicyUser_Page.arw_Select(driver).click();
						}
					}
				}
			}

			if(userRoleDeselect!="")
			{
				Select role2=new Select(PolicyUser_Page.lst_SelectedRoles(driver));
				List<WebElement> roleList2=role2.getOptions();;
				int roleList_Size=roleList2.size();
				if(roleList_Size!=0)
				{
					String[] deselectRole=userRoleDeselect.split(";");
					for(int i=0;i<deselectRole.length;i++)
					{
						role2.selectByVisibleText(deselectRole[i]);
						if(PolicyUser_Page.arw_Deselect(driver).isEnabled())
						{
							Thread.sleep(3000);
							PolicyUser_Page.arw_Deselect(driver).click();
						}
					}
				}
			}
			PolicyUser_Page.btn_SaveUser(driver).click();
		}

		return getResult;
	}

	public static TEST_RESULT execute_beforeModifyPolicyUser(WebDriver driver, int rowNo, String getTablePath,String userRoleSelect, String userRoleDeselect, String userRoleAddSelect)throws Exception
	{
		driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[17]/div")).click();

		Select role=new Select(PolicyUser_Page.lst_AllRoles(driver));
		List<WebElement> roleList=role.getOptions();;
		int roleList_Size=roleList.size();
		Thread.sleep(3000);
		String[] selectRole=userRoleSelect.split(";");

		Select deselectrole=new Select(PolicyUser_Page.lst_SelectedRoles(driver));
		List<WebElement> roleList1=deselectrole.getOptions();
		int roleList_Size1=roleList1.size(); 
		Thread.sleep(3000);
		String[] deselectRole=userRoleDeselect.split(";");

		String[] selectAddRole=userRoleAddSelect.split(";");

		for(int i=0;i<selectRole.length;i++)
		{
			for(int j=0;j<selectAddRole.length;j++)
			{
				if(selectRole[i].equalsIgnoreCase(selectAddRole[j]))
				{
					PolicyUser_Page.btn_CancelUser(driver).click();
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
					PolicyUser_Page.btn_CancelUser(driver).click();
					return TEST_RESULT.RESULT_FAILURE;
				}
			}
		}

		if(userRoleSelect!="")
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
						roleList1 = deselectrole.getOptions();
						roleList_Size1 = roleList1.size();
						for(int k=0;k<roleList_Size1;k++)
						{
							String getValueFromRoleList=roleList1.get(k).getText();
							if(selectRole[i].contains(getValueFromRoleList))
							{						
								deselectrole.selectByVisibleText(selectRole[i]);
								if(PolicyUser_Page.arw_Deselect(driver).isEnabled())
								{
									PolicyUser_Page.arw_Deselect(driver).click();
								}
							}
						}
					}
				}
			}
		}
		if(userRoleDeselect!="")
		{
			if(roleList_Size1!=0)
			{
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
						for(int m=0;m<deselectRole.length;m++)
						{
							roleList = role.getOptions();
							roleList_Size = roleList.size();
							for(int k=0;k<roleList_Size;k++)
							{
								String getValueFromRoleList=roleList.get(k).getText();
								if(deselectRole[m].contains(getValueFromRoleList))
								{
									role.selectByVisibleText(deselectRole[m]);
									if(PolicyUser_Page.arw_Select(driver).isEnabled())
									{
										PolicyUser_Page.arw_Select(driver).click();
										break;
									}
								}
							}
						}
					}
				}	
			}
		}
		PolicyUser_Page.btn_SaveUser(driver).click();
		return TEST_RESULT.RESULT_SUCCESS;
	}

	public static TEST_RESULT execute_AfterModifyPolicyUser(WebDriver driver,int rowNo,String getTablePath,String userName,String userDesc,String userRoleSelect, String userRoleDeselect)throws Exception
	{
		String getUserName=driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[1]/div")).getText();
		String getUserDesc=driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[8]/div")).getText();
		//click Modify icon
		Thread.sleep(5000);
		driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[17]/div")).click();
		if(userRoleSelect!="")
		{
			Select role=new Select(PolicyUser_Page.lst_SelectedRoles(driver));
			List<WebElement> roleList=role.getOptions();;
			int roleList_Size=roleList.size();
			if(roleList_Size!=0)
			{
				String[] selectRole=userRoleSelect.split(";");
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
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
		}

		if(userRoleDeselect!="")
		{
			Select role=new Select(PolicyUser_Page.lst_AllRoles(driver));
			List<WebElement> roleList=role.getOptions();;
			int roleList_Size=roleList.size();
			if(roleList_Size!=0)
			{
				String[] deselectRole=userRoleDeselect.split(",");
				for(int i=0;i<deselectRole.length;i++)
				{
					boolean matchFoundDeselect=false;
					for(int j=0;j<roleList_Size;j++)
					{
						String getValueFromRoleList=roleList.get(j).getText();
						if(deselectRole[i].contains(getValueFromRoleList))
						{
							matchFoundDeselect=true;
							break;
						}
					}
					if(matchFoundDeselect==false)
					{
						return TEST_RESULT.RESULT_FAILURE;
					}
				}
			}
		}
		PolicyUser_Page.btn_CancelUser(driver).click();
		if(getUserName.equals(userName)&& getUserDesc.equals(userDesc))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static TEST_RESULT execute_viewPolicyUser(WebDriver driver, int rowNo,
			String getTablePath, String policyUserName, String policyUserDesc,
			String policyUserAllRoles) throws Exception
	{
		String getUserName=driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[1]/div")).getText();
		String getUserDesc=driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[8]/div")).getText();

		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[16]/div")).click();

		if(policyUserAllRoles!="")
		{
			Select role=new Select(PolicyUser_Page.lst_SelectedRoles(driver));
			List<WebElement> roleList=role.getOptions();
			int roleList_Size=roleList.size();

			if(roleList_Size!=0)
			{
				String[] selectRole=policyUserAllRoles.split(";");
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

		boolean chkUserName=PolicyUser_Page.txt_Username(driver).isEnabled();
		boolean chkUserDesc=PolicyUser_Page.txt_UserDesc(driver).isEnabled();
		boolean chkAllRoles=PolicyUser_Page.lst_AllRoles(driver).isEnabled();
		boolean chkSelectedRoles=PolicyUser_Page.lst_SelectedRoles(driver).isEnabled();

		PolicyUser_Page.btn_CancelUser(driver).click();

		if(chkUserName==false && chkUserDesc==false && chkAllRoles==false && chkSelectedRoles==false && 
				getUserName.equals(policyUserName) && getUserDesc.equals(policyUserDesc))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}		
	}

	public static TEST_RESULT execute_addPolicyRole(WebDriver driver, String RoleName,
			String RoleDesc) throws Exception
	{
		PolicyRole_Page.btn_AddRole(driver).click();
		PolicyRole_Page.txt_Name(driver).sendKeys(RoleName);
		PolicyRole_Page.txt_Desc(driver).sendKeys(RoleDesc);
		PolicyRole_Page.btn_Save(driver).click();
		Thread.sleep(2000);
		//boolean alt_Success=PolicyRole_Page.alt_RolePolicy(driver).isDisplayed();				
		String alt_Success_msg=PolicyRole_Page.alt_RolePolicy(driver).getText();
		System.out.println(alt_Success_msg);
		//System.out.println("success alert status:"+alt_Success);
		Thread.sleep(3000);
		boolean chkDialog=driver.findElement(By.id("oRoleInfRespOverlay")).isDisplayed();
		System.out.println("Add dialog status:"+chkDialog);

		if(alt_Success_msg.equals("Role added successfully."))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else if((alt_Success_msg.equals("Error adding the role.")))
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		else if(chkDialog==true)
		{
			PolicyRole_Page.btn_Cancel(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
		else 
		{
			return TEST_RESULT.RESULT_ERROR;
		}
	}

	public static TEST_RESULT execute_AfterAddPolicyRole(WebDriver driver, int rowNo, String getTablePath, String RoleName, String RoleDesc) throws Exception
	{
		String getRoleName=driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getRoleDesc=driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[4]/div")).getText();

		if(getRoleName.equals(RoleName) && getRoleDesc.equals(RoleDesc))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static void execute_deletePolicyRole(WebDriver driver,String getTablePath, int rowNo) 
	{
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[7]/div")).click();
		driver.findElement(By.linkText("Yes")).click();			
	}


	public static TEST_RESULT execute_viewPolicyRole(WebDriver driver, int rowNo, String getTablePath,String RoleName,
			String RoleDesc) throws Exception
	{
		String getRoleName=driver.findElement(By.xpath(getTablePath+ "/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getRoleDesc=driver.findElement(By.xpath(getTablePath+ "/tr["+(rowNo+1)+"]/td[4]/div")).getText();
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[5]/div")).click();
		boolean chkRoleName=PolicyRole_Page.txt_Name(driver).isEnabled();
		boolean chkRoleDesc=PolicyRole_Page.txt_Desc(driver).isEnabled();
		boolean chkSaveBtn=PolicyRole_Page.btn_Save(driver).isDisplayed();
		boolean chkCloseBtn=PolicyRole_Page.btn_Cancel(driver).isDisplayed();
		PolicyRole_Page.btn_Cancel(driver).click();

		if(getRoleName.equals(RoleName) && getRoleDesc.equals(RoleDesc) && chkRoleName==false && chkRoleDesc==false && chkSaveBtn==false && chkCloseBtn==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static void getDeviceGroup(WebDriver driver,String MoveDeviceTo)throws Exception
	{
		WebElement wb=driver.findElement(By.xpath(".//*[@id='group_tree']/li"));
		WebElement wb1=wb.findElement(By.tagName("ul"));
		List<WebElement> lst=wb1.findElements(By.tagName("li"));
		for(int i=0;i<lst.size();i++)
		{
			List<WebElement> lst1=lst.get(i).findElements(By.tagName("div"));

			for(int j=0;j<lst1.size();j++)
			{
				List<WebElement> lst2=lst1.get(j).findElements(By.tagName("span"));
				int lst1_size=lst1.size();
				System.out.println(lst1_size);
				int lst2_size=lst2.size();
				System.out.println(lst2_size);
				for(WebElement option : lst2)
				{
					String txt=option.getText();
					if(txt.equals(MoveDeviceTo))
					{
						Thread.sleep(3000);
						Actions action = new Actions(driver);
						action.moveToElement(option).doubleClick().build().perform();
						break;
					}
				}
			}
		}
	}

	public static TEST_RESULT execute_PolicyApptemplate(WebDriver driver, 
			String AppTemplateNme, String AppTemplateProcess, String AppTemplateOS, String AppTemplateHashCode, 
			String AppTemplateOperation, String AppTemplateEncryptFletype) throws Exception
	{           
		PolicyAppTemplate_Page.lnk_AppTemplate(driver).click();
		PolicyAppTemplate_Page.lnk_Add(driver).click();
		PolicyAppTemplate_Page.txt_Name(driver).sendKeys(AppTemplateNme);
		PolicyAppTemplate_Page.txt_Process(driver).sendKeys(AppTemplateProcess);
		if(AppTemplateOS.equals("windows"))
		{
			PolicyAppTemplate_Page.dropdownList_OS(driver).click();
			Select se =new Select(PolicyAppTemplate_Page.dropdownList_OS(driver));
			se.selectByValue("1");
			Thread.sleep(2000);
		}
		else
		{
			PolicyAppTemplate_Page.dropdownList_OS(driver).click();
			Select se =new Select(PolicyAppTemplate_Page.dropdownList_OS(driver));
			se.selectByValue("2");
			Thread.sleep(2000);
		}
		PolicyAppTemplate_Page.txt_Hashcode(driver).sendKeys(AppTemplateHashCode);            
		switch (AppTemplateOperation) 
		{                                   
		case "Decrypt":
			PolicyAppTemplate_Page.Chkbox_Operation_Decrypt(driver).click();
			break;
		case "Encrypt":
			PolicyAppTemplate_Page.Chkbox_Operation_Encrypt(driver).click();
			break;
		case "Decrypt;Encrypt": 
			PolicyAppTemplate_Page.Chkbox_Operation_Decrypt(driver).click();
			PolicyAppTemplate_Page.Chkbox_Operation_Encrypt(driver).click();
		}
		PolicyAppTemplate_Page.lnk_EncryptFleType_Add(driver).click();
		PolicyAppTemplate_Page.lnk_EncryptFleType_Type(driver).click();
		PolicyAppTemplate_Page.lnk_EncryptFleType_Type(driver).sendKeys(AppTemplateEncryptFletype);
		PolicyAppTemplate_Page.lnk_Save(driver).click();
		boolean AppTmp_Add=PolicyAppTemplate_Page.Tble_addwindow(driver).isDisplayed();
		if(AppTmp_Add==true)   
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		else
		{
			return TEST_RESULT.RESULT_SUCCESS;  
		}
	}


	public static TEST_RESULT execute_AfterAddPolicyApptemplate(WebDriver driver, int rowNo, String AppTemplateNme,String AppTemplateProcess, String AppTemplateOS, String AppTemplateHashCode) 
	{
		String getAppTemplateNme=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[4]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getAppTemplateOS=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[4]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[3]/div")).getText();
		String getAppTemplateProcess=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[4]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[4]/div")).getText();            
		String getAppTemplateHashCode=driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[4]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[6]/div")).getText();

		if(getAppTemplateNme.equals(AppTemplateNme) && getAppTemplateOS.equals(AppTemplateOS) && getAppTemplateProcess.equals(AppTemplateProcess) && getAppTemplateHashCode.equals(AppTemplateHashCode))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}

	}

	public static TEST_RESULT execute_ViewPolicyApptemplate(WebDriver driver, int rowNo, String getTablePath_AppTemp,String AppTemplateNme, String AppTemplateProcess, String AppTemplateOS, String AppTemplateHashCode, String AppTemplateOperation, String AppTemplateEncryptFletype) throws Exception
	{
		String getAppTemplateNme=driver.findElement(By.xpath(getTablePath_AppTemp+ "/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getAppTemplateOS=driver.findElement(By.xpath(getTablePath_AppTemp+ "/tr["+(rowNo+1)+"]/td[3]/div")).getText();
		String getAppTemplateProcess=driver.findElement(By.xpath(getTablePath_AppTemp+ "/tr["+(rowNo+1)+"]/td[4]/div")).getText();           
		String getAppTemplateHashCode=driver.findElement(By.xpath(getTablePath_AppTemp+ "/tr["+(rowNo+1)+"]/td[6]/div")).getText();
		driver.findElement(By.xpath(getTablePath_AppTemp+ "/tr["+(rowNo+1)+"]/td[11]/div")).click();
		boolean chkAppTemplateNme=PolicyAppTemplate_Page.txt_Name(driver).isEnabled();
		boolean chkAppTemplateProcess=PolicyAppTemplate_Page.txt_Process(driver).isEnabled();
		boolean chkAppTemplateOS=PolicyAppTemplate_Page.dropdownList_OS(driver).isEnabled();  
		boolean chkAppTemplateHashCode=PolicyAppTemplate_Page.txt_Hashcode(driver).isEnabled();     
		boolean chkAppTempDecrypt=PolicyAppTemplate_Page.Chkbox_Operation_Decrypt(driver).isEnabled();
		boolean chkAppTempEncrypt=PolicyAppTemplate_Page.Chkbox_Operation_Encrypt(driver).isEnabled();      
		boolean chkgetAppTemplateEncryptFletype=PolicyAppTemplate_Page.Tble_EncryptFleType(driver).isEnabled();                        
		boolean chkSaveBtn=PolicyAppTemplate_Page.lnk_Save(driver).isDisplayed();
		boolean chkCloseBtn=PolicyAppTemplate_Page.Lnk_Cancel(driver).isDisplayed();
		
		switch (AppTemplateOperation) 
		{                                   
		case "Decrypt":
			boolean chkAppTemplateDecrpt=PolicyAppTemplate_Page.Chkbox_Operation_Decrypt(driver).isSelected();
			boolean chkAppTemplateEncrypt=PolicyAppTemplate_Page.Chkbox_Operation_Encrypt(driver).isSelected();
			if(chkAppTemplateDecrpt==true && chkAppTemplateEncrypt==false)
			{return TEST_RESULT.RESULT_SUCCESS;}
			else
			{return TEST_RESULT.RESULT_FAILURE;}                  

		case "Encrypt":
			boolean chkAppTemplateDecrpt1=driver.findElement(By.id("trustApplication_is_read_decrypt")).isSelected();
			boolean chkAppTemplateEncrypt1=driver.findElement(By.id("trustApplication_is_write_encrypt")).isSelected();
			if(chkAppTemplateDecrpt1==false && chkAppTemplateEncrypt1==true)
			{return TEST_RESULT.RESULT_SUCCESS;}
			else
			{return TEST_RESULT.RESULT_FAILURE;}

		case "Decrypt;Encrypt": 
			boolean chkAppTemplateDecrpt2=driver.findElement(By.id("trustApplication_is_read_decrypt")).isSelected();
			boolean chkAppTemplateEncrypt2=driver.findElement(By.id("trustApplication_is_write_encrypt")).isSelected();
			if(chkAppTemplateDecrpt2==true && chkAppTemplateEncrypt2==true)
			{return TEST_RESULT.RESULT_SUCCESS;}
			else
			{return TEST_RESULT.RESULT_FAILURE;}
		}

		if(getAppTemplateNme.equals(AppTemplateNme) && getAppTemplateOS.equalsIgnoreCase(AppTemplateOS) && getAppTemplateProcess.equals(AppTemplateProcess) && getAppTemplateHashCode.equals(AppTemplateHashCode) && chkAppTemplateNme==false && chkAppTemplateProcess==false && chkAppTemplateOS==false && chkAppTemplateHashCode==false && chkAppTempDecrypt==false && chkAppTempEncrypt==false && chkgetAppTemplateEncryptFletype==true && chkSaveBtn==false && chkCloseBtn==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}     
	}

	public static void execute_deletePolicyAppTemplate(WebDriver driver,
			String getTablePath_AppTemp, int rowNo) 
	{
		driver.findElement(By.xpath(getTablePath_AppTemp+"/tr["+(rowNo+1)+"]/td[13]/div")).click();
		driver.findElement(By.linkText("Yes")).click();		
	}

	public static TEST_RESULT execute_modifyPolicyAppTemp(
			WebDriver driver, int rowNo, String sptAppTemplateNme, String sptAppTemplateProcess,
			String sptAppTemplateOS, String sptAppTemplateHashCode,
			String sptAppTemplateOperation,
			String sptAppTemplateEncryptFletype) throws Exception
	{
		driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[4]/div/div/div[1]/div/div/div[2]/div[5]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[12]/div")).click();
		PolicyAppTemplate_Page.txt_Name(driver).clear();
		PolicyAppTemplate_Page.txt_Name(driver).sendKeys(sptAppTemplateNme);
		PolicyAppTemplate_Page.txt_Process(driver).clear();
		PolicyAppTemplate_Page.txt_Process(driver).sendKeys(sptAppTemplateProcess);
		if(sptAppTemplateOS.equals("windows"))
		{
			PolicyAppTemplate_Page.dropdownList_OS(driver).click();
			Select se =new Select(PolicyAppTemplate_Page.dropdownList_OS(driver));
			se.selectByValue("1");
			Thread.sleep(2000);
		}
		else
		{
			PolicyAppTemplate_Page.dropdownList_OS(driver).click();
			Select se =new Select(PolicyAppTemplate_Page.dropdownList_OS(driver));
			se.selectByValue("2");
			Thread.sleep(2000);
		}		
		PolicyAppTemplate_Page.txt_Hashcode(driver).clear();
		PolicyAppTemplate_Page.txt_Hashcode(driver).sendKeys(sptAppTemplateHashCode);		
		switch (sptAppTemplateOperation) 
		{						
		case "Decrypt":
			PolicyAppTemplate_Page.Chkbox_Operation_Decrypt(driver).click();
			break;
		case "Encrypt":
			PolicyAppTemplate_Page.Chkbox_Operation_Encrypt(driver).click();
			break;
		case "Decrypt;Encrypt":	
			PolicyAppTemplate_Page.Chkbox_Operation_Decrypt(driver).click();
			PolicyAppTemplate_Page.Chkbox_Operation_Encrypt(driver).click();
		}
		PolicyAppTemplate_Page.lnk_EncryptFleType_Add(driver).click();
		PolicyAppTemplate_Page.lnk_EncryptFleType_Type(driver).sendKeys(sptAppTemplateEncryptFletype);
		PolicyAppTemplate_Page.lnk_Save(driver).click();

		boolean AppTmp_Add=PolicyAppTemplate_Page.Tble_addwindow(driver).isDisplayed();
		if(AppTmp_Add==true)			
			return TEST_RESULT.RESULT_FAILURE;
		else if(AppTmp_Add==false)
		{
			return TEST_RESULT.RESULT_SUCCESS;	
		}
		else
		{
			return TEST_RESULT.RESULT_ERROR;	
		}
	}

	public static TEST_RESULT execute_AftermodifyPolicyAppTemp(
			WebDriver driver, int rowNo, String getTablePath_AppTemp, String sptAppTemplateNme, String sptAppTemplateProcess,
			String sptAppTemplateOS, String sptAppTemplateHashCode,
			String sptAppTemplateOperation,
			String sptAppTemplateEncryptFletype) throws Exception
	{
		String getAppTemplateNme=driver.findElement(By.xpath(getTablePath_AppTemp+ "/tr["+(rowNo+1)+"]/td[1]/div")).getText();
		String getAppTemplateOS=driver.findElement(By.xpath(getTablePath_AppTemp+ "/tr["+(rowNo+1)+"]/td[3]/div")).getText();
		String getAppTemplateProcess=driver.findElement(By.xpath(getTablePath_AppTemp+ "/tr["+(rowNo+1)+"]/td[4]/div")).getText();		
		String getAppTemplateHashCode=driver.findElement(By.xpath(getTablePath_AppTemp+ "/tr["+(rowNo+1)+"]/td[6]/div")).getText();

		if(getAppTemplateNme.equals(sptAppTemplateNme) && getAppTemplateProcess.equals(sptAppTemplateProcess) && getAppTemplateOS.equalsIgnoreCase(sptAppTemplateOS) && getAppTemplateHashCode.equals(sptAppTemplateEncryptFletype))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static TEST_RESULT execute_addFileFolder(WebDriver driver, String deviceName, String deviceDesc, String prerequisite1, String prerequisite2, String prerequisite3) throws Exception
	{
		TEST_RESULT result=null;
		
		parser.RepositoryParser(Constants.path_ObjectRepo);
		String getRoleTablePath=parser.prop.getProperty("PolicyRole_TablePath");
		String getUserTablePath=parser.prop.getProperty("PolicyUser_TablePath");
		String getApplicationTablePath=parser.prop.getProperty("PolicyAppTemp_TablePath");
		
		if(!((prerequisite1.equals("")) && (prerequisite2.equals("")) && (prerequisite3.equals(""))))
		{
			if(!(prerequisite1.equals("")))
			{
				driver.navigate().refresh();
				Home_Page.lnk_Policy(driver).click();
				PolicyRole_Page.lnk_PolicyRole(driver).click();
				
				String[] selectRole=prerequisite1.split(";");
				for(int i=0;i<selectRole.length;i++)
				{
					driver.navigate().refresh();
					Home_Page.lnk_Policy(driver).click();
					PolicyRole_Page.lnk_PolicyRole(driver).click();
					
					int a[]=SysMgmt_Action.searchForData(driver, getRoleTablePath, selectRole[i], 0);

					if(!(a[0]==1))
					{
						PolicyRole_Page.btn_AddRole(driver).click();
						PolicyRole_Page.txt_Name(driver).sendKeys(selectRole[i]);
						PolicyRole_Page.btn_Save(driver).click();

					}
				}
			}
			
			Thread.sleep(3000);
			
			if(!(prerequisite2.equals("")))
			{
				driver.navigate().refresh();
				Home_Page.lnk_Policy(driver).click();
				PolicyUser_Page.lnk_UserTab(driver).click();
				
				String[] selectUser=prerequisite2.split(";");
				for(int i=0;i<selectUser.length;i++)
				{
					driver.navigate().refresh();
					Home_Page.lnk_Policy(driver).click();
					PolicyUser_Page.lnk_UserTab(driver).click();
					
					int a[]=SysMgmt_Action.searchForData(driver, getUserTablePath, selectUser[i], 0);
					if(!(a[0]==1))
					{
						PolicyUser_Page.btn_AddUser(driver).click();
						PolicyUser_Page.txt_Username(driver).sendKeys(selectUser[i]);
						PolicyUser_Page.btn_SaveUser(driver).click();
					}
				}
			}
			
			Thread.sleep(3000);
			
			if(!(prerequisite3.equals("")))
			{
				driver.navigate().refresh();
				Home_Page.lnk_Policy(driver).click();
				PolicyAppTemplate_Page.lnk_AppTemplate(driver).click();
				
				String[] splitPrereq=prerequisite3.split(",");
				String[] splitApplication=splitPrereq[0].split(";");
				
				int splitApp_Length=splitApplication.length;
				for(int i=0; i<splitApp_Length; i++)
				{
					driver.navigate().refresh();
					Home_Page.lnk_Policy(driver).click();
					PolicyAppTemplate_Page.lnk_AppTemplate(driver).click();

					int a[]=SysMgmt_Action.searchForData(driver, getApplicationTablePath, splitApplication[i], 0);
					if(!(a[0]==1))
					{
						execute_PolicyApptemplate(driver, splitApplication[i], splitPrereq[1], splitPrereq[2], splitPrereq[3], splitPrereq[4], splitPrereq[5]);
					}
				}
			}
			
			Thread.sleep(3000);
			
			driver.navigate().refresh();
			Home_Page.lnk_Policy(driver).click();
			PolicyFileFolder_Page.lnk_FileFolderPolicyTab(driver).click();
			
			result=execute_addFileFolderAfterPre(driver,deviceName,deviceDesc);
		}
		else
		{
			result=execute_addFileFolderAfterPre(driver,deviceName,deviceDesc);
		}
		return result;
	}

	private static TEST_RESULT execute_addFileFolderAfterPre(WebDriver driver,
			String deviceName, String deviceDesc) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		String getDeviceTablePath=parser.prop.getProperty("FileFolder_Device_TablePath");

		if(deviceName!="")
		{
			PolicyFileFolder_Page.btn_Add(driver).click();
			PolicyFileFolder_Page.btn_Browse(driver).click();
			int a[]=SysMgmt_Action.searchForData(driver, getDeviceTablePath, deviceName, 1);
			if(a[0]==1)
			{
				driver.findElement(By.xpath(getDeviceTablePath+"/tr/td[2]/div")).click();
				PolicyFileFolder_Page.btn_OKBrowse(driver).click();
				PolicyFileFolder_Page.txt_Desc(driver).sendKeys(deviceDesc);
				PolicyFileFolder_Page.btn_Save(driver).click();
				Thread.sleep(2000);
				String altmsg=PolicyFileFolder_Page.altSuccess_AddFileFolder(driver).getText();

				if(altmsg.contains("File device added successfully."))
				{
					//PolicyFileFolder_Page.btn_Cancel(driver).click();
					return TEST_RESULT.RESULT_SUCCESS;
				}
				else
				{
					//Device Not Added
					PolicyFileFolder_Page.btn_Cancel(driver).click();
					return TEST_RESULT.RESULT_FAILURE;
				}
			}
			else
			{
				//Device not found in the list
				PolicyFileFolder_Page.btn_CancelDevice(driver).click();
				return TEST_RESULT.RESULT_FAILURE;
			}
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE; // Device name null in Excel sheet
		}
	}

	public static boolean execute_AfterAddFileFolder(WebDriver driver, int rowNo, String getTablePath, String deviceName, String deviceDesc)
	{
		String getDeviceName=driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[1]/div")).getText();
		String getDeviceDesc=driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[17]/div")).getText();
		if(getDeviceName.equals(deviceName)&&getDeviceDesc.equals(deviceDesc))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void execute_deleteFileFolder(WebDriver driver, int rowNo, String getTablePath)
	{
		driver.findElement(By.xpath(getTablePath+"/tr["+rowNo+"]/td[19]/div")).click();
		driver.findElement(By.linkText("Yes")).click();
	}
	
	public static void execute_deletePolicyFileFolderPrerequisites(WebDriver driver,
			String prerequisites1,String prerequisites2,String prerequisites3) throws Exception
	{
		
		parser.RepositoryParser(Constants.path_ObjectRepo);
		String getRoleTablePath=parser.prop.getProperty("PolicyRole_TablePath");
		String getUserTablePath=parser.prop.getProperty("PolicyUser_TablePath");
		String getApplicationTablePath=parser.prop.getProperty("PolicyAppTemp_TablePath");
		
		if(!(prerequisites1.equals("")))
		{
			driver.navigate().refresh();
			Home_Page.lnk_Policy(driver).click();
			PolicyRole_Page.lnk_PolicyRole(driver).click();
			
			String[] selectRole=prerequisites1.split(";");
			for(int i=0;i<selectRole.length;i++)
			{
				driver.navigate().refresh();
				Home_Page.lnk_Policy(driver).click();
				PolicyRole_Page.lnk_PolicyRole(driver).click();
				
				int a[]=SysMgmt_Action.searchForData(driver, getRoleTablePath, selectRole[i], 0);

				if(a[0]==1)
				{
					execute_deletePolicyRole(driver, a[1],getRoleTablePath);
					getRefresh_PolicyRole(driver);
				}
			}
		}
		
		Thread.sleep(3000);
		
		if(!(prerequisites1.equals("")))
		{
			driver.navigate().refresh();
			Home_Page.lnk_Policy(driver).click();
			PolicyUser_Page.lnk_UserTab(driver).click();
			
			String[] selectUser=prerequisites1.split(";");
			for(int i=0;i<selectUser.length;i++)
			{
				driver.navigate().refresh();
				Home_Page.lnk_Policy(driver).click();
				PolicyUser_Page.lnk_UserTab(driver).click();
				
				int a[]=SysMgmt_Action.searchForData(driver, getUserTablePath, selectUser[i], 0);
				if(a[0]==1)
				{
					execute_deletePolicyUser(driver, a[1], getUserTablePath);
				}
			}
		}
		
		Thread.sleep(3000);
		
		if(!(prerequisites3.equals("")))
		{
			driver.navigate().refresh();
			Home_Page.lnk_Policy(driver).click();
			PolicyAppTemplate_Page.lnk_AppTemplate(driver).click();
			
			String[] splitPrereq=prerequisites3.split(",");
			String[] splitApplication=splitPrereq[0].split(";");
			
			int splitApp_Length=splitApplication.length;
			for(int i=0; i<splitApp_Length; i++)
			{
				driver.navigate().refresh();
				Home_Page.lnk_Policy(driver).click();
				PolicyAppTemplate_Page.lnk_AppTemplate(driver).click();

				int a[]=SysMgmt_Action.searchForData(driver, getApplicationTablePath, splitApplication[i], 0);
				if(a[0]==1)
				{
					execute_deletePolicyAppTemplate(driver, getApplicationTablePath, a[1]);
				}
			}
		}
	}


	public static boolean execute_addEncryption(WebDriver driver,
			String identityType, String identityName, String cFileFolder,
			String cFileFolderList) throws Exception
	{
		boolean getResult=false;
		PolicyFileFolder_Page.btn_AddEncryption(driver).click();
		
		WebElement selIdentityType=PolicyFileFolder_Page.drpdown_encryptionIdentityType(driver);
		boolean chk1=selectFrmDropDown(driver,selIdentityType,identityType);
		Thread.sleep(3000);
		boolean chk2=false;
		if(identityType!="All")
		{
			WebElement selIdentityName=PolicyFileFolder_Page.drpdown_encryptionIdentityName(driver);
			chk2=selectFrmDropDown(driver,selIdentityName,identityName);
			Thread.sleep(3000);
		}
		
		PolicyFileFolder_Page.btn_encryptionFetch(driver).click();
		
		WebElement selFileFolder=PolicyFileFolder_Page.drpdown_encryptionFileFolderList(driver);
		boolean chk3=selectFrmDropDown1(driver,selFileFolder,cFileFolder);
		Thread.sleep(3000);
		
		boolean chk4=false;
		String[] sptFileFolderList=cFileFolderList.split("/");
		for(int i=0;i<sptFileFolderList.length;i++)
		{
			WebElement selFileFolderList=PolicyFileFolder_Page.drpdown_encryptionFileFolderList(driver);
			chk4=selectFrmDropDown1(driver,selFileFolderList,sptFileFolderList[i]);
			Thread.sleep(3000);
		}
		
		Thread.sleep(3000);
		
		PolicyFileFolder_Page.btn_encryptionOk(driver).click();
		Thread.sleep(2000);
		String getAlert=PolicyFileFolder_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("Encryption rule applied successfully."))
		{
			if((chk1==true) && (chk2==true) && (chk3==true) && (chk4==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}
		return getResult;
	}
	
	private static boolean selectFrmDropDown(WebDriver driver,WebElement ele,String selectFrmDrpDown)
	{
		boolean matchFound = false;
		Select sel=new Select(ele);
		List<WebElement> drpDownList=sel.getOptions();
		int drpDownList_Size=drpDownList.size();
		if(drpDownList_Size!=0)
		{
			for(WebElement drpDownEle : drpDownList)
			{
				String getText=drpDownEle.getText();
				if(getText.equals(selectFrmDrpDown))
				{
					sel.selectByVisibleText(selectFrmDrpDown);
					matchFound = true;
					break;
				}
			}
		}
		return matchFound;
	}
	
	private static boolean selectFrmDropDown1(WebDriver driver,WebElement ele,String selectFrmDrpDown) throws Exception
	{
		boolean matchFound = false;
		Select sel=new Select(ele);
		List<WebElement> drpDownList=sel.getOptions();
		int drpDownList_Size=drpDownList.size();
		if(drpDownList_Size!=0)
		{
			for(WebElement drpDownEle : drpDownList)
			{
				String getText=drpDownEle.getText();
				if(getText.equals(selectFrmDrpDown))
				{
					 Thread.sleep(3000);
					 Actions action = new Actions(driver);
					 action.moveToElement(drpDownEle).doubleClick().build().perform();
					 Thread.sleep(5000);
					 break;
				}
			}
		}
		return matchFound;
	}

	public static boolean execute_addAccessControl(WebDriver driver,
			String cFileFolder, String cFileFolderList) throws Exception
	{
		boolean getResult=false;
		PolicyFileFolder_Page.btn_accessControlAdd(driver).click();
		
		PolicyFileFolder_Page.btn_accessControlFetch(driver).click();
		
		WebElement selFileFolder=PolicyFileFolder_Page.drpdown_accessControlFileFolderList(driver);
		boolean chk3=selectFrmDropDown1(driver,selFileFolder,cFileFolder);
		Thread.sleep(3000);
		
		boolean chk4=false;
		String[] sptFileFolderList=cFileFolderList.split("/");
		for(int i=0;i<sptFileFolderList.length;i++)
		{
			WebElement selFileFolderList=PolicyFileFolder_Page.drpdown_accessControlFileFolderList(driver);
			chk4=selectFrmDropDown1(driver,selFileFolderList,sptFileFolderList[i]);
			Thread.sleep(3000);
		}
		
		Thread.sleep(3000);
		
		PolicyFileFolder_Page.btn_accessControlOk(driver).click();
		Thread.sleep(2000);
		String getAlert=PolicyFileFolder_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("File/Folder associated."))
		{
			if((chk3==true) && (chk4==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}
		
		return getResult;
	}

	public static boolean execute_addFileRights(WebDriver driver,
			String fileRightsName, String fileRightsDesc,
			String frIdentityType, String frfrIdentityname,
			String frApplication, String frOperation) throws Exception
	{
		boolean getResult=false;

		PolicyFileFolder_Page.btn_FolderAccessControlAdd(driver).click();
		PolicyFileFolder_Page.txt_FileRightsRuleName(driver).sendKeys(fileRightsName);
		PolicyFileFolder_Page.txt_FileRightsRuleDesc(driver).sendKeys(fileRightsDesc);
		
		WebElement selIdentityType=PolicyFileFolder_Page.drpdown_FileRightsIdentityType(driver);
		boolean chk1=selectFrmDropDown(driver,selIdentityType,frIdentityType);
		Thread.sleep(3000);
		
		boolean chk2=false;
		if(frIdentityType!="All")
		{
			WebElement selIdentityName=PolicyFileFolder_Page.drpdown_FileRightsIdentityName(driver);
			chk2=selectFrmDropDown(driver,selIdentityName,frfrIdentityname);
			Thread.sleep(3000);
		}
		
		WebElement selApplication=PolicyFileFolder_Page.drpdown_FileRightsApplication(driver);
		boolean chk3=selectFrmDropDown(driver,selApplication,frApplication);
		Thread.sleep(3000);
		
		boolean chk4=false;
		String[] Operations= frOperation.split(","); 
		for (String Operation : Operations) 
		{
			switch(Operation)
			{
			case "Read":
				PolicyFileFolder_Page.chk_FileRightsOperationRead(driver).click();
				break;
			case "Write":
				PolicyFileFolder_Page.chk_FileRightsOperationWrite(driver).click();
				break;
			case "Modify":
				PolicyFileFolder_Page.chk_FileRightsOperationModify(driver).click();
				break;
			case "Delete":
				PolicyFileFolder_Page.chk_FileRightsOperationDelete(driver).click();
				break;
			default:
				chk4=false;
			}
		}
		
		PolicyFileFolder_Page.btn_FileRightsSave(driver).click();

		Thread.sleep(2000);
		String getAlert=PolicyFileFolder_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("Access rule applied successfully."))
		{
			if((chk1==true) && (chk2==true) && (chk3==true) && (chk4==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}

		return getResult;
	}

	public static boolean execute_addKeySharing(WebDriver driver,
			String encryptKey, String encryptIdentityType,
			String encryptIdentityName) throws Exception
	{
		boolean getResult=false;
		
		WebElement selKeyId=PolicyFileFolder_Page.drpdown_EncryptKeyId(driver);
		boolean chk1=selectFrmDropDown(driver,selKeyId,encryptKey);
		Thread.sleep(3000);

		
		WebElement selIdentityType=PolicyFileFolder_Page.drpdown_EncryptKeyIdentityType(driver);
		boolean chk2=selectFrmDropDown(driver,selIdentityType,encryptIdentityType);
		Thread.sleep(3000);
		
		WebElement selIdentityName=PolicyFileFolder_Page.drpdown_EncryptKeyIdentityName(driver);
		boolean chk3=selectFrmDropDown(driver,selIdentityName,encryptIdentityName);
		Thread.sleep(3000);
		
		PolicyFileFolder_Page.btn_EncryptKeySave(driver).click();
		
		String getAlert=PolicyFileFolder_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("User Key added successfully"))
		{
			if((chk1==true) && (chk2==true) && (chk3==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}
		return getResult;
	}

	public static boolean execute_addApplication(WebDriver driver,
			String trustApplicationList) throws Exception
	{
		boolean getResult=false;
		PolicyFileFolder_Page.btn_AddApplication(driver).click();
		
		WebElement selApplication=PolicyFileFolder_Page.drpdown_TrustApplicationList(driver);
		boolean chk3=selectFrmDropDown(driver,selApplication,trustApplicationList);
		Thread.sleep(3000);
		
		String getAlert=PolicyFileFolder_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("Application wish list added successfully."))
		{
			if((chk3==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}
		return getResult;

	}

	public static boolean execute_addOfflineSafeguard(WebDriver driver,
			String offline, String agentPasswordModification,
			String offlinePeriod, String agentpassword,
			String confirmAgentPassword, String userPolicyLock,
			String strictOnlineMode, String migrationTool,
			String migrationToolPwd, String migrationToolCnfPwd) throws Exception
	{
		boolean getResult=false;
		
		WebElement ele1=PolicyFileFolder_Page.chk_EnableOffline(driver);
		boolean chk1=chkboxSelection(ele1,offline);
		
		WebElement ele2=PolicyFileFolder_Page.chk_AgentPasswordModification(driver);
		boolean chk2=chkboxSelection(ele2,agentPasswordModification);
		
		PolicyFileFolder_Page.txt_OfflinePeriod(driver).clear();
		PolicyFileFolder_Page.txt_OfflinePeriod(driver).sendKeys(offlinePeriod);
		
		PolicyFileFolder_Page.txt_AgentPassword(driver).clear();
		PolicyFileFolder_Page.txt_AgentPassword(driver).sendKeys(agentpassword);
		PolicyFileFolder_Page.txt_ConfirmAgentPassword(driver).clear();
		PolicyFileFolder_Page.txt_ConfirmAgentPassword(driver).sendKeys(confirmAgentPassword);
		
		PolicyFileFolder_Page.lnk_Safeguard(driver).click();
		
		WebElement ele3=PolicyFileFolder_Page.chk_UserPolicyLock(driver);
		boolean chk3=chkboxSelection(ele3,userPolicyLock);
		
		WebElement ele4=PolicyFileFolder_Page.chk_StrictOnlineMode(driver);
		boolean chk4=chkboxSelection(ele4,strictOnlineMode);
		
		WebElement ele5=PolicyFileFolder_Page.chk_MigrationTool(driver);
		boolean chk5=chkboxSelection(ele5,migrationTool);
		
		PolicyFileFolder_Page.txt_MigrationToolPwd(driver).clear();
		PolicyFileFolder_Page.txt_MigrationToolPwd(driver).sendKeys(migrationToolPwd);
		PolicyFileFolder_Page.txt_MigrationToolCnfPwd(driver).clear();
		PolicyFileFolder_Page.txt_MigrationToolCnfPwd(driver).sendKeys(migrationToolCnfPwd);
		
		PolicyFileFolder_Page.btn_Save(driver).click();
		
		String getAlert=PolicyFileFolder_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("File device Updated Successfully."))
		{
			if((chk1==true) && (chk2==true) && (chk3==true) && (chk4==true) && (chk5==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}
		
		return getResult;
	}
	
	private static boolean chkboxSelection(WebElement ele,String selection) throws Exception
	{
		boolean getResult=false;
		
		boolean chkSelection=ele.isSelected();
		if(selection.equals("1"))
		{
			if(chkSelection==false)
			{
				ele.click();
				getResult=true;
			}
		}
		else if(selection.equals("0"))
		{
			if(chkSelection==true)
			{
				ele.click();
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}
		return getResult;
	}
	
	public static TEST_RESULT execute_modifyPolicyRole(WebDriver driver, int rowNo, String RoleName, String RoleDesc) throws Exception
    {
          driver.findElement(By.xpath("html/body/div[2]/div/div/div[2]/div[4]/div/div/div[1]/div/div/div[2]/div[1]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr["+(rowNo+1)+"]/td[6]/div")).click();
          PolicyRole_Page.txt_Name(driver).clear();
          PolicyRole_Page.txt_Name(driver).sendKeys(RoleName);
          PolicyRole_Page.txt_Desc(driver).clear();
          PolicyRole_Page.txt_Desc(driver).sendKeys(RoleDesc);
          PolicyRole_Page.btn_Save(driver).click();
          Thread.sleep(2000);
          //boolean alt_Success=PolicyRole_Page.alt_RolePolicy(driver).isDisplayed();                        
          String alt_Success_msg=PolicyRole_Page.alt_RolePolicy(driver).getText();
          System.out.println(alt_Success_msg);
          //System.out.println("success alert status:"+alt_Success);
          Thread.sleep(3000);
          boolean chkDialog=driver.findElement(By.id("oRoleInfRespOverlay")).isDisplayed();
          System.out.println("Add dialog status:"+chkDialog);

          if(alt_Success_msg.equals("Role is updated successfully."))
          {
                return TEST_RESULT.RESULT_SUCCESS;
          }
          else if(alt_Success_msg.equals("Error adding the role."))
          {
                return TEST_RESULT.RESULT_FAILURE;
          }

          else if(chkDialog==true)
          {
                PolicyRole_Page.btn_Cancel(driver).click();
                return TEST_RESULT.RESULT_FAILURE;
          }
          else 
          {
                return TEST_RESULT.RESULT_ERROR;
          }

    }

    public static TEST_RESULT execute_AftermodifyPolicyRole(WebDriver driver, int rowNo, String getTablePath,String sptRoleName, String sptRoleDesc) throws Exception
    {
          driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[6]/div")).click();
          String getRoleName=driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[1]/div")).getText();
          String getRoleDesc=driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[4]/div")).getText();                  

          //driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[6]/div")).click();

          PolicyRole_Page.btn_Cancel(driver).click();

          if(getRoleName.equals(sptRoleName) && getRoleDesc.equals(sptRoleDesc))
          {
                return TEST_RESULT.RESULT_SUCCESS;
          }
          else
          {
                return TEST_RESULT.RESULT_FAILURE;
          }

    }
    
    public static TEST_RESULT execute_Addpolicytemp(WebDriver driver, String TemplateNme, String TemplateDesc, String TemplateType, String prerequisite1, String prerequisite2, String prerequisite3) throws Exception
	{
		TEST_RESULT result=null;

		if(!((prerequisite1.equals("")) && (prerequisite2.equals("")) && (prerequisite3.equals(""))))
		{
			if(!(prerequisite1.equals("")))
			{
				driver.navigate().refresh();
				Home_Page.lnk_Policy(driver).click();
				PolicyRole_Page.lnk_PolicyRole(driver).click();

				String[] selectRole=prerequisite1.split(";");
				for(int i=0;i<selectRole.length;i++)
				{
					driver.navigate().refresh();
					Home_Page.lnk_Policy(driver).click();
					PolicyRole_Page.lnk_PolicyRole(driver).click();

					int a[]=SysMgmt_Action.searchForData(driver, "", selectRole[i], 0);

					if(!(a[0]==1))
					{
						PolicyRole_Page.btn_AddRole(driver).click();
						PolicyRole_Page.txt_Name(driver).sendKeys(selectRole[i]);
						PolicyRole_Page.btn_Save(driver).click();

					}
				}
			}

			Thread.sleep(3000);

			if(!(prerequisite2.equals("")))
			{
				driver.navigate().refresh();
				Home_Page.lnk_Policy(driver).click();
				PolicyUser_Page.lnk_UserTab(driver).click();

				String[] selectUser=prerequisite2.split(";");
				for(int i=0;i<selectUser.length;i++)
				{
					driver.navigate().refresh();
					Home_Page.lnk_Policy(driver).click();
					PolicyUser_Page.lnk_UserTab(driver).click();

					int a[]=SysMgmt_Action.searchForData(driver, "", selectUser[i], 0);
					if(!(a[0]==1))
					{
						PolicyUser_Page.btn_AddUser(driver).click();
						PolicyUser_Page.txt_Username(driver).sendKeys(selectUser[i]);
						PolicyUser_Page.btn_SaveUser(driver).click();
					}
				}
			}

			Thread.sleep(3000);

			if(!(prerequisite3.equals("")))
			{
				driver.navigate().refresh();
				Home_Page.lnk_Policy(driver).click();
				PolicyAppTemplate_Page.lnk_AppTemplate(driver).click();

				String[] splitPrereq=prerequisite3.split(",");
				String[] splitApplication=splitPrereq[0].split(";");

				int splitApp_Length=splitApplication.length;
				for(int i=0; i<splitApp_Length; i++)
				{
					driver.navigate().refresh();
					Home_Page.lnk_Policy(driver).click();
					PolicyAppTemplate_Page.lnk_AppTemplate(driver).click();

					int a[]=SysMgmt_Action.searchForData(driver, "", splitApplication[i], 0);
					if(!(a[0]==1))
					{
						execute_PolicyApptemplate(driver, splitApplication[i], splitPrereq[1], splitPrereq[2], splitPrereq[3], splitPrereq[4], splitPrereq[5]);
					}
				}
			}

			Thread.sleep(3000);
			driver.navigate().refresh();
			Home_Page.lnk_Policy(driver).click();
			PolicyTemplate_Page.lnk_PolicyTemp(driver).click();
			result=execute_AddpolicytempAfterPre(driver,TemplateNme,TemplateDesc,TemplateType);
		}
		else
		{
			result=execute_AddpolicytempAfterPre(driver,TemplateNme,TemplateDesc,TemplateType);
		}
		return result;
	}

	public static TEST_RESULT execute_AddpolicytempAfterPre(WebDriver driver,String TemplateNme, String TemplateDesc,String TemplateType) throws Exception
	{
		PolicyTemplate_Page.lnk_PolicyTemp(driver).click();
		PolicyTemplate_Page.btn_PolicyTempAdd(driver).click();
		PolicyTemplate_Page.txt_PolicyTempAddName(driver).sendKeys(TemplateNme);
		PolicyTemplate_Page.txt_PolicyTempAddDesc(driver).sendKeys(TemplateDesc);
		if(TemplateType.equals("Windows"))
		{
			PolicyTemplate_Page.dropdownlist_PolicyTempAddType(driver).click();
			Select se =new Select(PolicyTemplate_Page.dropdownlist_PolicyTempAddType(driver));
			se.selectByValue("1");
			Thread.sleep(2000);
		}
		else
		{
			PolicyTemplate_Page.dropdownlist_PolicyTempAddType(driver).click();
			Select se =new Select(PolicyTemplate_Page.dropdownlist_PolicyTempAddType(driver));
			se.selectByValue("2");
			Thread.sleep(2000);
		}
		PolicyTemplate_Page.btn_PolicyTempSave(driver).click();
		Thread.sleep(2000);
		boolean alt_Success=PolicyTemplate_Page.Msg_alt_PolicyTemp(driver).isDisplayed();	
		String alt_Success_msg=PolicyTemplate_Page.Msg_alt_PolicyTemp(driver).getText();
		Thread.sleep(3000);
		boolean chkDialog=driver.findElement(By.xpath("html/body/div[45]/div[2]/div/div")).isDisplayed();

		//PolicyTemplate_Page.btn_CancelPolicyTemp(driver).click();
		if(alt_Success==true && alt_Success_msg.equals("Policy template added successfully."))
		{
			return TEST_RESULT.RESULT_SUCCESS;

		}
		else if(alt_Success==true && alt_Success_msg.equals("This Name is used, please choose another one."))
		{  
			return TEST_RESULT.RESULT_FAILURE;
		}

		else if(chkDialog==true)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		else 
		{  
			return TEST_RESULT.RESULT_ERROR;
		}


	}
	
	public static boolean execute_addEncryption(WebDriver driver, String Encrypt_DeviceIP,
			String identityType, String identityName, String cFileFolder,
			String cFileFolderList) throws Exception

	{
		boolean getResult=false;
		String getEncryptTablepath_Policytemp=parser.prop.getProperty("PolicyTemp_EncryptTablePath");

		if(Encrypt_DeviceIP!="")
		{
			PolicyTemplate_Page.btn_AddEncrypt(driver).click();
			Thread.sleep(2000);
			PolicyTemplate_Page.btn_encryptionbrowser(driver).click();
			Thread.sleep(2000);
			int a[]=SysMgmt_Action.searchForData(driver, getEncryptTablepath_Policytemp, Encrypt_DeviceIP, 0);
			Thread.sleep(2000);
			if(a[0]==1)
			{
				driver.findElement(By.xpath(getEncryptTablepath_Policytemp+"/tr/td[2]/div")).click();
				PolicyTemplate_Page.btn_SelectdeviceOk(driver).click(); 
			}
			else
			{
				PolicyTemplate_Page.btn_SelectdeviceCancel(driver).click(); 
			}
		}
		Thread.sleep(2000);
		WebElement selIdentityType=PolicyTemplate_Page.drpdown_encryptionIdentityType(driver);
		Thread.sleep(2000);
		boolean chk1=selectFrmDropDown(driver,selIdentityType,identityType);
		Thread.sleep(2000);
		Thread.sleep(3000);
		boolean chk2=false;
		if(identityType!="All")
		{
			WebElement selIdentityName=PolicyTemplate_Page.drpdown_encryptionIdentityName(driver);
			chk2=selectFrmDropDown(driver,selIdentityName,identityName);
			Thread.sleep(3000);
		}           
		PolicyTemplate_Page.btn_encryptionFetch(driver).click();

		WebElement selFileFolder=PolicyTemplate_Page.drpdown_encryptionFileFolderList(driver);
		boolean chk3=selectFrmDropDown1(driver,selFileFolder,cFileFolder);
		Thread.sleep(3000);

		boolean chk4=false;
		String[] sptFileFolderList=cFileFolderList.split("/");
		for(int i=0;i<sptFileFolderList.length;i++)
		{
			WebElement selFileFolderList=PolicyTemplate_Page.drpdown_encryptionFileFolderList(driver);
			chk4=selectFrmDropDown1(driver,selFileFolderList,sptFileFolderList[i]);
			Thread.sleep(3000);
		}

		Thread.sleep(3000);
		PolicyTemplate_Page.btn_encryptionOk(driver).click();
		Thread.sleep(2000);
		String getAlert=PolicyTemplate_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("Encryption rule applied successfully."))
		{
			if((chk1==true) && (chk2==true) && (chk3==true) && (chk4==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}
		return getResult;
	}
	
	public static boolean execute_addAccessControl(WebDriver driver,String AC_DeviceIP,String cFileFolder, String cFileFolderList)throws Exception 
	{
		boolean getResult=false;
		String getEncryptTablepath_Policytemp=parser.prop.getProperty("PolicyTemp_EncryptTablePath");


		PolicyTemplate_Page.btn_accessControlAdd(driver).click();		

		if(AC_DeviceIP!="")
		{
			PolicyTemplate_Page.btn_AddEncrypt(driver).click();
			PolicyTemplate_Page.btn_encryptionbrowser(driver).click();
			int a[]=SysMgmt_Action.searchForData(driver, getEncryptTablepath_Policytemp, AC_DeviceIP, 1);
			if(a[0]==1)
			{
				driver.findElement(By.xpath(getEncryptTablepath_Policytemp+"/tr/td[2]/div")).click();
				PolicyTemplate_Page.btn_Ac_SelectdeviceOk(driver).click(); 
			}
			else
			{
				PolicyTemplate_Page.btn_Ac_SelectdeviceCancel(driver).click(); 
			}
		}		

		PolicyTemplate_Page.btn_accessControlFetch(driver).click();
		WebElement selFileFolder=PolicyTemplate_Page.drpdown_accessControlFileFolderList(driver);
		boolean chk3=selectFrmDropDown1(driver,selFileFolder,cFileFolder);
		Thread.sleep(3000);

		boolean chk4=false;
		String[] sptFileFolderList=cFileFolderList.split("/");
		for(int i=0;i<sptFileFolderList.length;i++)
		{
			WebElement selFileFolderList=PolicyTemplate_Page.drpdown_accessControlFileFolderList(driver);
			chk4=selectFrmDropDown1(driver,selFileFolderList,sptFileFolderList[i]);
			Thread.sleep(3000);
		}
		Thread.sleep(3000);
		PolicyTemplate_Page.btn_accessControlOk(driver).click();
		Thread.sleep(2000);
		String getAlert=PolicyTemplate_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("Access Control rule applied successfully."))
		{
			if((chk3==true) && (chk4==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}
		return getResult;		
	}	
	
	public static boolean execute_addEncryptUserlst(WebDriver driver,String UserIdentityType, String UserIdentityName)throws Exception 
	{
		boolean getResult=false;
		PolicyTemplate_Page.btn_UserAdd(driver).click();		


		WebElement selIdentityType=PolicyTemplate_Page.drpdown_UserIdentityType(driver);
		boolean chk1=selectFrmDropDown(driver,selIdentityType,UserIdentityType);
		Thread.sleep(3000);
		boolean chk2=false;
		if(UserIdentityType!="All")
		{
			WebElement selIdentityName=PolicyTemplate_Page.drpdown_encryptionIdentityName(driver);
			chk2=selectFrmDropDown(driver,selIdentityName,UserIdentityName);
			Thread.sleep(3000);
		}           
		PolicyTemplate_Page.btn_UserSave(driver).click();
		Thread.sleep(2000);
		String getAlert=PolicyTemplate_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("Encrypt User List applied successfully."))
		{
			if((chk1==true) && (chk2==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}

		return getResult;
	}
	
	public static boolean execute_addApplication1(WebDriver driver,String TrustApplicationList)throws Exception 
	{
		boolean getResult=false;
		PolicyTemplate_Page.btn_AddApplication(driver).click();

		WebElement selApplication=PolicyTemplate_Page.drpdown_TrustApplicationList(driver);
		boolean chk3=selectFrmDropDown(driver,selApplication,TrustApplicationList);
		Thread.sleep(3000);
		PolicyTemplate_Page.btn_ApplicationSave(driver).click(); 
		String getAlert=PolicyTemplate_Page.Alt_encryption(driver).getText();
		if(getAlert.equals("Application wish list added successfully."))
		{
			if((chk3==true))
			{
				getResult=true;
			}
		}
		else
		{
			getResult=false;
		}
		return getResult;
	}

	public static boolean execute_addOfflineSafeguard1(WebDriver driver,String Offline,String AgentPasswordModification,String OfflinePeriod,
			String Agentpassword,String ConfirmAgentPassword,String UserPolicyLock,String StrictOnlineMode,String MigrationTool,String MigrationToolPwd,String MigrationToolCnfPwd)throws Exception 
	{
		{
			boolean getResult=false;

			WebElement ele1=PolicyTemplate_Page.chk_EnableOffline(driver);
			boolean chk1=chkboxSelection(ele1,Offline);

			WebElement ele2=PolicyTemplate_Page.chk_AgentPasswordModification(driver);
			boolean chk2=chkboxSelection(ele2,AgentPasswordModification);

			PolicyTemplate_Page.txt_OfflinePeriod(driver).clear();
			PolicyTemplate_Page.txt_OfflinePeriod(driver).sendKeys(OfflinePeriod);

			PolicyTemplate_Page.txt_AgentPassword(driver).clear();
			PolicyTemplate_Page.txt_AgentPassword(driver).sendKeys(Agentpassword);
			PolicyTemplate_Page.txt_ConfirmAgentPassword(driver).clear();
			PolicyTemplate_Page.txt_ConfirmAgentPassword(driver).sendKeys(ConfirmAgentPassword);

			PolicyTemplate_Page.lnk_Safeguard(driver).click();

			WebElement ele3=PolicyTemplate_Page.chk_UserPolicyLock(driver);
			boolean chk3=chkboxSelection(ele3,UserPolicyLock);

			WebElement ele4=PolicyTemplate_Page.chk_StrictOnlineMode(driver);
			boolean chk4=chkboxSelection(ele4,StrictOnlineMode);

			WebElement ele5=PolicyTemplate_Page.chk_MigrationTool(driver);
			boolean chk5=chkboxSelection(ele5,MigrationTool);

			PolicyTemplate_Page.txt_MigrationToolPwd(driver).clear();
			PolicyTemplate_Page.txt_MigrationToolPwd(driver).sendKeys(MigrationToolPwd);
			PolicyTemplate_Page.txt_MigrationToolCnfPwd(driver).clear();
			PolicyTemplate_Page.txt_MigrationToolCnfPwd(driver).sendKeys(MigrationToolCnfPwd);

			PolicyTemplate_Page.btn_Save(driver).click();

			String getAlert=PolicyTemplate_Page.Alt_encryption(driver).getText();
			if(getAlert.equals("File device Updated Successfully."))
			{
				if((chk1==true) && (chk2==true) && (chk3==true) && (chk4==true) && (chk5==true))
				{
					getResult=true;
				}
			}
			else
			{
				getResult=false;
			}

			return getResult;
		}
	}


	public static void execute_deletePolicytemplate(WebDriver driver,String getTablepath_Policytemp, int rowNo)throws Exception 
	{
		driver.findElement(By.xpath(getTablepath_Policytemp+"/tr["+(rowNo+1)+"]/td[18]/div")).click();
		driver.findElement(By.linkText("Yes")).click();		

	}

}


	



