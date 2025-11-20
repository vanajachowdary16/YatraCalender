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
		// Launch the browser
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // synchronizing the webdriver, explicit
																				// waits.

		// Maximize the browser window
		driver.manage().window().maximize();
		// load the yatra website
		driver.get("https://www.yatra.com/");

		// Locators---->By
		// why dont you create an object of By class
	

		crossPopup(driver);
		clickOnDepatureDateButton(wait);
		
		// List<WebElement> calendarMonthsWebElement
		// =driver.findElements(calendarMonthLocator);
		WebElement currentMonthWebElement = selectTheMonthFromCalendar(wait, 0);
		WebElement NextMonthWebElement = selectTheMonthFromCalendar(wait, 1);

		// System.out.println(currentMonthWebElement.getAttribute("aria-label"));

		String lowestPricedateForCurrentMonth=getMeLowestPrice(currentMonthWebElement);
		System.out.println(lowestPricedateForCurrentMonth);
		String lowestPricedateForNextMonth=getMeLowestPrice(NextMonthWebElement);
		System.out.println(lowestPricedateForNextMonth);
		
		compareTwoMonthsPrices(lowestPricedateForCurrentMonth, lowestPricedateForNextMonth);
		
		

	}
	
	private static void crossPopup(WebDriver driver) {
		By loginLabelLocator = By.className("label");
		WebElement loginLabel = driver.findElement(loginLabelLocator);
		By crossLocator = By.xpath("//img[@alt='cross']");
		WebElement cross = driver.findElement(crossLocator);
		if (loginLabel.isDisplayed()) {
			cross.click();
		}
	}
	private static void clickOnDepatureDateButton(WebDriverWait wait) {
		By depatureDateButtonLocator = By.xpath("//div[@aria-label='Departure Date inputbox' and @role='button']");
		WebElement depatureDateButton = wait.until(ExpectedConditions.elementToBeClickable(depatureDateButtonLocator));
		depatureDateButton.click();
		
	}

	private static String getMeLowestPrice(WebElement monthWebElement) {
		By dateLocator = By.xpath(".//div[contains(@class, 'react-datepicker__day')]");
		By priceLocator = By.xpath(".//span[contains(@class, 'custom-day-content ')]");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// List<WebElement> juneDatesList =
		// currentMonthWebElement.findElements(dateLocator);
		List<WebElement> pricesList = monthWebElement.findElements(priceLocator);
		// System.out.println(pricesList.size());

		int lowestPrice = Integer.MAX_VALUE;

		for (WebElement price : pricesList) {

			String priceString = price.getText();
			if (priceString.length() > 0) {
			priceString = priceString.replace("₹", "").replace(",", "");
			// System.out.println(priceString);

			int priceInt = Integer.parseInt(priceString); // convert the string value into integer

			// find the smallest number
			if (priceInt < lowestPrice) {
				lowestPrice = priceInt;
			}
			}

		}
		By lowestPriceDate = By.xpath(".//span[contains(@class, 'custom-day-content ')]/../..");
		WebElement date = monthWebElement.findElement(lowestPriceDate);		
		String result = date.getAttribute("aria-label") + "--Price is Rs" + lowestPrice;
		return result;
	}

	public static WebElement selectTheMonthFromCalendar(WebDriverWait wait, int index) {
		By calendarMonthLocator = By.xpath("//div[@class='react-datepicker__month']");
		List<WebElement> calendarMonthsList = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(calendarMonthLocator));

		System.out.println(calendarMonthsList.size());

		// we want to focus on current month
		WebElement monthCalendarWebElement = calendarMonthsList.get(index);// current month
		return monthCalendarWebElement;
	}
	
	public static void compareTwoMonthsPrices(String currentMonthPrice, String nextMonthPrice) {
		
		int currentMonthRsIndex = currentMonthPrice.indexOf("Rs");
		int nextMonthRsIndex = nextMonthPrice.indexOf("Rs");
		
		System.out.println(currentMonthRsIndex);
		System.out.println(nextMonthRsIndex);
		
		String currentPrice = currentMonthPrice.substring(currentMonthRsIndex + 2);
		String nextPrice = nextMonthPrice.substring(nextMonthRsIndex + 2);
		
		int current = Integer.parseInt(currentPrice);
		int next = Integer.parseInt(nextPrice);
		
		if(current<next) {
			System.out.println("the lowest price for two months is " +current);
		}
		else if(current==next){
			
			System.out.println("price is same for both months. choose whatever you prefer!");
		}
		else {
			System.out.println("the lowest price for two months is " +next);
		}
		
		
	}

}
