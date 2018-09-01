package com.randtronics.dpm.filemanager.config;

public class Constants 
{
    public static final String baseUrl="https://localhost:8443/dpmfile_web/";
	
	public static final String CHROME_DRIVER_EXEPATH ="C:\\Selenium\\chromedriver_win32\\chromedriver.exe";
	public static final String CHROME_DRIVER ="webdriver.chrome.driver";
	public static final int implicitWaitSec = 30;
	
	public static String getPath_TestData=System.getProperty("user.dir")+"\\src\\com\\randtronics\\dpm\\filemanager\\dataEngine\\";
	public static final String Path_TestData = getPath_TestData.replace("\\", "\\\\");
			

	public static String getPath_ObjectRepo=System.getProperty("user.dir")+"\\ObjectRepo.properties";
	public static final String path_ObjectRepo=getPath_ObjectRepo.replace("\\", "\\\\");

	public static final String File_TestData = "TestSheet.xls";
	public static final String File_TestData1 = "TestSheet";
	
	public static final String File_TestSheet_SystemUser = "System User";
	public static final String File_TestSheet_SystemRole = "System Role";
	public static final String File_TestSheet_SystemGroup = "Group";
	public static final String File_TestSheet_PolicyUser="Policy User";
	public static final String File_TestSheet_PolicyRole="Policy Role";
	public static final String File_TestSheet_PolicyAppTemplate="App Template";
	public static final String File_TestSheet_PolicyFileFolder="FileFolderDB Policy";
	public static final String File_TestSheet_PolicyTemplate="PolicyTemplate";
	public static final String File_TestSheet_Login="Login";
	public static final String File_TestSheet_Profile="Profile";
	public static final String File_TestSheet_Email = "Email";
	public static final String File_TestSheet_PasswordMgmt= "Password Management";
	public static final String File_TestSheet_Device="Device";
	public static final String File_TestSheet_License="License";
	public static final String File_TestSheet_SystemInformation="System Info";
	public static final String File_TestSheet_EventConfig="Event Config";
}

