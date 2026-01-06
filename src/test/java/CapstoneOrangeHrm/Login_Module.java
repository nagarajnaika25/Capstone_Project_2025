package CapstoneOrangeHrm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;



public class Login_Module{

    // Path to the Excel file containing login data
	String excelPath = "C:\\Users\\nagar\\eclipse-workspace\\.metadata_backup\\Capstone_2025\\src\\test\\java\\CapstoneOrangeHrm\\OrangeHrm.xlsx";

    // URL of the OrangeHRM login page
    String orangeHrmUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    File file;
    FileInputStream fis;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    
    WebDriver driver;
    
    
    ExtentReports extent;
    ExtentTest test;

   
	@BeforeTest
    public void setUp() throws IOException {
       
    	// Initialize the Excel file
        file = new File(excelPath);
        fis = new FileInputStream(file);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet("HRM2");
      

        // Initialize the WebDriver
    //    WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(orangeHrmUrl);

        // Set up ExtentReports for reporting
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/Login_Module_Report.html");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Login Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        extent.setSystemInfo("Host Name", "Nagaraj Naik");
        extent.setSystemInfo("Project Name", "CapstoneOrangeHrm");
        extent.setSystemInfo("Tester", "Nagaraj Naik");
        extent.setSystemInfo("OS", "Windows");
        extent.setSystemInfo("Browser", "Chrome");
    }

    @DataProvider
    public Object[][] getLoginData() {
        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getPhysicalNumberOfCells();
        String[][] loginData = new String[rows - 1][cols];

        for (int i = 1; i < rows; i++) 
        {
            XSSFRow row = sheet.getRow(i);
            
           for (int j = 0; j < cols; j++) 
            
           {
                XSSFCell cell = row.getCell(j);
                loginData[i - 1][j] = cell.getStringCellValue();
            }
        }
        return loginData;
    }

    @Test(dataProvider = "getLoginData")
    public void testLogin(String username, String password) {
        test = extent.createTest("Login Test - " + username);

        try {
        	
        	
             
             
            driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(username);
            driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(password);
            driver.findElement(By.xpath("//button[@type='submit']")).click();

            // Generate unique screenshot name
            String screenshotPath = System.getProperty("user.dir") + "/src/test/java/OrangeHrm_ScreenShot/Login_" + username + ".png";

            // Valid login assertion & screenshot
            if (username.equals("Admin") && password.equals("admin123")) {
                Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Valid login failed!");
                captureScreenshot(screenshotPath);
                test.log(Status.PASS, "Valid credentials [" + username + " / " + password + "]");
                test.addScreenCaptureFromPath(screenshotPath);
            } 
            // Invalid login assertion & screenshot
            else {
                boolean isErrorMessageDisplayed = driver.findElement(By.cssSelector(".oxd-alert.oxd-alert--error")).isDisplayed();
                if (isErrorMessageDisplayed) {
                    captureScreenshot(screenshotPath);
                    test.log(Status.FAIL, "Invalid credentials [" + username + " / " + password + "]");
                    test.addScreenCaptureFromPath(screenshotPath);
                    AssertJUnit.fail("Test case failed for invalid credentials!");
                } else {
                    captureScreenshot(screenshotPath);
                    test.log(Status.FAIL, "Error message not displayed for invalid credentials [" + username + " / " + password + "]");
                    test.addScreenCaptureFromPath(screenshotPath);
                    AssertJUnit.fail("Error message not displayed for invalid credentials!");
                }
            }
        } catch (Exception e) {
            String errorScreenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/Error_" + username + ".png";
            captureScreenshot(errorScreenshotPath);
            test.log(Status.FAIL, "Error occurred during login test: " + e.getMessage());
            test.addScreenCaptureFromPath(errorScreenshotPath);
            AssertJUnit.fail("Unexpected error: " + e.getMessage());
        }
    }


    // Method to capture screenshots
    public void captureScreenshot(String filePath) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Ensure the directory exists
            File destinationFile = new File(filePath);
            destinationFile.getParentFile().mkdirs(); // Creates directory if it doesn't exist

            FileHandler.copy(sourceFile, destinationFile);
            System.out.println("Screenshot saved: " + filePath);
        } catch (IOException e) {
            System.out.println("Screenshot capture failed: " + e.getMessage());
        }
    }

  
	@AfterTest
    public void tearDown() throws IOException 
	{
        fis.close();
        workbook.close();
        driver.quit();
        extent.flush();
 
	}
}
