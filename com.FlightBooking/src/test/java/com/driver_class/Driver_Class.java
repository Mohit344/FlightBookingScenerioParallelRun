package com.driver_class;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeClass;

public class Driver_Class {
	protected WebDriver driver;
	/**
	 *switch case is created for the browsers-chrome,firefox.
	 * @throws IOException
	 */

	@BeforeClass
	public void configSetup() throws IOException {
		
		File file = new File("./src/test/resources/cheapticket_testData/cheapticket_config.properties");
		Properties promote = property_return(file);
		switch (promote.getProperty("webdrivername")) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "./libs/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "./libs/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver","./libs/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;
			
			
		}
	}
	
	/**
	 * method-property_return
	 * @param file
	 * @return
	 * @throws IOException
	 */

	public Properties property_return(File file) throws IOException {
		Properties prop1 = new Properties();
		FileInputStream input = new FileInputStream(file);
		prop1.load(input);
		return prop1;
	}



}
