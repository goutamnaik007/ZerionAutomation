package com.zerion.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.zerion.actiondriver.Action;
import com.zerion.base.BaseClass;

/**
 * 
 * @author Goutam Naik
 *
 */

public class IntgrationsPage extends BaseClass {

	Actions actions;

	/**
	 * Constructor
	 */
	public IntgrationsPage() {
		PageFactory.initElements(driver, this);
		actions = new Actions(driver);
	}
	
	/**
	 * Locators
	 */
	@FindBy(xpath = "//*[@id='707883_37131pi_707883_37131']")
	WebElement emailInput;
	
	@FindBy(xpath = "//input[@id='707883_37127pi_707883_37127']")
	WebElement firstNameInput;
	
	@FindBy(xpath = "//input[@id='707883_37129pi_707883_37129']")
	WebElement lastNameInput;
	
	@FindBy(xpath = "//input[@id='707883_37139pi_707883_37139']")
	WebElement companyInput;
	
	@FindBy(xpath = "//input[@id='707883_37135pi_707883_37135']")
	WebElement phoneNumInput;
	
	@FindBy(xpath = "//textarea[@id='707883_37141pi_707883_37141']")
	WebElement whatNewInput;
	
	@FindBy(xpath = "//input[@value='Submit']")
	WebElement submitBtn;
	
	@FindBy(xpath = "//iframe[@loading='lazy']")
	WebElement frameWindow;
	
	@FindBy(xpath = "//p[@class='errors']")
	WebElement errorsMsg;
	
	/**
	 * Function to get webelement for the tiles
	 * @param tileTitle pass tile title as a string
	 * @return WebElement
	 */
	public WebElement getTitle(String tileTitle) {
		return driver.findElement(By.xpath("//h3/strong[contains(text(),'" + tileTitle + "')]"));
	}

	/**
	 * Function to get webelement for the tiles description
	 * @param tileTitle pass tile title as a string
	 * @return WebElement
	 */
	public WebElement getTileDescription(String tileTitle) {
		return driver
				.findElement(By.xpath("//h3/strong[contains(text(),'" + tileTitle + "')]/../following-sibling::*"));
	}

	/**
	 * function to naviagate to a page under zerion
	 * @param url pass partial url 
	 */
	public void navigateToPage(String url) {
		String newUrl = prop.getProperty("url") + url;
		driver.navigate().to(newUrl);
		BaseClass.extentInfoLog("Navigated to : ", newUrl);
	}

	/**
	 * Function to verify tile decription
	 * @param tileTitle pass tile title as a string
	 * @param tileCaption pass tile description as a string
	 * @throws InterruptedException
	 */
	public void verifyTile(String tileTitle, String tileCaption) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", this.getTitle(tileTitle));
		actions.moveToElement(this.getTitle(tileTitle)).perform();
		String tileDesc = this.getTileDescription(tileTitle).getText();
		Assert.assertEquals(tileDesc, tileCaption);
		BaseClass.extentInfoLog("verified tile description for '" + tileTitle + "' as : ", tileDesc);
	}

	/**
	 * Function to check redirection for each tile
	 * @param tileTitle pass tile title as a string
	 * @param expectedLink pass expected link after redirection
	 * @throws InterruptedException
	 */
	public void verifyTileRedirection(String tileTitle, String expectedLink) throws InterruptedException {
		String currentUrl, expectedUrl;
		expectedUrl = prop.getProperty("url")+expectedLink;
		Action.scrollToElement(this.getTitle(tileTitle));
		this.getTitle(tileTitle).click();
		currentUrl = driver.getCurrentUrl();
		BaseClass.extentInfoLog("Current URL : ",currentUrl);
		BaseClass.extentInfoLog("expectedUrl URL : ",expectedUrl);
		Assert.assertEquals(currentUrl, expectedUrl);
		BaseClass.extentInfoLog("URL assertion success for : ",tileTitle);
	}

	/**
	 * Function to fill integration request form
	 * @param email pass email as String
	 * @param firstName pass first name as String
	 * @param lastName pass last name as String
	 * @param company pass company name as String
	 * @param phoneNumber pass phone number as String
	 * @param whatNew pass whats new text as String
	 * @throws InterruptedException
	 */
	public void sendIntegrationRequest(String email, String firstName, String lastName, String company,
			String phoneNumber, String whatNew) throws InterruptedException {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		Action.explicitWait(frameWindow, 20);
		driver.switchTo().frame(frameWindow);
		
		Action.sendKeysWithLog(emailInput,email,"Setting email as : "+email);
		Action.sendKeysWithLog(firstNameInput,firstName,"Setting first name as : "+firstName);
		Action.sendKeysWithLog(lastNameInput,lastName,"Setting last name as : "+lastName);
		Action.sendKeysWithLog(companyInput,company,"Setting company name as : "+company);
		Action.sendKeysWithLog(phoneNumInput,phoneNumber,"Setting number as : "+phoneNumber);
		Action.sendKeysWithLog(whatNewInput,whatNew,"Setting 'What new integration would you like' as : "+whatNew);
		
		Action.explicitWait(submitBtn, 20);
		submitBtn.click();
	}

	/**
	 * Function to verify validation error thrown for each of fields.
	 * @param expectedError pass expected validation error as a String.
	 */
	public void verifyRequestFormValidation(String expectedError) {
		Action.explicitWait(errorsMsg, 10);
		String actualError = errorsMsg.getText();
		Assert.assertEquals(actualError, expectedError);
		BaseClass.extentInfoLog("Validation message verified to be : ", actualError);
	}
}
