package com.zerion.utility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.zerion.actiondriver.Action;
import com.zerion.base.BaseClass;

/**
 *	Author : Goutam Naik
 *	Date : 14 Oct 2022
 **/

public class CustomListener extends BaseClass implements ITestListener {

	public void onStart(ITestContext context) {
		test = exprep.createTest(context.getName());
		System.out.println("*** Test Suite " + context.getName() + " started ***");
	}

	public void onTestStart(ITestResult result) {
		test = exprep.createTest(result.getName());
		BaseClass.extentInfoLog(result.getMethod().getDescription(), " : Test is Started");
	}

	public void onTestSuccess(ITestResult result) {
		BaseClass.extentPassLog(result.getName(), " : Test-case Passed");
		exprep.flush();
	}

	public void onTestFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL,
					MarkupHelper.createLabel("Failed Test case is : "+result.getName(), ExtentColor.RED));
			test.log(Status.FAIL,
					MarkupHelper.createLabel("Test Case Failed : "+result.getThrowable(), ExtentColor.RED));
			String imgPath = Action.screenShot(BaseClass.driver, result.getName());
			test.addScreenCaptureFromPath(imgPath);
			exprep.flush();
		}
	}

	public void onTestSkipped(ITestResult result) {
		BaseClass.extentSkipLog(" : Test-case Skipped ",result.getThrowable());
		exprep.flush();
	}

	public void onFinish(ITestContext context) {
		if (exprep != null) {
			exprep.flush();
		}
	}
}
