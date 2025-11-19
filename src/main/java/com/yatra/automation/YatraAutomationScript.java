package com.yatra.automation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YatraAutomationScript {
	
	public static void main(String[] args) {
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		//Launch the browser
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //synchronizing the webdriver, explicit waits.
		
		//Maximize the browser window
		driver.manage().window().maximize();
		//load the yatra website
		driver.get("https://www.yatra.com/");
	
		
		
		
		//Locators---->By
		//why dont you create an object of By class
		By depatureDateButtonLocator=By.xpath("//div[@aria-label='Departure Date inputbox' and @role='button']");
		
		WebElement depatureDateButton=wait.until(ExpectedConditions.elementToBeClickable(depatureDateButtonLocator));
		//WebElement depatureDateButton = driver.findElement(depatureDateButtonLocator);
		
		
		By loginLabelLocator = By.className("label");
		WebElement loginLabel = driver.findElement(loginLabelLocator);
		By crossLocator = By.xpath("//img[@alt='cross']");
		WebElement cross = driver.findElement(crossLocator);
		if(loginLabel.isDisplayed()) {
			cross.click();
		}
		depatureDateButton.click();
		
		
		By calendarMonthLocator = By.xpath("//div[@class='react-datepicker__month-container']");
		//List<WebElement> calendarMonthsWebElement =driver.findElements(calendarMonthLocator);
		
		List<WebElement> calendarMonthsList =	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(calendarMonthLocator));
		
		
		System.out.println(calendarMonthsList.size());
		
		//we want to focus on current month
		
		By dateLocator = By.xpath(".//div[contains(@class, 'react-datepicker__day')]");
		
	   WebElement juneCalendarWebElement = calendarMonthsList.get(0); //current month
	   
	   try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   List<WebElement> juneDatesList = juneCalendarWebElement.findElements(dateLocator);
	   
	   for(WebElement date : juneDatesList) {
		   
		   date.getText();
		   
		   System.out.println(date.getText());
		   
	   }
		
	}

}
