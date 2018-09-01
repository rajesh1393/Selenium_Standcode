package com.randtronics.dpm.filemanager.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.utility.RepositoryParser;
import com.randtronics.dpm.filemanager.config.Constants;

public class PolicyFileFolder_Page 
{
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element=null;
	
	public static WebElement lnk_FileFolderPolicyTab(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileORFolderTab"));
		return element;
	}
	public static WebElement btn_Add(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Add_FileFolder"));
		return element;
	}
	public static WebElement txt_TargetDeviceName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("TargetDeviceName"));
		return element;
	}
	public static WebElement txt_DeviceIP(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("DeviceIP"));
		return element;
	}
	public static WebElement drpdwn_OS(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("OS_drpdwn"));
		return element;
	}
	public static WebElement txt_Desc(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Desc_FileFolder"));
		return element;
	}
	public static WebElement drpdwn_RebuildPolicy(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("RebuildPolicy"));
		return element;
	}
	public static WebElement btn_Browse(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Browse_FileFolder"));
		return element;
	}
	public static WebElement btn_Save(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Save_FileFolder"));
		return element;
	}
	public static WebElement btn_Cancel(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Cancel_FileFolder"));
		return element;
	}
	public static WebElement altSuccess_AddFileFolder(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("alt_Add_FileFolder"));
		return element;
	}
	public static WebElement btn_OKBrowse(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("OK_Browse"));
		return element;
	}
	public static WebElement btn_CancelBrowse(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Cancel_Browse"));
		return element;
	}
	
	public static WebElement btn_CancelDevice(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("Cancel_DeviceBtn"));
		return element;
	}
	
	public static WebElement btn_AddEncryption(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AddEncryption"));
		return element;
	}
	
	public static WebElement drpdown_encryptionIdentityType(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptionIdentityType"));
		return element;
	}
	
	public static WebElement drpdown_encryptionIdentityName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptionIdentityName"));
		return element;
	}
	
	public static WebElement btn_encryptionFetch(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptionFetch"));
		return element;
	}
	
	public static WebElement drpdown_encryptionFileFolderList(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptionFileFolderList"));
		return element;
	}
	
	public static WebElement btn_encryptionOk(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptionOk"));
		return element;
	}
	
	public static WebElement Alt_encryption(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptionAlt"));
		return element;
	}
	
	public static WebElement lnk_AccessControl(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AccessControltab"));
		return element;
	}
	
	public static WebElement btn_accessControlAdd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AccessControlAdd"));
		return element;
	}
	
	public static WebElement btn_accessControlFetch(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AccessControlFetch"));
		return element;
	}
	
	public static WebElement drpdown_accessControlFileFolderList(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AccessControlFileFolderList"));
		return element;
	}
	
	public static WebElement btn_accessControlOk(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AccessControlOk"));
		return element;
	}
	
	public static WebElement btn_FolderAccessControlAdd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FolderAccessControlAdd"));
		return element;
	}
	
	public static WebElement txt_FileRightsRuleName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsRuleName"));
		return element;
	}
	
	public static WebElement txt_FileRightsRuleDesc(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsRuleDesc"));
		return element;
	}
	
	public static WebElement drpdown_FileRightsIdentityType(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsIdentityType"));
		return element;
	}
	
	public static WebElement drpdown_FileRightsIdentityName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsIdentityName"));
		return element;
	}
	
	public static WebElement drpdown_FileRightsApplication(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsApplication"));
		return element;
	}
	
	public static WebElement chk_FileRightsOperationRead(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsOperationRead"));
		return element;
	}
	
	public static WebElement chk_FileRightsOperationWrite(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsOperationWrite"));
		return element;
	}
	
	public static WebElement chk_FileRightsOperationModify(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsOperationModify"));
		return element;
	}
	
	public static WebElement chk_FileRightsOperationDelete(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsOperationDelete"));
		return element;
	}
	
	public static WebElement btn_FileRightsSave(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("FileRightsSave"));
		return element;
	}
	
	public static WebElement lnk_KeySharing(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("KeySharingTab"));
		return element;
	}
	
	public static WebElement btn_KeySharingAdd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("KeySharingAdd"));
		return element;
	}
	
	public static WebElement drpdown_EncryptKeyId(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptKeyId"));
		return element;
	}
	
	public static WebElement drpdown_EncryptKeyIdentityType(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptKeyIdentityType"));
		return element;
	}
	
	public static WebElement drpdown_EncryptKeyIdentityName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptKeyIdentityName"));
		return element;
	}
	
	public static WebElement btn_EncryptKeySave(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EncryptKeySave"));
		return element;
	}
	
	public static WebElement lnk_Application(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ApplicationTab"));
		return element;
	}
	
	public static WebElement btn_AddApplication(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AddApplication"));
		return element;
	}
	
	public static WebElement drpdown_TrustApplicationList(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("TrustApplicationList"));
		return element;
	}
	
	public static WebElement btn_ApplicationSave(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ApplicationSave"));
		return element;
	}
	
	public static WebElement lnk_Offline(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("OfflineTab"));
		return element;
	}
	
	public static WebElement chk_EnableOffline(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EnableOffline"));
		return element;
	}
	
	public static WebElement chk_AgentPasswordModification(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("EnableAgentPasswordModification"));
		return element;
	}
	
	public static WebElement txt_OfflinePeriod(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("OfflinePeriod"));
		return element;
	}
	
	public static WebElement txt_AgentPassword(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("AgentPassword"));
		return element;
	}
	
	public static WebElement txt_ConfirmAgentPassword(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("ConfirmAgentPassword"));
		return element;
	}
	
	public static WebElement lnk_Safeguard(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("SafeguardTab"));
		return element;
	}
	
	public static WebElement chk_UserPolicyLock(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("UserPolicyLock"));
		return element;
	}
	
	public static WebElement chk_StrictOnlineMode(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("StrictOnlineMode"));
		return element;
	}
	
	public static WebElement chk_MigrationTool(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("MigrationTool"));
		return element;
	}
	
	public static WebElement txt_MigrationToolPwd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("MigrationToolPwd"));
		return element;
	}
	
	public static WebElement txt_MigrationToolCnfPwd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element=driver.findElement(parser.getLocator("MigrationToolCnfPwd"));
		return element;
	}
	
}
