package com.zerion.actiondriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.zerion.base.BaseClass;

/**
 * Author : Goutam Naik
 **/

public class Action extends BaseClass {

	public static void click(WebDriver driver, WebElement ele) {

		Actions act = new Actions(driver);
		act.moveToElement(ele).click().build().perform();
	}

	public static void pressEnter(WebDriver driver, WebElement ele) {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ENTER).perform();
	}

	public static void pressEsc(WebDriver driver, WebElement ele) {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ESCAPE).perform();
	}

	public static void mouseOverElement(WebDriver driver, WebElement element) {
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(element).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				System.out.println(" MouserOver Action is performed on ");
			} else {
				System.out.println("MouseOver action is not performed on");
			}
		}
	}

	public static boolean launchUrl(WebDriver driver, String url) {
		boolean flag = false;
		try {
			driver.navigate().to(url);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (flag) {
				System.out.println("Successfully launched \"" + url + "\"");
			} else {
				System.out.println("Failed to launch \"" + url + "\"");
			}
		}
	}

	public String getTitle(WebDriver driver) {
		boolean flag = false;

		String text = driver.getTitle();
		if (flag) {
			System.out.println("Title of the page is: \"" + text + "\"");
		}
		return text;
	}

	public static void implicitWait(WebDriver driver, int timeOut) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
	}

	public static void explicitWait(WebElement element, long i) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(i));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static String screenShot(WebDriver driver, String filename) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\ScreenShot\\" + filename + "_" + dateName + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		// This new path for jenkins
		String newImageString = "http://localhost:8082/job/MyStoreProject/ws/MyStoreProject/ScreenShots/" + filename
				+ "_" + dateName + ".png";
		return destination;
	}

	/**
	 * Function to wait for element to disappear
	 * 
	 * @param element pass the WebElement
	 * @param i       pass the number of seconds
	 */
	public static void explicitWaitForElementToDisappear(WebElement element, long i) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(i));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 * Function to wait for element to be clickable
	 * 
	 * @param element pass the WebElement
	 * @param i       pass the number of seconds
	 */
	public static void explicitWaitForElementTobeclickable(WebElement element, long i) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(i));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Fuction to scroll to an element
	 * 
	 * @param element pass webelement
	 * @throws InterruptedException
	 */
	public static void scrollToElement(WebElement element) throws InterruptedException {
		Actions actions = new Actions(driver);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(3000);
		actions.moveToElement(element).perform();
	}

	/**
	 * Function to send keys textbox
	 * 
	 * @param element pass webelement
	 * @param text    pass text to send as keys
	 * @param log     pass the log to be printed on report
	 */
	public static void sendKeysWithLog(WebElement element, String text, String log) {
		element.clear();
		element.sendKeys(text);
		BaseClass.extentInfoLog(log, "");
	}
}
