package stepDefinitions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class DemoRestAPITest {
	
	//	private Scenario scenario;
		private Response response;
		//private final String Base_URL = "https://reqres.in/";
		private final String Base_URL = "https://api.restful-api.dev/objects";

	/*	@Before
	    public void before(Scenario scenarioVal) {
	        this.scenario = scenarioVal;
	    }
	*/	
		@Given("Get Call to {string}")
		public void get_call_to(String url) throws URISyntaxException {
			RestAssured.baseURI = Base_URL;
	        RequestSpecification req = RestAssured.given();
	        response = req.when().get(new URI(url));
	        
	        // Pretty print the response body
	        printPrettyResponse(response);
	     //OR   // Print the response body to understand its structure
	     //   System.out.println("Response Body: " + response.getBody().asString());
	        
		}

		private void printPrettyResponse(Response response) {
			 // Convert response body to a string
            String responseBody = response.getBody().asString();
            
            JSONObject jsonObject = new JSONObject(responseBody);

            // Convert JSONObject to pretty-printed string
            String prettyJsonString = jsonObject.toString(4); // The parameter is the number of spaces for indentation

            // Print the pretty JSON string
            System.out.println(prettyJsonString);
        
	}

		@Then("Response Code {string} is validated")
		public void response_code_is_validated(String expectedStatusCode) {
			  int actualStatusCode = response.getStatusCode();
			  Assert.assertEquals(actualStatusCode, Integer.parseInt(expectedStatusCode));
		}

		// Method to verify array size
		@Then("Response is array total {string}")
		public void response_is_array_total(String expectedTotal) {
		    response.then().statusCode(200);

		    // Extract data as a Map
		    Map<String, Object> dataMap = response.jsonPath().getMap("data");
		    System.out.println("Data Map: " + dataMap);

		    // Extract the list from the map
		    int actualSize = dataMap.size();
		    Assert.assertEquals(actualSize, Integer.parseInt(expectedTotal));
		}



	}


