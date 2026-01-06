package Telecom_Domain;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class ExtentUtility {
	
	
	private static ExtentReports extent; //  1
	private  static  ExtentTest test;  //    2
	
	
	
	
	
	public static ExtentReports setupExtentReport()
	{

		
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/APISparkReport.html");
		htmlReporter.config().setDocumentTitle("API Test Report");
		htmlReporter.config().setReportName("RestAssured API Testing");
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		extent=new ExtentReports();
		extent.attachReporter(htmlReporter);
		return extent;
		
		
		
	}
	
	//ExtentTest
	public static ExtentTest craeteTest(String testName)
	{
		
	    test=extent.createTest(testName);
		return test;
	}
	
	public static void  flushReport()
	{
		
		extent.flush();
		
	}
	
	
	
	
	
	

}
