package com.randtronics.dpm.filemanager.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class RepositoryParser 
{
	public FileInputStream stream;
	public Properties prop=new Properties();
	public String RepoFileName;
	
	public void RepositoryParser(String fileName) throws IOException
	{
		this.RepoFileName=fileName;
		stream=new FileInputStream(fileName);
		prop.load(stream);	
	}
	
	/*public RepositoryParser(String fileName) throws IOException
	{
		this.RepoFileName=fileName;
		stream=new FileInputStream(fileName);
		prop.load(stream);
		
	}*/
	
	public By getLocator(String locatorName)
	{
		String locatorProperty=prop.getProperty(locatorName);
		String locatorType=locatorProperty.split(":")[0];
		String locatorValue=locatorProperty.split(":")[1];
		
		By locator=null;
		switch(locatorType)
		{
		case "Id":
			locator=By.id(locatorValue);
			break;
		case "Name":
			locator=By.name(locatorValue);
			break;
		case "ClassName":
			locator=By.className(locatorValue);
			break;
		case "LinkText":
			locator=By.linkText(locatorValue);
			break;
		case "PartialLinkText":
			locator=By.partialLinkText(locatorValue);
			break;
		case "TagName":
			locator=By.tagName(locatorValue);
			break;
		case "xpath":
			locator=By.xpath(locatorValue);
			break;
		case "CssSelector":
			locator=By.cssSelector(locatorValue);
			break;
		}
		return locator;
	}
}

