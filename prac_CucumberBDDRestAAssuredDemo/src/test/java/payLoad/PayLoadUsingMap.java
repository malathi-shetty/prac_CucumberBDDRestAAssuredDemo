package payLoad;

import io.restassured.specification.RequestSpecification;
import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
public class PayLoadUsingMap {

	//RequestSpecification and Response are from RestAssured to define and handle HTTP requests and responses.
	  RequestSpecification requestSpecification;
	    ValidatableResponse validatableResponse;
	    WebDriver driver;
	    String baseUrl = "https://restful-booker.herokuapp.com/booking";

	    @BeforeClass
	    public void setUp() {
	        // Initialize Selenium WebDriver
	       WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	    }
	    

	    @Test
	    public void testPOSTReq(){
	        //{
	        //            "firstname" : "Jim",
	        //                "lastname" : "Brown",
	        //                "totalprice" : 111,
	        //                "depositpaid" : true,
	        //                "bookingdates" : {
	        //            "checkin" : "2018-01-01",
	        //                    "checkout" : "2019-01-01"
	        //        },
	        //            "additionalneeds" : "Breakfast"
	        //        }

	        // Hashmap
	        // JSON paYLOAD -> Map - > Contain further maps
	    	
	    	  // Use Selenium to interact with a webpage
	        driver.get("https://restful-booker.herokuapp.com/"); // Replace with a valid URL
	        WebElement element = driver.findElement(By.tagName("h1"));
	        System.out.println("Page Header: " + element.getText());
	    

	    	//Map and LinkedHashMap are used to construct the request body.
	    	//A LinkedHashMap is used to maintain the order of the keys.
	        Map<String, Object> jsonBodyUsingMap = new LinkedHashMap<>();
	        Faker faker = new Faker(); //Faker is used to generate random  fake data.
	        String name = faker.name().fullName();

	        //Random data is added for firstname, lastname, totalprice, and depositpaid
	        jsonBodyUsingMap.put("firstname", faker.name().firstName());
	        jsonBodyUsingMap.put("lastname", faker.name().lastName());
	        jsonBodyUsingMap.put("totalprice", faker.random().nextInt(1000));
	        jsonBodyUsingMap.put("depositpaid", faker.random().nextBoolean());

	        //A nested map (bookingDatesMap) is created to include checkin and checkout dates
	        Map<String,Object> bookingDatesMap = new HashMap<>();
	        bookingDatesMap.put("checkin","2018-01-01");
	        bookingDatesMap.put("checkout","2019-01-01");

	      //The nested map is added to the main map, and an additional key-value pair for additionalneeds is added.
	        jsonBodyUsingMap.put("bookingdates",bookingDatesMap);
	        jsonBodyUsingMap.put("additionalneeds", "Breakfast");

	   //     System.out.println("jsonBodyUsingMap: ---> ");
	        //The constructed JSON payload is printed to the console.
	      //  System.out.println(jsonBodyUsingMap);
	     //   printPrettyResponse(jsonBodyUsingMap);
	        // "" - Map -> JSON
	        
	        
	      //  Convert Map to JSONObject
	        JSONObject jsonObject = new JSONObject(jsonBodyUsingMap);

	        // Pretty print JSON
	        String prettyJsonString = jsonObject.toString(4); // 4 spaces for indentation
	        System.out.println("jsonBodyUsingMap: --->\n" + prettyJsonString);

	        System.out.println(" ");
	        System.out.println("requestSpecification Data: ---> ");
	        // Setup the request specification
	    	requestSpecification = RestAssured.given();
	    	requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
	    	requestSpecification.basePath("booking");
	    	requestSpecification.contentType(ContentType.JSON);
	    	requestSpecification.body(jsonBodyUsingMap).log().all();
	    	
	    	System.out.println(" ");
	        System.out.println("POST Request Data: ---> ");
	    	// Send the POST request
	  //  	Response response = requestSpecification.when().post();
	    	//OR
	    	 // Send POST request using RestAssured
	        Response response = RestAssured.given()
	                .baseUri(baseUrl)
	                .contentType(ContentType.JSON)
	                .body(jsonBodyUsingMap)
	                .post();
	    	
	        
	   
	    	// Extract booking ID from the response
	    	Integer bookingId = response.then().extract().path("bookingid");
	    	
	        // Validate response
	        Assert.assertEquals(response.getStatusCode(), 200);
	        Assert.assertNotNull(bookingId, "Booking ID should not be null");
	        
	        // Pretty-print the HTTP response headers
	  /*      System.out.println("HTTP Response Headers:");
	        response.getHeaders().forEach(header -> 
	            System.out.println(header.getName() + ": " + header.getValue())
	        );
	  */  	
	        System.out.println(" ");
	        System.out.println("validatableResponse Data: ---> ");
	    	// Validate and log the response to perform validation
	    	validatableResponse = response.then().log().all();
	    	validatableResponse.statusCode(200);
	    	System.out.println("Your Booking Id is -> " + bookingId);

	        printPrettyResponse(response);

	    }
	   
	/*    private void printPrettyResponse(Map<String, Object> map) {
	    	
	    	
	 
			// Convert the Map to a JSONObject
	       JSONObject jsonObject = new JSONObject(map);
	     
	        // Convert JSONObject to a pretty-printed JSON string
	        String prettyJsonString = jsonObject.toString(4); // 4 is the number of spaces for indentation
	        
	        // Print the pretty-printed JSON
	        System.out.println("Pretty Printed JSON:\n" + prettyJsonString);
      
	}
	*/	

		private void printPrettyResponse(Response response) {
			 // Convert response body to a string
           String responseBody = response.getBody().asString();
           
           JSONObject jsonObject = new JSONObject(responseBody);

           // Convert JSONObject to pretty-printed string
           String prettyJsonString = jsonObject.toString(4); // The parameter is the number of spaces for indentation

           // Print the pretty JSON string
           System.out.println(prettyJsonString);
       
	}
	    
	    @AfterClass
	    public void tearDown() {
	        // Close the browser
	        if (driver != null) {
	            driver.quit();
	        }

	    }}
