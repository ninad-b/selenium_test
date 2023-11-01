package demo.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class DemoExtentReporterTest {
	public static ExtentReports getExtentReports() {
		ExtentSparkReporter reporter = new ExtentSparkReporter(
				System.getProperty("user.dir") + "//reports//index.html");
		reporter.config().setDocumentTitle("Demo Project - Automation Test Results");
		reporter.config().setReportName("Automation Results");
		ExtentReports extentReport = new ExtentReports();
		extentReport.attachReporter(reporter);
		extentReport.setSystemInfo("Tester", "Ninad");
		return extentReport;
	}
}
