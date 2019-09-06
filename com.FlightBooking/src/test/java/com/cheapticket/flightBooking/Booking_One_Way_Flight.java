package com.cheapticket.flightBooking;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.cheapticket.xlsxreader.Xlsx_Reader_DataProvider;
import com.driver_class.Driver_Class;


public class Booking_One_Way_Flight extends Driver_Class{
	Logger logge ;
	Properties locate = new Properties();
	/** 
	 * This method is used to Setup  the file-locators.properties . 
	 * driver.manage() is call to maximize the Browser_window and deleteAllCookies
	 * implicitwait method is use to wait the browser till the all properties file get upload.
	 * 
	 */

	@Test(priority=1)   
	public void readytorun() throws IOException
	{

		FileInputStream inputdata = new FileInputStream("./src/test/resources/Cheapticketfile_locators/Cheapticket_locator.properties");
		locate.load(inputdata);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	/**
	 * 
	 * @param drive,xpath,
	 * @param arr
	 * @throws InterruptedException
	 * use sendkey,click method 
	 */

	@Test(priority=2,dataProvider="Read_data_Provider",dataProviderClass=Xlsx_Reader_DataProvider.class)
	public void homePage(String[] arr) throws InterruptedException
	{
		logge=Logger.getLogger(Booking_One_Way_Flight.class);  // log4j implementation for storing the result 
		logge.info("opening homepage of one_way flight ");

		driver.get(locate.getProperty("url"));// load the url
		driver.findElement(By.xpath(locate.getProperty("domesticflit"))).click(); //choose the domestic_one 
		driver.findElement(By.xpath(locate.getProperty("onewayflit"))).click();// oneway flit booking
		WebElement from = waitAndGetElement(30, driver,"(//input[@class='search'])[1]");
		from.sendKeys(arr[0]);
		Thread.sleep(1000);
		WebElement to = waitAndGetElement(30, driver,"(//input[@class='search'])[2]");
		to.sendKeys(arr[1]);
		Thread.sleep(1000);
		//driver.findElement(By.xpath("//div[text()='Goa (GOI)']")).click();
		driver.findElement(By.xpath(locate.getProperty("depart"))).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(locate.getProperty("departdate"))).click();
		Thread.sleep(1000);
		Thread.sleep(1000);

		driver.findElement(By.xpath(locate.getProperty("class"))).click();
		driver.findElement(By.xpath(locate.getProperty("submit"))).click();
		Thread.sleep(5000);
		logge=Logger.getLogger(Booking_One_Way_Flight.class);  // log4j implementation for storing the result 
		logge.info("  passing through the oneway_flightbooking travellersDetail ");
		WebElement book=waitAndGetElement(30,driver,"(//button[text()='BOOK'])[1]");
		book.click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(locate.getProperty("email"))).sendKeys(arr[2]);// enter the emailid
		String phoneno=arr[3].replace(".", "").replace("E9", "");
		driver.findElement(By.xpath(locate.getProperty("mobile"))).sendKeys(phoneno);// fill the phoneno of the traveller
		driver.findElement(By.xpath(locate.getProperty("continue"))).click();
		driver.findElement(By.xpath(locate.getProperty("dropdown1"))).click();
		driver.findElement(By.xpath(locate.getProperty("Mr"))).click();
		driver.findElement(By.xpath(locate.getProperty("firstname1"))).sendKeys(arr[4]);// fill the firstname 
		driver.findElement(By.xpath(locate.getProperty("lastname1"))).sendKeys(arr[5]);// fill the lastname 
		driver.findElement(By.xpath(locate.getProperty("submitlast"))).click();// it will finally submit

	}


	/**
	 * waitAndGetElement method is used for implementation of fluentwait .
	 * @param seconds
	 * @param driver
	 * @param xpath
	 * @return
	 */
	public WebElement waitAndGetElement(long seconds,WebDriver driver,final String xpath) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(seconds))
				.pollingEvery(Duration.ofSeconds(2))
				.ignoring(NoSuchElementException.class);

		WebElement element = fluentWait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver dvr) {
				return dvr.findElement(By.xpath(xpath));
			}
		});
		return element;
	}
	
	/**
	 * quitDown method is use to quit the browser window.
	 * quit() is used to close the browser window.
	 * 
	 */
	@AfterClass
	public void quitDown()
	{
		logge=Logger.getLogger(Booking_One_Way_Flight.class);
		logge.info(" passed successfully");
		driver.quit();// close the browser window 
	}
}

