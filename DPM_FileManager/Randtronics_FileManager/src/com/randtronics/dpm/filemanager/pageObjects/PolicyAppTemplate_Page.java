package com.randtronics.dpm.filemanager.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.randtronics.dpm.filemanager.config.Constants;
import com.randtronics.dpm.filemanager.utility.RepositoryParser;

public class PolicyAppTemplate_Page {
	
		public static RepositoryParser parser=new RepositoryParser();
		private static WebElement element=null;
		
		public static WebElement lnk_AppTemplate(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_AppTemplate"));
		    	return element;
		 }
		
		public static WebElement lnk_Add(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_Add"));
		    	return element;
		 }
		
		public static WebElement txt_Name(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_Name"));
		    	return element;
		 }
		
		public static WebElement txt_Process(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_Process"));
		    	return element;
		 }
		
		public static WebElement dropdownList_OS(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_OS"));
		    	return element;
		 }
		
		public static WebElement txt_Hashcode(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_Hashcode"));
		    	return element;
		 }
		
		public static WebElement Chkbox_Operation_Decrypt(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_Operatn_Decrypt"));
		    	return element;
		 }
		public static WebElement Chkbox_Operation_Encrypt(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_Operatn_Encrypt"));
		    	return element;
		 }		
		
		public static WebElement lnk_EncryptFleType_Add(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_EncryptFleType_Add"));
		    	return element;
		 }
		
		public static WebElement lnk_EncryptFleType_Type(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_EncryptFleType_Type"));
		    	return element;
		 }			
		
		public static WebElement lnk_Save(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_Save"));
		    	return element;
		 }
		
		public static WebElement Lnk_Cancel(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_Cancel"));
		    	return element;
		 }
		
		public static WebElement Tble_addwindow(WebDriver driver) throws Exception
		 {
		    	parser.RepositoryParser(Constants.path_ObjectRepo);
		    	element = driver.findElement(parser.getLocator("AppTemp_addwindow"));
		    	return element;
		 }

		public static WebElement Tble_EncryptFleType(WebDriver driver) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
}
