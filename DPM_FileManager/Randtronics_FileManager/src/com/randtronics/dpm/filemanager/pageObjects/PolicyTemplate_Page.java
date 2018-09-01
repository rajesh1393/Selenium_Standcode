package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class PolicyTemplate_Page {
	public static RepositoryParser parser=new RepositoryParser();
	private static WebElement element = null;
	
	public static WebElement lnk_PolicyTemp(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("PolicyTemp"));
    	return element;
    }	
	public static WebElement btn_PolicyTempAdd(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("PolicyTempAdd"));
    	return element;
    }
	public static WebElement txt_PolicyTempAddName(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("PolicyTempAddName"));
    	return element;
    }
	public static WebElement txt_PolicyTempAddDesc(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("PolicyTempAddDesc"));
    	return element;
    }
	public static WebElement dropdownlist_PolicyTempAddType(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("PolicyTempAddType"));
    	return element;
    }
	public static WebElement btn_PolicyTempSave(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("PolicyTempSave"));
    	return element;
    }
	public static WebElement Msg_alt_PolicyTemp(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("altmsg_PolicyTemp"));
    	return element;
    }
	public static WebElement btn_CancelPolicyTemp(WebDriver driver) throws Exception
    {
    	parser.RepositoryParser(Constants.path_ObjectRepo);
    	element = driver.findElement(parser.getLocator("CancelPolicyTemp"));
    	return element;
    }
	public static WebElement btn_AddEncrypt(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("AddEncrypt"));
		return element;
	}	
	public static WebElement btn_encryptionbrowser(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("encryptionbrowser"));
		return element;
	}
	public static WebElement btn_SelectdeviceOk(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("SelectdeviceOk_btn"));
		return element;
	}	
	public static WebElement btn_SelectdeviceCancel(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("SelectdeviceCancel_btn"));
		return element;
	}	
		
	public static WebElement drpdown_encryptionIdentityType(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("encryptionIdentityType"));
		return element;
	}
	public static WebElement drpdown_encryptionIdentityName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("encryptionIdentityName"));
		return element;
	}
	public static WebElement btn_encryptionFetch(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("encryptionFetch"));
		return element;
	}	
	public static WebElement drpdown_encryptionFileFolderList(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("encryptionFileFolderList"));
		return element;
	}	
	public static WebElement btn_encryptionOk(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("encryptionOk"));
		return element;
	}
	public static WebElement Alt_encryption(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("Alt_encryption"));
		return element;
	}
	
	
	//access control	
	
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
    
    public static WebElement btn_Ac_SelectdeviceOk(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("Ac_SelectdeviceOk_btn"));
		return element;
	}	
	public static WebElement btn_Ac_SelectdeviceCancel(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("Ac_SelectdeviceCancel_btn"));
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

    //File Rights
    
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

    
	//user
	
	public static WebElement lnk_EncryptUserlst(WebDriver driver) throws Exception
    {
                    parser.RepositoryParser(Constants.path_ObjectRepo);
                    element=driver.findElement(parser.getLocator("EncryptUserlst"));
                    return element;
    }
    
    public static WebElement btn_UserAdd(WebDriver driver) throws Exception
    {
                    parser.RepositoryParser(Constants.path_ObjectRepo);
                    element=driver.findElement(parser.getLocator("UserAdd"));
                    return element;
    }
    
    public static WebElement drpdown_UserIdentityType(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("UserIdentityType"));
		return element;
	}
	public static WebElement drpdown_UserIdentityName(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("UserIdentityName"));
		return element;
	}
	
	public static WebElement btn_UserSave(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("UserSave"));
		return element;
	}
    
    	
	//Application
	
	public static WebElement lnk_Application(WebDriver driver) throws Exception
    {
                    parser.RepositoryParser(Constants.path_ObjectRepo);
                    element=driver.findElement(parser.getLocator("Application"));
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

	//offline/Safeguard
	
	public static WebElement lnk_Offline(WebDriver driver) throws Exception
    {
                    parser.RepositoryParser(Constants.path_ObjectRepo);
                    element=driver.findElement(parser.getLocator("Offline"));
                    return element;
    }
	
	public static WebElement lnk_Safeguard(WebDriver driver) throws Exception
    {
                    parser.RepositoryParser(Constants.path_ObjectRepo);
                    element=driver.findElement(parser.getLocator("Safeguard"));
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
                    element=driver.findElement(parser.getLocator("AgentPasswordModification"));
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
		element = driver.findElement(parser.getLocator("ConfirmAgentPassword"));
		return element;
	}
	public static WebElement chk_UserPolicyLock(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("UserPolicyLock"));
		return element;
	}
	
	public static WebElement chk_StrictOnlineMode(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("StrictOnlineMode"));
		return element;
	}
	public static WebElement chk_MigrationTool(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("MigrationTool"));
		return element;
	}
	
	public static WebElement txt_MigrationToolPwd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("MigrationToolPwd"));
		return element;
	}
	
	public static WebElement txt_MigrationToolCnfPwd(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("MigrationToolCnfPwd"));
		return element;
	}
	public static WebElement btn_Save(WebDriver driver) throws Exception
	{
		parser.RepositoryParser(Constants.path_ObjectRepo);
		element = driver.findElement(parser.getLocator("Save"));
		return element;
	}
}
