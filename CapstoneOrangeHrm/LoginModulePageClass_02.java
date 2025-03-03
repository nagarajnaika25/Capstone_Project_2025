package CapstoneOrangeHrm;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginModulePageClass_02 {
	WebDriver driver;
	
	
	
	//Page Object
	//User Name
	@FindBy(css = "input[placeholder='Username']")private WebElement Username;

	
	//Password
	@FindBy(css = "input[placeholder='Password']")private WebElement Password;
	
	
	//Button
	@FindBy(xpath="//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")private WebElement Loginbtn;
	
	

	
	
	
	public  LoginModulePageClass_02(WebDriver d)
	{
		
		driver=d;
		PageFactory.initElements(driver, this);// This refers to driver Object
		
	}
	
	
	public String ValidLogin(String Un , String Pw)
	{
		
		
	
		Username.sendKeys(Un);	
		Password.sendKeys(Pw);
		Loginbtn.click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	     wait.until(ExpectedConditions.urlContains("dashboard"));
	    
	    // Return the current URL after login, which can be used to verify success
	    return driver.getCurrentUrl();
		
	}
	
	
	
	
	
	
	
}
