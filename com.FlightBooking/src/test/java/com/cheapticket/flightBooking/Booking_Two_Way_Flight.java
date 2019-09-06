/**
 *This class is  for to automating the two_way flightbooking Scenarios
 *for various field i use the sendkeys and click method.
 * 
 *
 */
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

public class Booking_Two_Way_Flight extends Driver_Class{
	Logger logge ;
	Properties locate = new Properties();
	/**
	 * 
	 * @throws IOException
	 */
	@Test(priority=1)
	public void readytorun() throws IOException
	{
		FileInputStream input = new FileInputStream("./src/test/resources/Cheapticketfile_locators/Cheapticket_locator.properties");

		locate.load(input);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
	/**
	 * 
	 * @param arr
	 * @throws InterruptedException
	 */

	@Test(priority=2,dataProvider="Read_data_Provider",dataProviderClass=Xlsx_Reader_DataProvider.class)
	public void homepage(String[] arr) throws InterruptedException
	{
		logge=Logger.getLogger(Booking_Two_Way_Flight.class);
		logge.info(" opening homepage of two_way flight ");

		driver.get(locate.getProperty("url"));//  for loading the url 
		driver.findElement(By.xpath(locate.getProperty("domesticflit"))).click();// choose domestic flit
		driver.findElement(By.xpath(locate.getProperty("twowayflit"))).click();  // to choose the twoway option
		WebElement from = waitAndGetElement(30, driver,"(//input[@class='search'])[1]");
		from.sendKeys(arr[0]);
		Thread.sleep(1000);
		WebElement to = waitAndGetElement(30, driver,"(//input[@class='search'])[2]");
		to.sendKeys(arr[1]);
		Thread.sleep(1000);
		driver.findElement(By.xpath(locate.getProperty("depart"))).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(locate.getProperty("departdate"))).click();
		WebElement arrivedate = waitAndGetElement(30, driver,"(//a[text()='1' and @class='day'])[4]");
		arrivedate.click();
		Thread.sleep(1000);
		//driver.findElement(By.xpath(loc.getProperty("adult"))).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(locate.getProperty("class"))).click();
		driver.findElement(By.xpath(locate.getProperty("submit"))).click();
		Thread.sleep(10000);
		logge=Logger.getLogger(Booking_Two_Way_Flight.class);
		logge.info("  passing through the twoway travellersDetail ");
		driver.findElement(By.xpath(locate.getProperty("fly1"))).click();

		driver.findElement(By.xpath(locate.getProperty("fly2"))).click();

		driver.findElement(By.xpath(locate.getProperty("book2"))).click();
		driver.findElement(By.xpath(locate.getProperty("email"))).sendKeys(arr[2]);// enter the emailid
		String phoneno=arr[3].replace(".", "").replace("E9", "");

		driver.findElement(By.xpath(locate.getProperty("mobile"))).sendKeys(phoneno);

		driver.findElement(By.xpath(locate.getProperty("continue"))).click();
		driver.findElement(By.xpath(locate.getProperty("dropdown1"))).click();
		driver.findElement(By.xpath(locate.getProperty("Mr"))).click();
		driver.findElement(By.xpath(locate.getProperty("firstname1"))).sendKeys(arr[4]);// enter the first name of traveller
		driver.findElement(By.xpath(locate.getProperty("lastname1"))).sendKeys(arr[5]);// enter the second name of traveller

		driver.findElement(By.xpath(locate.getProperty("submitlast"))).click();// finally submit the details.


	}
	/**
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
	 * quit() method is used to close  the browser window .
	 */
	@AfterClass
	public void tearDown()
	{
		logge=Logger.getLogger(Booking_Two_Way_Flight.class);
		logge.info("Passed successfully");
		driver.quit();// close the browser_window
	}


}
