package EcommerceApplication;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Ecommerce_Client {
    
    WebDriver driver;
    Ecommerce_Utility utilitypg;
    
    ExtentReports extent;
    ExtentTest test;
    
    @BeforeTest
    public void BeforeTest()
    {
    	extent = Ecommerce_Utility.setupExtentReport();
    
    	
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        
        utilitypg = new Ecommerce_Utility(driver);
    }
    
    
    //SignUpUser
    
    @Test (priority=1)
    public void SignUpUser() throws InterruptedException {
    	String scrpath=System.getProperty("user.dir")+"/src/test/java/Ecommerce_Screenshot/SignUpUser.png";
    	test=Ecommerce_Utility.craeteTest("SignUpUser");
    	test.log(Status.INFO,"Starting SignUpUser test");
    	
    	try {
    	
        utilitypg.SignUp_01();
        Thread.sleep(5000);
        utilitypg.SignUpUsername("NaGaraj");
        Thread.sleep(5000);
        utilitypg.SignUpPassword("Nagarajnaik1234");
        utilitypg.ScreenShot(scrpath);
        Thread.sleep(5000);
        utilitypg.SignUpButton();
        utilitypg.HandleAlert();
        test.pass("SignUp successfully");
       
        
    	}catch(Exception e)
    	{
    		test.fail("SignUp Not successfully"+e);
    		System.out.println("Exception"+e.getMessage());
    		 utilitypg.ScreenShot(scrpath);
    	}
    	
        
    }
    
    //SignInUser
    
    @Test(priority=2)
    public void SignInUser() throws InterruptedException
    {
    
    	
    	String scrpath=System.getProperty("user.dir")+"/src/test/java/Ecommerce_Screenshot/SignInUser.png";
    	
    	test=Ecommerce_Utility.craeteTest("SignInUser");
    	test.log(Status.INFO, "Starting SignInuser Test");
    	
    	try {
    	utilitypg.SignInUs("NaGaraj", "Nagarajnaik1234");
    	
    	
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loggedInUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser"))); 
        utilitypg.ScreenShot(scrpath);
        String actualText = loggedInUser.getText();
        Assert.assertTrue(actualText.contains("NaGaraj"), "Login Failed: Username not displayed.");
        test.pass("User logged in successfully as :"+ actualText);
       
        
    	}
    	catch(Exception e)
    	{
    		test.fail("SignIn failed "+e.getMessage());
    		utilitypg.ScreenShot(scrpath);
    		
    	}
        
    }
    
    
    
    //Add To cart
    @Test(priority=3)
    public void SelectMobile() throws InterruptedException
    {
    	String scrpath=System.getProperty("user.dir")+"/src/test/java/Ecommerce_Screenshot/SelectMobile.png";
    	test=Ecommerce_Utility.craeteTest("SelectMobile");
    	test.log(Status.INFO,"Selecting Mobile");
    	
    	try {
        String mobileName = utilitypg.selectedMobile();
        Assert.assertTrue(mobileName != null && mobileName.toLowerCase().contains("iphone 6"), "Mobile not selected");
        test.pass("Mobile selected successfully");
    	 utilitypg.ScreenShot(scrpath);
    }catch(Exception e)
    
    	{
    	
    	test.fail("Failed  to select mobile"+e.getMessage());
    	
    	  utilitypg.ScreenShot(scrpath);
    	}
    }
    	
    	
    	
    	
    	
    	
    @Test(priority=4)
    public void AddTocCart()
    {	  
    	
    	String scrpath=System.getProperty("user.dir")+"/src/test/java/Ecommerce_Screenshot/AddTocCart.png";

    	test=Ecommerce_Utility.craeteTest("AddToCart");
    	test.log(Status.INFO,"Adding Item to cart");
    	
    	try {
        utilitypg.ScreenShot(scrpath);
    	utilitypg.AddToCart();
    	test.pass("Item added to cart Successfully");
    	
    	}catch(Exception e )
    	{
    		test.fail("Failed to add item to cart"+e.getMessage());
    		utilitypg.ScreenShot(scrpath);
    	}
    	
    }
    
  @Test(priority=5)
    
    public void placeOrderBy()
    {
	  String scrpath=System.getProperty("user.dir")+"/src/test/java/Ecommerce_Screenshot/placeOrderBy.png";
	  test=Ecommerce_Utility.craeteTest("placeOrderByUser");
	  test.log(Status.INFO,"Placing Order");
	  
	try {
		utilitypg.ScreenShot(scrpath);
	    boolean isClicked = utilitypg.placeOrder();
	    Assert.assertTrue(isClicked, "Place Order button click failed");
		
	}catch(Exception e)
	
	{	
		
		test.fail("Order placement failed " +e.getMessage());
		utilitypg.ScreenShot(scrpath);
	}
	      
	      
	      
	  }

    
  
    @Test(priority=6)
    
    public void UserandOrderDetails()
    
    {
    	
    	String scrpath=System.getProperty("user.dir")+"/src/test/java/Ecommerce_Screenshot/UserandOrderDetails.png";
    	  test = Ecommerce_Utility.craeteTest("UserandOrderDetails");
          test.log(Status.INFO, "Entering user and order details");
    	
    	try {
    		
    	utilitypg.UserandOrderdata("NaGaraj", "India", "Bangalore", "589845855865", "04", "2025");
    	utilitypg.ScreenShot(scrpath);
    	test.pass("User and Order details entered successfully");
    	Thread.sleep(1000);
    	
    	utilitypg.logoutuser();
    	}
    	
    	catch(Exception e)
    	{
    		
    		test.fail("Failed to enter user and order details: " + e.getMessage());
    		utilitypg.ScreenShot(scrpath);
    	}
    	
   }
    
    
    @AfterTest
    public void afterTest() {
        // Flush the report so changes are written to the file
        Ecommerce_Utility.flushReport();
        driver.quit();
    }
}
