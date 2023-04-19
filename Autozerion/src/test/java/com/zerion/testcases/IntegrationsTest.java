package com.zerion.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.zerion.base.BaseClass;
import com.zerion.pageobjects.IntgrationsPage;
import com.zerion.utility.DataProviders;

public class IntegrationsTest extends BaseClass {
	
	IntgrationsPage integration_obj;
	
	@BeforeMethod
	public void setup() throws InterruptedException {
		launchApplication();
		integration_obj = new IntgrationsPage();
	}
	
	@Test(priority=1, description="Check tile contents", dataProvider = "ingrationsTileContents", dataProviderClass = DataProviders.class, enabled = false)
	public void Integration_checkTileContents(String testCase, String page, String tileTitle, String tileDescription) throws InterruptedException {
		extentInfoLog("Test case : ",testCase);
		integration_obj.navigateToPage(page);
		integration_obj.verifyTile(tileTitle,tileDescription);
	}
	
	@Test(priority=2, description="Check tile redirections", dataProvider = "verifyLinkRedirections", dataProviderClass = DataProviders.class, enabled = false)
	public void Integration_checkTileRedirections(String testCase, String page, String tileTitle, String expectedLink) throws InterruptedException {
		extentInfoLog("Test case : ",testCase);
		integration_obj.navigateToPage(page);
		integration_obj.verifyTileRedirection(tileTitle,expectedLink);
	}
	
	@Test(priority=3, description="Check request form validations", dataProvider = "validations", dataProviderClass = DataProviders.class, enabled = true)
	public void Integration_verifyRequestFormValidations(String testCase, String page, String email, String firstName, String lastName, String company, String phoneNumber, String whatNew, String expectedError) throws InterruptedException {
		extentInfoLog("Test case : ",testCase);
		integration_obj.navigateToPage(page);
		integration_obj.sendIntegrationRequest(email,firstName,lastName,company,phoneNumber,whatNew);
		integration_obj.verifyRequestFormValidation(expectedError);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
