package Telecom_Domain;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;




//System.out.println("Response statusCode " + response.getStatusCode());
//System.out.println("Response Body: "+ response.getBody().asString());
//
//token = response.jsonPath().getString("token");
//System.out.println("Token is  :"+ token);
//Assert.assertEquals(response.getStatusCode(), 201, "Status code does not match!");


public class RestassuredApiTest {
	  private static final String BASE_URL = "https://thinking-tester-contact-list.herokuapp.com";
	    private static String token;
	    private static String Ltoken;
	    private static String id;  
	    private static ExtentReports extent;
	    private static ExtentTest test;

	    @BeforeTest
	    public void setupBaseUrl()
	    {
	        RestAssured.baseURI = BASE_URL;
	        extent = ExtentUtility.setupExtentReport();
	    }

	    @Test(priority = 1)
	    public void addNewUser() {
	        test = ExtentUtility.craeteTest("Add New User_01");
            Map<String, String> userPayload = new HashMap<>();
	        userPayload.put("firstName", "Nagaraj");
	        userPayload.put("lastName", "Naik");
	        userPayload.put("email", "nagarajnaiknw07@gmail.com");
	        userPayload.put("password", "NagarajNaik@2025");

	        Response response = given()
	                .header("Content-Type", "application/json")
	                .body(userPayload)
	                .when()
	                .post("/users")
	                .then()
	                .extract().response();

	        test.log(Status.INFO, "Creating a new user");

	        try {
	            Assert.assertEquals(response.getStatusCode(), 201, "Status code does not match!");
	            System.out.println("Response Body: "+ response.getBody().asString());
	            token = response.jsonPath().getString("token");
	            Assert.assertNotNull(token, "Token should not be null");

	            test.log(Status.PASS, "User created successfully. Token: " + token);
	        } catch (AssertionError e) {
	            test.log(Status.FAIL, e.getMessage());
	            throw e;
	        }
	    }

	    @Test(priority = 2)
	    public void testGetUserProfile() {
	        test = ExtentUtility.craeteTest("testGetUserProfile_02");

	        Response response = given()
	                .header("Authorization", "Bearer " + token)
	                .when()
	                .get("/users/me")
	                .then()
	                .extract().response();

	        try {
	            Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
	            test.log(Status.PASS, "User profile retrieved successfully.");
	        } catch (AssertionError e) {
	            test.log(Status.FAIL, e.getMessage());
	            throw e;
	        }
	    }

	    @Test(priority = 3)
	    public void updateUserProfile() {
	        test = ExtentUtility.craeteTest("UpdateUserProfile_03");

	        Map<String, String> userPayload = new HashMap<>();
	        userPayload.put("firstName", "Nagarajaa");
	        userPayload.put("lastName", "M Naik");
	        userPayload.put("email", "nagarajnaiknw07@gmail.com");
	        userPayload.put("password", "Nagaraj@2025");

	        Response response = given()
	                .header("Content-Type", "application/json")
	                .header("Authorization", "Bearer " + token)
	                .body(userPayload)
	                .when()
	                .patch("/users/me")
	                .then()
	                .extract().response();

	        try {
	            Assert.assertEquals(response.getStatusCode(), 200, "Status code does not match!");
	            test.log(Status.PASS, "User profile updated successfully.");
	        } catch (AssertionError e) {
	            test.log(Status.FAIL, e.getMessage());
	            throw e;
	        }
	    }

	    @Test(priority = 4)
	    public void loginUser() {
	        test = ExtentUtility.craeteTest("loginUser_04");

	        Map<String, String> userPayload = new HashMap<>();
	        userPayload.put("email", "nagarajnaiknw07@gmail.com");
	        userPayload.put("password", "Nagaraj@2025");

	        Response response = given()
	                .header("Content-Type", "application/json")
	                .body(userPayload)
	                .when()
	                .post("/users/login")
	                .then()
	                .extract().response();

	        try {
	            Assert.assertEquals(response.getStatusCode(), 200, "Status code does not match!");
	            Ltoken = response.jsonPath().getString("token");
	            Assert.assertNotNull(Ltoken, "Token should not be null");
	            test.log(Status.PASS, "User logged in successfully. Token: " + Ltoken);
	        } catch (AssertionError e) {
	            test.log(Status.FAIL, e.getMessage());
	            throw e;
	        }
	        
//	        JsonPath json =response.jsonPath();
//	        json.getInt(BASE_URL)
	    }

	    @Test(priority = 5)
	    public void addContact() {
	        test = ExtentUtility.craeteTest("AddContact_05");

	        Map<String, String> contactPayload = new HashMap<>();
	        contactPayload.put("firstName", "Pavitr");
	        contactPayload.put("lastName", "Maa Naik");
	        contactPayload.put("birthdate", "2000-01-01");
	        contactPayload.put("email", "pavinaiknew901@gmail.com");
	        contactPayload.put("Mobilenumber", "9353127257");
	        contactPayload.put("street1", "1 Main St");
	        contactPayload.put("street2", "Apartment Asss");
	        contactPayload.put("city", "Bangalore");
	        contactPayload.put("stateProvince", "Karnataka");
	        contactPayload.put("postalCode", "560061");
	        contactPayload.put("country", "India");

	        Response response = given()
	                .header("Content-Type", "application/json")
	                .header("Authorization", "Bearer " + Ltoken)
	                .body(contactPayload)
	                .when()
	                .post("/contacts")
	                .then()
	                .extract().response();

	        try {
	            Assert.assertEquals(response.getStatusCode(), 201, "Status code does not match!");
	            id = response.jsonPath().getString("_id");
	            Assert.assertNotNull(id, "Contact ID should not be null");
	            test.log(Status.PASS, "Contact added successfully. ID: " + id);
	        } catch (AssertionError e) {
	            test.log(Status.FAIL, e.getMessage());
	            throw e;
	        }
	 }
	
    
    //Get Contact List
    @Test(priority =6)
    
    public void getContactList() 
    {
    	
    	test=ExtentUtility.craeteTest("getContactList_06");
        Response response = given()
        		.header("Authorization", "Bearer " + Ltoken)
                .when()
                .get("/contacts")
               .then()
                .statusCode(200)
                .extract().response();
try {
       Assert.assertEquals(response.statusCode(), 200, "Status code  be 200 OK");
       JsonPath data=response.jsonPath();
       System.out.println("Json data"+data.prettify());
       
       System.out.println("Get Contact List Response: " + response.getBody().asString());
       test.log(Status.PASS, "Token received: " + Ltoken);
      
    
     }catch (AssertionError e) {
    	    test.log(Status.FAIL, e.getMessage());
    	    throw e;
     }
    }
    
    
    
    //Get Contact
    
    @Test(priority=7)
public void getContact()

{
    	test=ExtentUtility.craeteTest("getContact_07");
    	  Response response = given()
                  .header("Authorization", "Bearer " + Ltoken)
                  .when()
                  .get("/contacts/" + id)
                   .then()
                  .statusCode(200)
                  .extract().response();
    	  
    	  try {
          System.out.println("Get Contact Response: " + response.getBody().asString());

          Assert.assertEquals(response.statusCode(), 200, "Status code should not be 200 OK");
         JsonPath data=response.jsonPath();
         System.out.println("Json data"+data.prettify());
    	  }
         catch (AssertionError e) {
             test.log(Status.FAIL, e.getMessage());
             throw e;
      
}    	}


//Update Contact 

    
    // Update Contact using PUT (full update) including all required fields
    @Test (priority=8)
    public void UpdateContact() {
    	
    	test=ExtentUtility.craeteTest("UpdateContact_08");
    	
        Map<String, String> userPayload = new HashMap<>();
        userPayload.put("firstName", "abhi");
        userPayload.put("lastName", "Naik");
        userPayload.put("birthdate", "1998-01-01");
        userPayload.put("email", "Abhinaiknew55@gmail.com");
        userPayload.put("Mobilenumber", "9353129257"); // Ensure this key matches the API spec
        userPayload.put("street1", "1 Main St");
        userPayload.put("street2", "Apartment ss");
        userPayload.put("city", "Bangalore");
        userPayload.put("stateProvince", "Karnataka");
        userPayload.put("postalCode", "560888");
        userPayload.put("country", "India");
       

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Ltoken)
                .body(userPayload)
                .when()
                .put("/contacts/" + id)
                .then()
                .statusCode(200)
                .extract().response();

        try {
        	
        	Assert.assertEquals(response.getStatusCode(), 200, "Status code does not match!");
            test.log(Status.PASS, "Contact list updated successfully.");
             System.out.println("Update Contact (PUT) Response: " + response.getBody().asString());
        // Validate that the email was updated
      
   
        }
        catch (AssertionError e) {
            test.log(Status.FAIL, e.getMessage());
            throw e;
    }
    
    }
    
    
    @Test(priority = 9)
    public void updateContactPatch()
    {
    	
    	test=ExtentUtility.craeteTest("updateContactPatch_09");
    	
        Map<String, String> payload = new HashMap<>();
        payload.put("firstName", "Kiran");

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Ltoken)
                .body(payload)
                .when()
                .patch("/contacts/" + id)
                .then()
                .statusCode(200)
                .extract().response();
        
        
        try {
        	
        
          Assert.assertEquals(response.getStatusCode(), 200, "Status code does not match!");
         
        System.out.println("Update Contact (PATCH) Response: " + response.getBody().asString());
        
       // JsonPath json= response.jsonPath();
        // Validate that the firstName is updated to "Anna"
        String updatedFirstName = response.jsonPath().getString("firstName");
        Assert.assertEquals(updatedFirstName, "Kiran", "First name should be updated to Kiran");
        test.log(Status.PASS, "Contact list updated successfully.");
        
    } catch (AssertionError e) {
        test.log(Status.FAIL, e.getMessage());
        throw e;
        
    }}
    
 // Test Case 10: Logout User
    
    
    @Test(priority=10)
    public void logoutUser() {
    	
    	test=ExtentUtility.craeteTest("logoutUser_10");
    	
    	// System.out.println("Ltoken before logout: " + Ltoken);
        Response response = given()
                .header("Authorization", "Bearer " + Ltoken)  // Using the login token
                .when()
                .post("/users/logout")
                .then()
               .extract().response();

        System.out.println("Logout User Response: " + response.getBody().asString());
        try {
        	
        	 
            Assert.assertEquals(response.getStatusCode(), 200, "Status code does not match!");
            test.log(Status.PASS, "Log out successfully.");
            
        }

        catch (AssertionError e) {
        	  test.log(Status.FAIL, "Logout failed: " + e.getMessage());
            throw e;
        }
    }
    
    
    @AfterTest
    public void tearDown() {
    	ExtentUtility.flushReport();
    }

}
    
    
    
    
    





    
    














//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import org.testng.Assert;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static io.restassured.RestAssured.given;
//
//public class RestAssuredAPITest {
//    private static String BASE_URL = "https://thinking-tester-contact-list.herokuapp.com";
//    private static String token;
//
//    @BeforeClass
//    public void setup() {
//        RestAssured.baseURI = BASE_URL;
//    }
//
//    @Test(priority = 1)
//    public void testAddNewUser() {
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("firstName", "Test");
//        requestBody.put("lastName", "User");
//        requestBody.put("email", "test@fake.com");
//        requestBody.put("password", "myPassword");
//
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .body(requestBody)
//                .when()
//                .post("/users")
//                .then()
//                .statusCode(201)
//                .extract().response();
//
//        token = response.jsonPath().getString("token");
//        Assert.assertNotNull(token, "Token should not be null");
//    }
//
//    @Test(priority = 2, dependsOnMethods = {"testAddNewUser"})
//    public void testGetUserProfile() {
//        Response response = given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/users/me")
//  0              .then()
//                .statusCode(200)
//                .extract().response();
//
//        Assert.assertEquals(response.statusCode(), 200, "Status code should be 200 OK");
//    }
//
//    @Test(priority = 3, dependsOnMethods = {"testAddNewUser"})
//    public void testUpdateUser() {
//        Map<String, String> updateBody = new HashMap<>();
//        updateBody.put("firstName", "Updated");
//        updateBody.put("lastName", "Username");
//        updateBody.put("email", "test2@fake.com");
//        updateBody.put("password", "myNewPassword");
//
//        Response response = given()
//                .header("Authorization", "Bearer " + token)
//                .header("Content-Type", "application/json")
//                .body(updateBody)
//                .when()
//                .patch("/users/me")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        Assert.assertEquals(response.statusCode(), 200, "Status code should be 200 OK");
//    }
//}



//Mock Servrer  for API testing
//designer.mocky.io - Create mock servi
//jsoneditoronline.org  -Comapre
//




//✅ Create POST → GET → PATCH → GET flow in Postman Mock Server
//✅ Test the same flow using REST Assured
//(Even though mock server is fake, your tests will PASS/FAIL correctly)
//
//This is exactly how companies use mock servers before backend is ready.
//
//⭐ PART 1 — Create POST / GET / PATCH API in Postman Mock Server
//🟩 STEP 1 — Create New Mock Server
//
//Open Postman
//
//Click → New
//
//Select → Mock Server
//
//Choose → Mock a request & response
//
//Name → User API Mock
//
//Click → Create Mock Server
//
//You will get a mock URL like:
//
//https://abcd123.mock.pstmn.io
//
//🟦 STEP 2 — Create Mock for POST /users
//In Postman:
//
//Create new request
//
//Select POST
//
//URL:
//
//{{mockUrl}}/users
//
//
//Go to Body → raw → JSON
//
//{
//  "name": "Nagaraj",
//  "age": 28
//}
//
//
//Go to Example (top right)
//Click Add Example
//
//Add Mock Response:
//
//Status: 201 Created
//
//Body:
//
//{
//  "message": "User created (mock)",
//  "id": 1
//}
//
//
//Save
//
//🟪 STEP 3 — Create Mock for GET /users
//
//New request → GET
//
//URL:
//
//{{mockUrl}}/users
//
//
//Add Example
//
//Mock response:
//
//Status 200
//
//Body:
//
//[
//  {
//    "id": 1,
//    "name": "Nagaraj",
//    "age": 28
//  }
//]
//
//🟧 STEP 4 — Create Mock for PATCH /users/1
//
//New request → PATCH
//
//URL:
//
//{{mockUrl}}/users/1
//
//
//Body:
//
//{
//  "name": "Updated Name"
//}
//
//
//Add Example with response:
//
//Mock response:
//
//Status 200
//
//Body:
//
//{
//  "message": "User updated (mock)",
//  "id": 1
//}
//
//🟩 STEP 5 — Create Mock for GET /users/1
//
//New request → GET
//
//URL:
//
//{{mockUrl}}/users/1
//
//Mock response:
//
//Status 200
//
//Body:
//
//{
//  "id": 1,
//  "name": "Updated Name",
//  "age": 28
//}
//
//⭐ Completed Mock Setup
//
//Your mock server now has:
//
//Method	Endpoint	Description
//POST	/users	Create user
//GET	/users	List users
//PATCH	/users/1	Update user
//GET	/users/1	Fetch updated user
//⭐ PART 2 — Test This Flow With RestAssured
//
//Set your base URI:
//
//RestAssured.baseURI = "https://abcd123.mock.pstmn.io";
//
//🟢 Test 1 — POST Create User
//@Test
//public void createUser() {
//
//    String body = """
//    {
//      "name": "Nagaraj",
//      "age": 28
//    }
//    """;
//
//    given()
//        .header("Content-Type", "application/json")
//        .body(body)
//    .when()
//        .post("/users")
//    .then()
//        .statusCode(201)
//        .body("message", equalTo("User created (mock)"))
//        .body("id", equalTo(1));
//}
//
//🟣 Test 2 — GET Users List
//@Test
//public void getUsers() {
//    given()
//    .when()
//        .get("/users")
//    .then()
//        .statusCode(200)
//        .body("[0].id", equalTo(1))
//        .body("[0].name", equalTo("Nagaraj"));
//}
//
//🟠 Test 3 — PATCH Update User
//@Test
//public void updateUser() {
//
//    String updateBody = """
//    {
//      "name": "Updated Name"
//    }
//    """;
//
//    given()
//        .header("Content-Type", "application/json")
//        .body(updateBody)
//    .when()
//        .patch("/users/1")
//    .then()
//        .statusCode(200)
//        .body("message", equalTo("User updated (mock)"));
//}
//
//🔵 Test 4 — GET User After Update
//@Test
//public void getUpdatedUser() {
//
//    given()
//    .when()
//        .get("/users/1")
//    .then()
//        .statusCode(200)
//        .body("name", equalTo("Updated Name"));
//}



//Token validation 

//POST https://api.example.com/login
//Body: { "username": "user", "password": "pass" }
//Response: { "token": "abcd1234" }
//You can first call this endpoint and extract the token.
//
//java
//Copy code
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.*;
//
//public class AuthTest {
//    public static void main(String[] args) {
//
//        // Step 1: Get token from login API
//        Response loginResponse = given()
//            .header("Content-Type", "application/json")
//            .body("{\"username\":\"user\",\"password\":\"pass\"}")
//        .when()
//            .post("https://api.example.com/login")
//        .then()
//            .statusCode(200)
//            .extract().response();
//
//        // Extract token from response JSON
//        String token = loginResponse.jsonPath().getString("token");
//        System.out.println("Token: " + token);
//
//        // Step 2: Use token to access protected API
//        given()
//            .header("Authorization", "Bearer " + token)
//        .when()
//            .get("https://api.example.com/protected")
//        .then()
//            .statusCode(200)
//            .body("data", notNullValue());
//
//        // Step 3: Test invalid token
//        given()
//            .header("Authorization", "Bearer " + "invalid_token")
//        .when()
//            .get("https://api.example.com/protected")
//        .then()
//            .statusCode(401);
//    }
//}
//


//Coockies - in postman

//Get all cookies
//var cookies = pm.cookies.toObject();
//console.log(cookies);
//
////Validate specific cookie exists
//pm.test("Session cookie exists", function () {
// pm.expect(pm.cookies.has('session_id')).to.be.true;
//});
//
////Validate cookie value
//pm.test("Session cookie value is correct", function () {
// pm.expect(pm.cookies.get('session_id')).to.eql('expected_value');
//});


//pm.environment.set("session_id", pm.cookies.get('session_id'));

//In Restassured
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//
//import static io.restassured.RestAssured.given;
//import static org.junit.Assert.assertFalse;
//
//public class CookieValidationTest {
//
//    public static void main(String[] args) {
//
//        // Base URL
//        RestAssured.baseURI = "https://api.example.com";
//
//        // --- Step 1: First login ---
//        Response firstResponse = given()
//            .header("Content-Type", "application/json")
//            .body("{\"username\":\"user\",\"password\":\"pass\"}")
//        .when()
//            .post("/login")
//        .then()
//            .statusCode(200)
//            .extract().response();
//
//        // Extract first session cookie
//        String firstSessionId = firstResponse.getCookie("session_id");
//        System.out.println("First Session ID: " + firstSessionId);
//
//        // Validate cookie exists and is not empty
//        assertFalse("First session_id should not be empty", firstSessionId == null || firstSessionId.isEmpty());
//
//        // --- Step 2: Second login ---
//        Response secondResponse = given()
//            .header("Content-Type", "application/json")
//            .body("{\"username\":\"user\",\"password\":\"pass\"}")
//        .when()
//            .post("/login")
//        .then()
//            .statusCode(200)
//            .extract().response();
//
//        // Extract second session cookie
//        String secondSessionId = secondResponse.getCookie("session_id");
//        System.out.println("Second Session ID: " + secondSessionId);
//
//        // Validate cookie exists and is not empty
//        assertFalse("Second session_id should not be empty", secondSessionId == null || secondSessionId.isEmpty());
//
//        // --- Step 3: Validate cookies are unique ---
//        assertFalse("Session IDs should not be the same", firstSessionId.equals(secondSessionId));
//
//        // --- Step 4: Optional: validate invalid cookie does not exist ---
//        assertFalse("Invalid cookie should not exist", firstResponse.getCookies().containsKey("invalid_cookie"));
//        assertFalse("Invalid cookie should not exist", secondResponse.getCookies().containsKey("invalid_cookie"));
//
//        System.out.println("Cookie validation completed successfully!");
//    }
//}






