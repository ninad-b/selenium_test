package demo.Components;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import demo.abstractComponents.CommonUtility;
import demo.resources.DemoExtentReporterTest;

public class DemoTestListeners extends InitTests implements ITestListener{
	
	ExtentReports extentReports = DemoExtentReporterTest.getExtentReports();
	ExtentTest extentTest;
	ThreadLocal<ExtentTest> threadInstance = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest = extentReports.createTest(result.getMethod().getMethodName());
		threadInstance.set(extentTest);
		try {
			driver = (WebDriver)result.getTestClass().getRealClass()
					.getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//ITestListener.super.onTestStart(result);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		threadInstance.get().log(Status.PASS, "Passed successfully");
		//extentTest.log(Status.PASS, "Passed successfully");
		//ITestListener.super.onTestSuccess(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		//ITestListener.super.onTestFailure(result);
		String screenShotPath = null;
		//extentTest.fail(result.getThrowable());
		//extentTest.log(Status.FAIL, "Test Failed : "+result.getThrowable());
		threadInstance.get().fail(result.getThrowable());
		threadInstance.get().log(Status.FAIL, "Test Failed : "+result.getThrowable());
		try {
			screenShotPath = getScreenShot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//extentTest.addScreenCaptureFromPath(screenShotPath);
		threadInstance.get().addScreenCaptureFromPath(screenShotPath, result.getMethod().getMethodName()+" - ErrorSnap");
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		//ITestListener.super.onFinish(context);
		
		extentReports.flush();
	}

}
