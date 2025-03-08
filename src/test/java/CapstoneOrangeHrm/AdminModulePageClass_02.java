package CapstoneOrangeHrm;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminModulePageClass_02 {

	@FindBy(css = ".oxd-main-menu-item.active")
	private WebElement adminMenu;

	@FindBy(className = "oxd-main-menu")
	private List<WebElement> menuItems;
	// Search User name

	@FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input")
	private WebElement EnterUsername;

	// Search btn
	@FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")
	private WebElement searchbtn;

	// List of User ByUserName():

	@FindBy(className = "orangehrm-container")
	private List<WebElement> ListofUser;

	// List of User ByUserRole():

	@FindBy(className = "oxd-select-text-input")
	private WebElement UserRole;

	// All Dropdown
	@FindBy(xpath = "//div[contains(@class, 'oxd-select-dropdown')]//div") // Get all options
	private List<WebElement> dropdownOptions;

	// Table Full Data
	@FindBy(className = "orangehrm-container")
	private WebElement userTable;

	// .oxd-table-row

	// .oxd-table-cell

	// ByUserStatus():
 @FindBy(xpath="//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div")private WebElement dropdownclick;




	WebDriver driver;
	WebDriverWait wait;

	public AdminModulePageClass_02(WebDriver d) {

		driver = d;
		PageFactory.initElements(driver, this);// This refers to driver Object

	}

//			public String MenuList() throws InterruptedException
//			{
//				
//				//List Menu
//				
//	        List<WebElement> MenuSize=	driver.findElements(By.className("oxd-main-menu"));
//				
//		    System.out.println("Total Number Of Manue" +  MenuSize.size());
//		    
//		    String text=""; //Created outside the block bcs return statement
//		    for(WebElement  menuItems: MenuSize)
//		    {
//		    	text = menuItems.getText();
//		    	//System.out.println("Menu List"+ text);
//		    	
//		    	
//		    	//Clicked Admin
//	    	Thread.sleep(1000);
//
//		    	
//		    	
//		    	Admin.click();
////		    	
//		    }
//				
//				return text;
//				
//				
//			}
//			
//			
//			

	public String MenuList() {
		// Locate all elements matching the class name
		 List<WebElement> menuItems =
		 driver.findElements(By.className("oxd-main-menu"));

		System.out.println("Total Number Of Menu Items: " + menuItems.size());

		String text = ""; // Will hold the text of the last menu item
		// Iterate using the index so we can re-find the element each time
		for (int i = 0; i < menuItems.size(); i++) {
			// Re-locate the element to avoid stale element reference
			WebElement currentMenuItem = driver.findElements(By.className("oxd-main-menu")).get(i);
			text = currentMenuItem.getText();
			//System.out.println("Menu List: " + text);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement adminMenu = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//a[@href='/web/index.php/admin/viewAdminModule']")));
			adminMenu.click();

		}

		return text;
	}

	// ByUserName() // : 2
//			
//			public String ByUserName(String Username) throws InterruptedException
//			{
//				
//				
//				EnterUsername.sendKeys(Username);
//				searchbtn.click();
//				
//				//Wait
//				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//			    wait.until(ExpectedConditions.visibilityOfAllElements(ListofUser));
//				
//			    boolean isEmpty=false; // Initially, we assume that the list is not empty (false).
//			    
//			    if (ListofUser.isEmpty()) { // Step 2: Check if the list is empty
//			        System.out.println("No users found."); 
//			        isEmpty = true; // Mark as empty
//			        return "No records found"; // Stop execution if no users exist
//			    }
//			    	
//			    
//			    
//			    
//			Thread.sleep(10000);
//				
//			String records=" ";
//			
//			
//		//	StringBuilder recordss = new StringBuilder(); // Use StringBuilder for efficient concatenation
//            System.out.println("Total Number of User  : "+ListofUser.size());
//				
//			 for(WebElement totalrecodrs : ListofUser)
//			 {
//				 
//				 records=totalrecodrs.getText();
//				 System.out.println("Total record found : " + records);
//				 
//		    
//			 }
//		
//			  
//			 return records;
//			
//			}
//			
//			
//}

	// ByUserName()
	public String ByUserName(String Username) throws InterruptedException {

		EnterUsername.sendKeys(Username);
		searchbtn.click();

		Thread.sleep(10000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 500);"); // Scroll down by 500 pixels

		// Wait for elements to be visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-container")));

		// Locate all user rows inside the container
		List<WebElement> userRows = driver.findElements(By.cssSelector(".orangehrm-container .oxd-table-row"));
		System.out.println(" userRows" + userRows.size());

		if (userRows.isEmpty()) {
			System.out.println("No users found.");
			return "No records found";
		}

		Thread.sleep(5000); // Reduce sleep time
		StringBuilder records = new StringBuilder(); // Dynamic
		System.out.println("| Username    | User Role   | Employee Name  | Status  | Actions  |");

		for (WebElement row : userRows) // Iterates All rows
		{

			// Get columns in row

			List<WebElement> columns = row.findElements(By.cssSelector(".oxd-table-cell"));
			if (columns.size() >= 5) { // Ensure correct column structure
				String username = columns.get(0).getText().trim();
				String userRole = columns.get(1).getText().trim();
				String employeeName = columns.get(2).getText().trim();
				String status = columns.get(3).getText().trim();
				String actions = columns.get(4).getText().trim();

				// Format row correctly in horizontal format
				String formattedRow = String.format("| %-12s | %-10s | %-14s | %-7s | %-8s |", username, userRole,
						employeeName, status, actions);
				System.out.println(formattedRow);
				records.append(formattedRow).append("\n");
				driver.navigate().refresh();

			}
		}
		return records.toString();

	}

	// ByUserRole():

	public String ByUserRole(String text) throws InterruptedException {
		UserRole.click(); // Open dropdown

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(dropdownOptions));

		// wait.until(ExpectedConditions.visibilityOfAllElements(dropdownOptions));

		boolean optionFound = false;

		for (WebElement option : dropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(text)) // Admin" and "admin" are considered the same
			{
				option.click();
				optionFound = true;
				break; // return; in a void method simply exits the method early

			}

		}

		if (!optionFound) {
			System.out.println("Option not found: " + text);
			return "Option not found";
		}

		searchbtn.click();

		// Wait for user table to refresh after filtering
		wait.until(ExpectedConditions.visibilityOf(userTable));

		// Scroll the user table into view
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", userTable);
		System.out.println("Filtered by role: " + text);
		// return text;

		// **Iterate Over Table Rows** _Table Row (1) Size

		List<WebElement> userRows = driver.findElements(By.cssSelector(".oxd-table-row.oxd-table-row--with-border"));
		System.out.println("User rows found: " + userRows.size());

		if (userRows.isEmpty()) {
			System.out.println("No users found.");
			return "No records found";
		}

		
		System.out.println("| Username    | User Role   | Employee Name  | Status  |");
		StringBuilder records = new StringBuilder();// Dynamic data

		// Iterate Table Row (2)

		for (WebElement row : userRows) {
		    try {
		        List<WebElement> columns = row.findElements(By.cssSelector(".oxd-table-cell"));
		        if (columns.size() >= 4) {
		            String username = columns.get(0).getText().trim();
		            String userRole = columns.get(1).getText().trim();
		            String employeeName = columns.get(2).getText().trim();
		            String status = columns.get(3).getText().trim();

		            String formattedRow = String.format("| %-12s | %-10s | %-14s | %-7s |", username, userRole, employeeName, status);
		            System.out.println(formattedRow);
		            records.append(formattedRow).append("\n");
		        }
		    } catch (StaleElementReferenceException e) {
		        // Optionally, re-locate this row or log a message
		        System.out.println("Stale element encountered. Skipping this row or re-fetching if needed.");
		    }
		}


		return records.toString();

	}

//ByUserStatus():

	public String ByUserstatus(String text) throws InterruptedException

	{
		Thread.sleep(5000);
		driver.navigate().refresh();
		
	   dropdownclick.click();
	  
	   
	   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(dropdownOptions));

		// wait.until(ExpectedConditions.visibilityOfAllElements(dropdownOptions));

		boolean optionFound = false;

		//Iteration
		for (WebElement option : dropdownOptions) {
			if (option.getText().trim().equalsIgnoreCase(text)) // Admin" and "admin" are considered the same
			{
				option.click();
				optionFound = true;
				break; // return; in a void method simply exits the method early

			}

		}
		if (!optionFound) {
			System.out.println("Option not found: " + text);
			return "Option not found";
		}

		searchbtn.click();
	   
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 500);"); // Scroll down by 500 pixels
		// Wait for elements to be visible
		 wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-container")));

		// Locate all user rows inside the container
		
		
		
		List<WebElement> userRows = driver.findElements(By.cssSelector(".oxd-table-row.oxd-table-row--with-border"));
		System.out.println(" userRows" + userRows.size());


		if (userRows.isEmpty()) {
			System.out.println("No users found.");
			return "No records found";
		}

		Thread.sleep(5000); // Reduce sleep time
		System.out.println("| Username    | User Role   | Employee Name  | Status  | Actions  |");

		StringBuilder records = new StringBuilder(); //Dynamic and Mutable
		
		for (WebElement row : userRows) {
		    try {
		        List<WebElement> columns = row.findElements(By.cssSelector(".oxd-table-cell"));
		        if (columns.size() >= 4) {
		            String username = columns.get(0).getText().trim();
		            String userRole = columns.get(1).getText().trim();
		            String employeeName = columns.get(2).getText().trim();
		            String status = columns.get(3).getText().trim();

		            String formattedRow = String.format("| %-12s | %-10s | %-14s | %-7s |", username, userRole, employeeName, status);
		            System.out.println(formattedRow);
		            records.append(formattedRow).append("\n");
		        }
		    } catch (StaleElementReferenceException e) {
		        // Optionally, re-locate this row or log a message
		        System.out.println("Stale element encountered. Skipping this row or re-fetching if needed.");
		    }
		}

		return records.toString();
       
	   
	   
	// Wait for options to be visible
//	   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//	   List<WebElement> options = wait.until(ExpectedConditions
//	       .visibilityOfAllElementsLocatedBy(By.cssSelector(".oxd-select-dropdown div")));
//
//	   // Iterate over the options and select "Enabled"
//	   boolean optionFound = false;
//	   for (WebElement option : options) {
//	       if (option.getText().trim().equalsIgnoreCase("Enabled")) {
//	           option.click();
//	           optionFound = true;
//	           break;
//	       }
//	   }
//
//	   if (!optionFound) {
//	       System.out.println("Option 'Enabled' not found.");
//	   }
//  
//        
        
        
	}
	
}
	