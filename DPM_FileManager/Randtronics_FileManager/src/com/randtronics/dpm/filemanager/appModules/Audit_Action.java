package com.randtronics.dpm.filemanager.appModules;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.randtronics.dpm.filemanager.appModules.SysMgmt_Action.TEST_RESULT;
import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.pageObjects.Device_Page;
import com.randtronics.dpm.filemanager.pageObjects.EventConfig_Page;
import com.randtronics.dpm.filemanager.pageObjects.Home_Page;
import com.randtronics.dpm.filemanager.pageObjects.PolicyUser_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemGroup_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemRole_Page;
import com.randtronics.dpm.filemanager.pageObjects.SystemUser_Page;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;


public class Audit_Action 
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

	public static TEST_RESULT execute_addEventActionRule(WebDriver driver,
			String eARuleName, String eARuleDesc, String eventType,
			String eventUser, String eventRiskLevel, String eventOperation,
			String email, String prerequisites) throws Exception
	{
		TEST_RESULT testResult;
		parser.RepositoryParser(Constants.path_ObjectRepo);
		String getPcyUserTablePath=parser.prop.getProperty("PolicyUser_TablePath");

		if(eventUser=="")
		{
			testResult=addEventActionRuleAfterPrerequisite(driver, eARuleName, eARuleDesc, eventType,eventUser, eventRiskLevel, eventOperation, email);
		}
		else
		{
			driver.navigate().refresh();
			Home_Page.lnk_Policy(driver).click();
			PolicyUser_Page.lnk_UserTab(driver).click();
			Thread.sleep(3000);

			int a[]=SysMgmt_Action.searchForData(driver, getPcyUserTablePath, prerequisites,0);
			if(!(a[0]==1))
			{
				PolicyUser_Page.btn_AddUser(driver).click();
				PolicyUser_Page.txt_Username(driver).sendKeys(prerequisites);
				PolicyUser_Page.btn_SaveUser(driver).click();
			}
			driver.navigate().refresh();
			Home_Page.lnk_Audit(driver).click();
			EventConfig_Page.lnk_EventConfig(driver).click();
			
			testResult=addEventActionRuleAfterPrerequisite(driver, eARuleName, eARuleDesc, eventType,eventUser, eventRiskLevel, eventOperation, email);
		}
		return testResult;
	}

	private static TEST_RESULT addEventActionRuleAfterPrerequisite(WebDriver driver, 
			String eARuleName, String eARuleDesc, String eventType,
			String eventUser, String eventRiskLevel, String eventOperation,
			String email) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		EventConfig_Page.btn_AddEventConfig(driver).click();
		Thread.sleep(3000);
		EventConfig_Page.txtbx_EventRuleName(driver).sendKeys(eARuleName);
		EventConfig_Page.txtbx_EventRuleDesc(driver).sendKeys(eARuleDesc);
		
		WebElement selEventRuleType=EventConfig_Page.drpdown_EventRuleType(driver);
		boolean chk1=selectFrmDropDown(driver,selEventRuleType,eventType);
		if(chk1==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		Thread.sleep(3000);
		
		WebElement selEventUsername=EventConfig_Page.drpdown_EventUserName(driver);
		boolean chk2=selectFrmDropDown(driver,selEventUsername,eventUser);
		if(chk2==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		Thread.sleep(3000);
		
		WebElement selEventRiskLevel=EventConfig_Page.drpdown_EventRiskLevel(driver);
		boolean chk3=selectFrmDropDown(driver,selEventRiskLevel,eventRiskLevel);
		if(chk3==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		Thread.sleep(3000);

		boolean getChk=selectChkBox(driver,eventOperation);
		if(getChk==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		
		Thread.sleep(3000);
		
		if(email!="")
		{
			EventConfig_Page.txtbx_EventEmailAddress(driver).sendKeys(email);
		}
		
		EventConfig_Page.btn_SaveEventAction(driver).click();
		Thread.sleep(2000);
		boolean alt_Success=EventConfig_Page.alt_EventAction(driver).isDisplayed();
		System.out.println("success alert status:"+alt_Success);
		Thread.sleep(3000);
		boolean chkDialog=driver.findElement(By.id(parser.prop.getProperty("AddEventActionDialog"))).isDisplayed();
		System.out.println("Add dialog status:"+chkDialog);

		if(alt_Success==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else if(chkDialog==true)
		{
			EventConfig_Page.btn_CancelEventAction(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
		else 
		{
			return TEST_RESULT.RESULT_ERROR;
		}
	}	
	

	public static void execute_deleteEventActionRulePrerequisites(
			WebDriver driver, String prerequisites) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		String getUserTablePath=parser.prop.getProperty("PolicyUser_TablePath");
		driver.navigate().refresh();
		Home_Page.lnk_Policy(driver).click();
		PolicyUser_Page.lnk_UserTab(driver).click();
		Thread.sleep(3000);

		if(prerequisites!="")
		{			
			int a[]=SysMgmt_Action.searchForData(driver, getUserTablePath, prerequisites, 0);
			if(a[0]==1)
			{
				driver.findElement(By.xpath(getUserTablePath+"/tr["+(a[1]+1)+"]/td[18]/div")).click();
				driver.findElement(By.linkText("Yes")).click();			
			}
		}
	}

	public static void execute_deleteEventActionRule(WebDriver driver,
			String getTablePath, int rowNo) 
	{
		//Need to change column no
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[17]/div")).click();
		driver.findElement(By.linkText("Yes")).click();
	}

	public static TEST_RESULT execute_AfterAddEventActionRule(
			WebDriver driver, int rowNo, String getTablePath, String eARuleName,
			String eARuleDesc, String eventType, String eventUser,
			String eventRiskLevel, String eventOperation, String eventEmail) throws Exception
	{
		boolean chk=false;
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[15]/div")).click();
		String getEventName=EventConfig_Page.txtbx_EventRuleName(driver).getAttribute("value");
		String getEventDesc=EventConfig_Page.txtbx_EventRuleDesc(driver).getAttribute("value");
		
		WebElement ele1=EventConfig_Page.drpdown_EventRuleType(driver);
		String getEventType=getValuefromdrpdown(ele1);
		
		WebElement ele2=EventConfig_Page.drpdown_EventUserName(driver);
		String getEventUserName=getValuefromdrpdown(ele2);
		
		WebElement ele3=EventConfig_Page.drpdown_EventRiskLevel(driver);
		String getEventRiskLevel=getValuefromdrpdown(ele3);
		
		String getEventOperation="";
		WebElement ele4=EventConfig_Page.chkbox_SendSyslog(driver);
		chk=verifyCheckboxSelection(ele4);
		if(chk==true)
		{
			getEventOperation=getEventOperation+"Send Syslog"+";";
		}
		
		WebElement ele5=EventConfig_Page.chkbox_SendSNMP(driver);
		chk=verifyCheckboxSelection(ele5);
		if(chk==true)
		{
			getEventOperation=getEventOperation+"Send SNMP trap"+";";
		}
		
		WebElement ele6=EventConfig_Page.chkbox_SendMail(driver);
		chk=verifyCheckboxSelection(ele6);
		if(chk==true)
		{
			getEventOperation=getEventOperation+"Send email to";
		}
		
		String getEventEmail=EventConfig_Page.txtbx_EventEmailAddress(driver).getAttribute("value");
		
		EventConfig_Page.btn_CancelEventAction(driver).click();
		
		if(getEventName.equals(eARuleName) && getEventDesc.equals(eARuleDesc) && getEventType.equals(eventType)
				&& getEventUserName.equals(eventUser) && getEventRiskLevel.equals(eventRiskLevel) 
				&& getEventOperation.equals(eventOperation) && getEventEmail.equals(eventEmail))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static TEST_RESULT execute_viewSysUser(
			WebDriver driver, int rowNo, String getTablePath, String eARuleName, String eARuleDesc,
			String eventType, String eventUser, String eventRiskLevel,
			String eventOperation, String eventEmail) throws Exception
	{
		boolean chk=false;
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[15]/div")).click();
		
		boolean chkEventName=EventConfig_Page.txtbx_EventRuleName(driver).isEnabled();
		boolean chkEventDesc=EventConfig_Page.txtbx_EventRuleDesc(driver).isEnabled();
		boolean chkEventType=EventConfig_Page.drpdown_EventRuleType(driver).isEnabled();
		boolean chkEventUser=EventConfig_Page.drpdown_EventUserName(driver).isEnabled();
		boolean chkEventRiskLevel=EventConfig_Page.drpdown_EventRiskLevel(driver).isEnabled();
		boolean chkEventSyslog=EventConfig_Page.chkbox_SendSyslog(driver).isEnabled();
		boolean chkEventSNMPTrap=EventConfig_Page.chkbox_SendSNMP(driver).isEnabled();
		boolean chkEventSendEmail=EventConfig_Page.chkbox_SendMail(driver).isEnabled();
		boolean chkEventCancelBtn=EventConfig_Page.btn_CancelEventAction(driver).isDisplayed();
		boolean chkEventSaveBtn=EventConfig_Page.btn_SaveEventAction(driver).isDisplayed();
		
		String getEventName=EventConfig_Page.txtbx_EventRuleName(driver).getAttribute("value");
		String getEventDesc=EventConfig_Page.txtbx_EventRuleDesc(driver).getAttribute("value");
		
		WebElement ele1=EventConfig_Page.drpdown_EventRuleType(driver);
		String getEventType=getValuefromdrpdown(ele1);
		
		WebElement ele2=EventConfig_Page.drpdown_EventUserName(driver);
		String getEventUserName=getValuefromdrpdown(ele2);
		
		WebElement ele3=EventConfig_Page.drpdown_EventRiskLevel(driver);
		String getEventRiskLevel=getValuefromdrpdown(ele3);
		
		String getEventOperation="";
		WebElement ele4=EventConfig_Page.chkbox_SendSyslog(driver);
		chk=verifyCheckboxSelection(ele4);
		if(chk==true)
		{
			getEventOperation=getEventOperation+"Send Syslog"+";";
		}
		
		WebElement ele5=EventConfig_Page.chkbox_SendSNMP(driver);
		chk=verifyCheckboxSelection(ele5);
		if(chk==true)
		{
			getEventOperation=getEventOperation+"Send SNMP trap"+";";
		}
		
		WebElement ele6=EventConfig_Page.chkbox_SendMail(driver);
		chk=verifyCheckboxSelection(ele6);
		if(chk==true)
		{
			getEventOperation=getEventOperation+"Send email to";
		}
		
		String getEventEmail=EventConfig_Page.txtbx_EventEmailAddress(driver).getAttribute("value");
		
		EventConfig_Page.btn_CancelEventAction(driver).click();
		
		if(getEventName.equals(eARuleName) && getEventDesc.equals(eARuleDesc) && getEventType.equals(eventType)
				&& getEventUserName.equals(eventUser) && getEventRiskLevel.equals(eventRiskLevel) 
				&& getEventOperation.equals(eventOperation) && getEventEmail.equals(eventEmail)
				&& chkEventName==false && chkEventDesc==false && chkEventType==false && chkEventUser==false && chkEventRiskLevel==false
				&& chkEventSyslog==false && chkEventSNMPTrap==false && chkEventSendEmail==false && chkEventSaveBtn==false && chkEventCancelBtn==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
	}

	public static TEST_RESULT execute_modifyEventActionRule(
			WebDriver driver, int rowNo, String getTablePath, String eARuleName, String eARuleDesc,
			String eventType, String eventUser, String eventRiskLevel,
			String eventOperation, String eventEmail, String eventOperationDeselect) throws Exception
	{
		//Click modify icon and edit the records
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[16]/div")).click();

		EventConfig_Page.txtbx_EventRuleName(driver).clear();
		EventConfig_Page.txtbx_EventRuleName(driver).sendKeys(eARuleName);

		EventConfig_Page.txtbx_EventRuleDesc(driver).clear();
		EventConfig_Page.txtbx_EventRuleDesc(driver).sendKeys(eARuleDesc);
		
		WebElement selEventRuleType=EventConfig_Page.drpdown_EventRuleType(driver);
		boolean chk1=selectFrmDropDown(driver,selEventRuleType,eventType);
		if(chk1==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		Thread.sleep(3000);
		
		WebElement selEventUsername=EventConfig_Page.drpdown_EventUserName(driver);
		boolean chk2=selectFrmDropDown(driver,selEventUsername,eventUser);
		if(chk2==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		Thread.sleep(3000);
		
		WebElement selEventRiskLevel=EventConfig_Page.drpdown_EventRiskLevel(driver);
		boolean chk3=selectFrmDropDown(driver,selEventRiskLevel,eventRiskLevel);
		if(chk3==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		Thread.sleep(3000);
		
		boolean getChk=selectChkBox(driver,eventOperation);
		if(getChk==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		
		boolean getDeselChk=deselectChkBox(driver,eventOperationDeselect);
		if(getDeselChk==false)
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
		
		if(eventEmail!="")
		{
			EventConfig_Page.txtbx_EventEmailAddress(driver).clear();
			EventConfig_Page.txtbx_EventEmailAddress(driver).sendKeys(eventEmail);
		}
		
		EventConfig_Page.btn_SaveEventAction(driver).click();

		boolean alt_Success=EventConfig_Page.alt_EventAction(driver).isDisplayed();
		System.out.println("success alert status:"+alt_Success);
		Thread.sleep(3000);
		boolean chkDialog=driver.findElement(By.id(parser.prop.getProperty("add-event-rules-dialog"))).isDisplayed();
		System.out.println("Dialog status:"+chkDialog);
		
		if(alt_Success==true)
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else if(chkDialog==true)
		{
			EventConfig_Page.btn_CancelEventAction(driver).click();
			return TEST_RESULT.RESULT_FAILURE;
		}
		else
		{
			return TEST_RESULT.RESULT_ERROR;
		}

	}
	

	public static TEST_RESULT execute_AfterModifyEventActionRule(
			WebDriver driver, int rowNo, String getTablePath, String eARuleName, String eARuleDesc,
			String eventType, String eventUser, String eventRiskLevel,
			String eventOperation, String eventEmail, String eventOperationDeselect) throws Exception
	{
		
		boolean chk=false;
		driver.findElement(By.xpath(getTablePath+"/tr["+(rowNo+1)+"]/td[16]/div")).click();
		String getEventName=EventConfig_Page.txtbx_EventRuleName(driver).getAttribute("value");
		String getEventDesc=EventConfig_Page.txtbx_EventRuleDesc(driver).getAttribute("value");
		
		WebElement ele1=EventConfig_Page.drpdown_EventRuleType(driver);
		String getEventType=getValuefromdrpdown(ele1);
		
		WebElement ele2=EventConfig_Page.drpdown_EventUserName(driver);
		String getEventUserName=getValuefromdrpdown(ele2);
		
		WebElement ele3=EventConfig_Page.drpdown_EventRiskLevel(driver);
		String getEventRiskLevel=getValuefromdrpdown(ele3);
		
		String[] selectedChkBox=getChkBoxSelection(driver);
		String getEventOperation=selectedChkBox[0];
		String getEventOperationDeselect=selectedChkBox[1];
		
		String getEventEmail=EventConfig_Page.txtbx_EventEmailAddress(driver).getAttribute("value");
		
		EventConfig_Page.btn_CancelEventAction(driver).click();
		
		if(getEventName.equals(eARuleName) && getEventDesc.equals(eARuleDesc) && getEventType.equals(eventType)
				&& getEventUserName.equals(eventUser) && getEventRiskLevel.equals(eventRiskLevel) 
				&& getEventOperation.equals(eventOperation) && getEventOperationDeselect.equals(eventOperationDeselect) 
				&& getEventEmail.equals(eventEmail))
		{
			return TEST_RESULT.RESULT_SUCCESS;
		}
		else
		{
			return TEST_RESULT.RESULT_FAILURE;
		}
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
	
	private static String getValuefromdrpdown(WebElement ele) throws Exception
	{
		String getVal="";
		Select sel=new Select(ele);
		getVal=sel.getFirstSelectedOption().getText();
		return getVal;
	}
	
	private static boolean verifyCheckboxSelection(WebElement checkBoxElement) 
	{
		boolean chkselection=false;
		try 
		{
			if (checkBoxElement.isSelected()) 
			{
				chkselection=true;
			} 
			else 
			{
				chkselection=false;
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Unable to select the checkbox: " + checkBoxElement);    
		}
		return chkselection;
	}
	
	private static boolean selectChkBox(WebDriver driver,String getStr) throws Exception
	{
		boolean chk=false;

		if(getStr!="")
		{
			String[] Operations= getStr.split(","); 
			for (String Operation : Operations) 
			{
				switch(Operation)
				{
				case "Send Syslog":
					if(!(EventConfig_Page.chkbox_SendSyslog(driver)).isSelected())
					{
						EventConfig_Page.chkbox_SendSyslog(driver).click();
					}
					chk=true;
					break;
				case "Send SNMP trap":
					if(!(EventConfig_Page.chkbox_SendSNMP(driver)).isSelected())
					{
						EventConfig_Page.chkbox_SendSNMP(driver).click();
					}
					chk=true;
					break;
				case "Send email to":
					if(!(EventConfig_Page.chkbox_SendMail(driver)).isSelected())
					{
						EventConfig_Page.chkbox_SendMail(driver).click();
					}
					chk=true;
					break;
				default:
					chk=false;	
					return chk;
				}
			}
		}
		return chk;
	}
	
	private static boolean deselectChkBox(WebDriver driver,String getStr) throws Exception
	{
		boolean chk=false;
		if(getStr!="")
		{
			String[] Operations= getStr.split(","); 
			for (String Operation : Operations) 
			{
				switch(Operation)
				{
				case "Send Syslog":
					if((EventConfig_Page.chkbox_SendSyslog(driver)).isSelected())
					{
						EventConfig_Page.chkbox_SendSyslog(driver).click();
					}
					chk=true;
					break;
				case "Send SNMP trap":
					if((EventConfig_Page.chkbox_SendSNMP(driver)).isSelected())
					{
						EventConfig_Page.chkbox_SendSNMP(driver).click();
					}
					chk=true;
					break;
				case "Send email to":
					if((EventConfig_Page.chkbox_SendMail(driver)).isSelected())
					{
						EventConfig_Page.chkbox_SendMail(driver).click();
					}
					chk=true;
					break;
				default:
					chk=false;	
				}
			}
		}
		return chk;
	}
	private static String[] getChkBoxSelection(WebDriver driver) throws Exception
	{
		boolean chk;
		String[] getSelection = new String[2];
		getSelection[0]="";
		getSelection[1]="";
		
		chk=verifyCheckboxSelection(EventConfig_Page.chkbox_SendSyslog(driver));
		if(chk==true)
		{
			getSelection[0]=getSelection[0]+"Send Syslog"+";";
		}
		else
		{
			getSelection[1]=getSelection[1]+"Send Syslog"+";";
		}
		
		chk=verifyCheckboxSelection(EventConfig_Page.chkbox_SendSNMP(driver));
		if(chk==true)
		{
			getSelection[0]=getSelection[0]+"Send SNMP trap"+";";
		}
		else
		{
			getSelection[1]=getSelection[1]+"Send SNMP trap"+";";
		}
		
		chk=verifyCheckboxSelection(EventConfig_Page.chkbox_SendMail(driver));
		if(chk==true)
		{
			getSelection[0]=getSelection[0]+"Send email to"+";";
		}
		else
		{
			getSelection[1]=getSelection[1]+"Send email to"+";";
		}
		
		getSelection[0]=getSelection[0].substring(0, getSelection[0].length()-1);
		getSelection[1]=getSelection[1].substring(0, getSelection[1].length()-1);
		return getSelection;
	}


}
