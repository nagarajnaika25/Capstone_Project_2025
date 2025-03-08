package CapstoneOrangeHrm;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AdminModuletestClass_01 
{
	WebDriver driver;
	AdminModulePageClass_02 p2;
	LoginModulePageClass_02 p1;
	
@BeforeTest
	
	public void  BeforeTest()
	{
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		 
		
		////Page Object class of Login
		// Instantiate the login page object and perform login 
        p1 = new LoginModulePageClass_02(driver);
        String loginResult = p1.ValidLogin("Admin", "admin123");
        System.out.println("Login result URL: " + loginResult);
		
		//Page Object class of Admin
       p2=new AdminModulePageClass_02(driver);
		
		
		
	}
	
	
	
@Test (priority=1)
public void testAdminModule() throws InterruptedException {
    // Now use the admin module methods, e.g., to fetch the menu list
    String menuText = p2.MenuList();
    System.out.println("Admin module menu text: " + menuText);
   
   //Clicked Admin
    
   Thread.sleep(10000);
    String act=driver.getCurrentUrl();
    String Exp="https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers";
    Assert.assertEquals(act, Exp, "Not Matched act and Exp");

      
}

@Test(priority=2)
public void  ByUserName() throws InterruptedException

{
	
	p2.ByUserName("Admin");
	
	//searchbtn.click();
		
}


@Test(priority=3)
public void ByUserRoleAdmin() throws InterruptedException
{
	
	


	p2.ByUserRole("Admin");


}


@Test(priority=4)
public void ByUserStatus() throws InterruptedException

{
	p2.ByUserstatus("Enabled");
	
	
}


}