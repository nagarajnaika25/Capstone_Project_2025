package EcommerceApplication;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Ecommerce_Utility {
    
	
	
	
	
	
	//SignUp
    @FindBy(id="signin2") private WebElement SignUp; // SignUp button
    @FindBy(id="sign-username") private WebElement SignUpUserName; // Username field
    @FindBy(id="sign-password") private WebElement SignUpPsw; // Password field
    @FindBy(xpath="//*[@id=\"signInModal\"]/div/div/div[3]/button[2]") private WebElement SignUpBtn; // SignUp button
    @FindBy(xpath="//*[@id=\"signInModal\"]/div/div/div[3]/button[1]")private WebElement close;
    //Login 
    
    @FindBy(id="login2")private WebElement login;
    @FindBy(id="loginusername")private WebElement LoginUsername;
    @FindBy(id="loginpassword")private WebElement LoginPassword;
    @FindBy(xpath="//*[@id=\"logInModal\"]/div/div/div[3]/button[2]")private WebElement LoginButton;
    
    
    //Select Mobile
    @FindBy(xpath="//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'iphone 6')]")
    private WebElement SelectIphone;

    //Add To cart
    @FindBy(xpath="//*[@id=\"tbodyid\"]/div[2]/div/a")
    private WebElement AddtoCart;

    //Cart
    @FindBy(linkText="Cart") private WebElement Cart;
    
    //Place Order
    
    @FindBy(xpath="//*[@id=\"page-wrapper\"]/div/div[2]/button")private WebElement placeOrder;
     //Place Order Data
    @FindBy(id="name")private WebElement name;
    @FindBy(id="country")private WebElement country;
    @FindBy(id="city")private WebElement city;
    @FindBy(id="card")private WebElement CreditCard;
    @FindBy(id="month")private WebElement month;
    @FindBy(id="year")private WebElement year;
    @FindBy(xpath="//*[@id=\"orderModal\"]/div/div/div[3]/button[2]")private WebElement Purchase;
    
    @FindBy(css = "p.lead.text-muted")
    private WebElement purchaseDetails;

    
    @FindBy(xpath="/html/body/div[10]/div[7]/div/button")private WebElement Ok;
    
    @FindBy(id="logout2")private WebElement logout;
    
    
    
    
    WebDriver driver;
    WebDriverWait wait;
    private static ExtentReports extent; //  1
	private  static  ExtentTest test;  //    2
	
    
    // Constructor
    public Ecommerce_Utility(WebDriver d) {
        this.driver = d;
        PageFactory.initElements(driver, this);
    }
    
    // Click SignUp Link
    public void SignUp_01() {
        SignUp.click();
    }
    
    // Enter SignUp Username
    public void SignUpUsername(String SignU) {
        SignUpUserName.sendKeys(SignU);
    }

    // Enter SignUp Password
    public void SignUpPassword(String SignP) {
        SignUpPsw.sendKeys(SignP);
    }
    
    // Click SignUp Button
    public void SignUpButton() throws InterruptedException {
        try {
            SignUpBtn.click();
            Thread.sleep(2000); // Wait for potential alert

            // Switch to alert
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert Message: " + alertText);

            if (alertText.contains("This user already exist.")) {
                alert.accept();  // Click OK on alert
                System.out.println("User already exists, alert accepted.");

                Thread.sleep(1000); // Wait for the modal to remain visible

                // Ensure 'close' button is clickable before clicking
                if (close.isDisplayed()) {
                    close.click();
                    System.out.println("Sign-up modal closed.");
                } else {
                    System.out.println("Close button not visible.");
                }
            }
        } catch (NoAlertPresentException e) {
            System.out.println("No alert appeared, proceeding normally.");
        }
    }

    
    // Handle Alert
    public void HandleAlert() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("alert('This is a test alert');"); 

        try {
            // Switch to alert and accept (Click OK)
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert Text: " + alert.getText());
            Thread.sleep(2000);
            alert.accept();
            System.out.println("Test alert accepted.");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present.");
        }
    }

    //Login
    
    
     
  public void SignInUs(String Un, String Pw) throws InterruptedException  
    
  {
	  login.click();
	  Thread.sleep(5000);
	  LoginUsername.sendKeys(Un);
	  Thread.sleep(5000);
	  LoginPassword.sendKeys(Pw);
	  LoginButton.click();
	  
	  
	  
	  try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        System.out.println("Alert Text: " + alert.getText());
	        alert.accept();  // Accept the alert
	    } catch (Exception e) {
	        System.out.println("No alert appeared during sign-in.");
	    }
	}
  
    
  //Select Mobile
  public String selectedMobile() {
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement mobileElement = wait.until(ExpectedConditions.elementToBeClickable(SelectIphone));

	    String mobileText = mobileElement.getText();
	 
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,500)");
	    
	    mobileElement.click();

	    return mobileText;
	}

 

  
  // Check Alert is present 
  
  private boolean isAlertPresent()
  {
	  
	  try{
		  
	  driver.switchTo().alert();
	  return true;
	  
	  }catch(Exception e)
	  {
		  
		  return false;
		  
	  }
	 
	  
  }
  
  //Add To Cart

  public void AddToCart() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    try {
	      
	        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(AddtoCart));

	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
	       
	        addToCartBtn.click();
	        System.out.println("Clicked 'Add to Cart' successfully!");

	        // Handle alert if it appears
	        if (isAlertPresent()) {
	            Alert alert = driver.switchTo().alert();
	            System.out.println("Alert Text: " + alert.getText());
	            alert.accept();
	        }

	        
	        WebElement cartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Cart")));
	        cartBtn.click();
	        System.out.println("Navigated to Cart!");

	    } catch (Exception e) {
	        System.out.println("Exception in AddToCart: " + e.getMessage());
	    }
	}
  
 //PlaceOrder
  public boolean placeOrder() {
	    try {
	        
	        WebElement placeOrderElement = wait.until(ExpectedConditions.elementToBeClickable(placeOrder));
	        // Scroll into view if needed
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeOrderElement);
	        Thread.sleep(1000);
	        // Click the button
	        placeOrderElement.click();
	        System.out.println("Clicked 'Place Order' successfully!");
	        return true;
	    } catch (Exception e) {
	        System.out.println("Exception in placeOrder: " + e.getMessage());
	        return false;
	    }
	}

  //Details
 public void UserandOrderdata(String nm,String cont,String ct,String Ccardd,String mt, String yr)
 {
	 
	
    
    
    try {
    	
    	
    	name.sendKeys(nm);
    	country.sendKeys(cont);
    	city.sendKeys(ct);
    	CreditCard.sendKeys(Ccardd);
    	month.sendKeys(mt);
    	year.sendKeys(yr);
        Purchase.click();
        
    	Thread.sleep(5000);
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement Orderid= wait.until(ExpectedConditions.visibilityOf(purchaseDetails));
    	
        String Orderdata=Orderid.getText();
        System.out.println("Order Details : "+Orderdata);
        
    	Ok.click();
    	
    	
    }
    
    
    catch(Exception e)
    {
    	
    	System.out.println("Exception : "+e.getMessage());
    }
    
 }
 
 
 //Extent Report
	
	public static ExtentReports setupExtentReport()
	{

		
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/Ecommerce.html");
		htmlReporter.config().setDocumentTitle("Ecommerce Test Report");
		htmlReporter.config().setReportName("Selenium TestNgTesting");
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
	
	
	//ScreenShot
	public void ScreenShot(String screenshotpath)
	{
		try {
			
			 TakesScreenshot screenshot = (TakesScreenshot) driver;
			 File sourcefile=screenshot.getScreenshotAs(OutputType.FILE);
					
			 File destinationFile=new File(screenshotpath);
			 destinationFile.getParentFile().mkdirs();
			 
			 
			 FileHandler.copy(sourcefile, destinationFile);
			 System.out.println("ScreenShot Saved"+screenshotpath);
			
			 
			 

		}catch(Exception e)
		{
			
			System.out.println("ScreenShot Capture failed"+e.getMessage());
		}
		
		
	}
	
	
	public void logoutuser()
	{
		logout.click();
		
	}
	
	
	

	
}
    
    
    
    
    
    
    
	  

 






