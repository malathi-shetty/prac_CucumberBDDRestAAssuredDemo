package payLoad;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import io.github.bonigarcia.wdm.WebDriverManager;
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

public class PayLoadUsingMap_1 {
	 private RequestSpecification requestSpecification;
	    private ValidatableResponse validatableResponse;
	    private WebDriver driver;
	    private final String baseUrl = "https://restful-booker.herokuapp.com/booking";

	    @BeforeClass
	    public void setUp() {
	        // Initialize Selenium WebDriver
	        WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	    }

	    @Test
	    public void testPOSTReq() {
	        // Use Selenium to interact with a webpage
	        driver.get("https://restful-booker.herokuapp.com/"); // Replace with a valid URL
	        WebElement element = driver.findElement(By.tagName("h1"));
	        System.out.println("Page Header: " + element.getText());

	        // Construct the request body using Map and LinkedHashMap
	        Map<String, Object> jsonBodyUsingMap = new LinkedHashMap<>();
	        Faker faker = new Faker(); // Faker is used to generate random fake data

	        jsonBodyUsingMap.put("firstname", faker.name().firstName());
	        jsonBodyUsingMap.put("lastname", faker.name().lastName());
	        jsonBodyUsingMap.put("totalprice", faker.random().nextInt(1000));
	        jsonBodyUsingMap.put("depositpaid", faker.random().nextBoolean());

	        Map<String, Object> bookingDatesMap = new HashMap<>();
	        bookingDatesMap.put("checkin", "2018-01-01");
	        bookingDatesMap.put("checkout", "2019-01-01");

	        jsonBodyUsingMap.put("bookingdates", bookingDatesMap);
	        jsonBodyUsingMap.put("additionalneeds", "Breakfast");

	        // Convert Map to JSONObject and pretty print JSON payload
	        JSONObject jsonObject = new JSONObject(jsonBodyUsingMap);
	        String prettyJsonString = jsonObject.toString(4); // 4 spaces for indentation
	        System.out.println("jsonBodyUsingMap: --->\n" + prettyJsonString);

	        // Setup the request specification
	        requestSpecification = RestAssured.given()
	            .baseUri(baseUrl)
	            .contentType(ContentType.JSON)
	            .body(jsonBodyUsingMap);

	        // Send POST request and get the response
	        Response response = requestSpecification.post();

	        // Extract booking ID from the response
	        Integer bookingId = response.then().extract().path("bookingid");
System.out.println("bookingId: "+ bookingId);
	        // Validate response
	        Assert.assertEquals(response.getStatusCode(), 200);
	        Assert.assertNotNull(bookingId, "Booking ID should not be null");

	        // Pretty-print HTTP response headers
	     //   System.out.println("HTTP Response Headers:");
	    /*    response.getHeaders().forEach(header ->
	            System.out.println(header.getName() + ": " + header.getValue())
	        );
*/
	        // Pretty-print headers with JSON values
	        System.out.println("\nPretty Printed JSON Headers:");
	        printPrettyJsonHeaders(response);

	      //  System.out.println("****2***");
	        // Validate and log the response
	        validatableResponse = response.then().log().all();
	        System.out.println(" ****3***** ");
	     System.out.println(  validatableResponse.statusCode(200));
	       
	       System.out.println("------");
	        System.out.println("Your Booking Id is -> " + bookingId);

	        System.out.println("****4***** ");
	        // Pretty-print response body
	        System.out.println("\nPretty Printed Response Body:");
	        printPrettyResponse(response);
	    }

	   
	   private void printPrettyResponse(Response response) {
	        // Convert response body to a string and pretty-print JSON
		   System.out.println("****5***** ");
	        String responseBody = response.getBody().asString();
	        JSONObject jsonObject = new JSONObject(responseBody);
	        String prettyJsonString = jsonObject.toString(4); // The parameter is the number of spaces for indentation
	        System.out.println(prettyJsonString);
	    }

	   
	    private void printPrettyJsonHeaders(Response response) {
	    	System.out.println("****6***** ");
	        response.getHeaders().forEach(header -> {
	            try {
	                JSONObject jsonObject = new JSONObject(header.getValue());
	                String prettyJsonString = jsonObject.toString(4); // Pretty print JSON header value
	                System.out.println(header.getName() + ":\n" + prettyJsonString);
	            } catch (Exception e) {
	                // If the header value is not JSON, just print it normally
	                System.out.println(header.getName() + ": " + header.getValue());
	            }
	            
	            System.out.println(" ");
	            System.out.println("****7***** ");
	        });
	    }

	    @AfterClass
	    public void tearDown() {
	        // Close the browser
	        if (driver != null) {
	            driver.quit();
	        }
	    }

}
