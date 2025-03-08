package CapstoneOrangeHrm;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginModuletestClass_01 

{
	WebDriver driver;
	LoginModulePageClass_02 p1;
	
	
	
	
	@BeforeTest
	
	public void  BeforeTest()
	{
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		 //Page Object class
		p1=new LoginModulePageClass_02(driver);
	}
	
	@Test
	public void userLoginTest()
	{
	    // Use sample credentials for testing; these could also come from a DataProvider.
	    String result = p1.ValidLogin("Admin", "admin123");
	    System.out.println("Login result URL: " + result);
	    Assert.assertTrue(result.contains("dashboard"), "Login did not navigate to dashboard");
	}

}