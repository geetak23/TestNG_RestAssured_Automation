package userapi_tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.JsonDataProvider;
import utils.BaseTest;

public class TC09_DataDrivenAPITesting_Json {
	
	private static int userJsonId;
	
	@Test(dataProvider = "getUserJsonDataProvider", dataProviderClass = JsonDataProvider.class)
	public void testUserAPI(Map<String, Object> userData) throws IOException {	
		
		// Extract user details from the map
		Map<String, Object> userAddress = (Map<String, Object>) userData.get("userAddress");

		String firstName = (String) userData.get("user_first_name");
		String lastName = (String) userData.get("user_last_name");
		String contactNumber = (String) userData.get("user_contact_number");
		String emailId = (String) userData.get("user_email_id");
		boolean basicAuth = Boolean.parseBoolean((String) userData.get("BasicAuth"));
		int expectedStatusCode = (int) userData.get("StatusCode");
		
		BaseTest objbasetest = new BaseTest();
		objbasetest.getUserCredentials();
		if (basicAuth == true) {			
			objbasetest.setupGlobalAuth();
		}

		// Create user map collection
		Map<String, Object> userRequestBody = new HashMap<>();
		userRequestBody.put("user_first_name", firstName);
		userRequestBody.put("user_last_name", lastName);
		userRequestBody.put("user_contact_number", contactNumber);
		userRequestBody.put("user_email_id", emailId);
		userRequestBody.put("userAddress", userAddress);

		// Send POST API request
		Response response = RestAssured.given().auth().basic(objbasetest.username, objbasetest.password)
				.contentType(ContentType.JSON).body(userRequestBody).when().post("/uap/createusers").then().extract()
				.response();

		// ASSERTIONS

		// Validating Status Code
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Unexpected Status Code");
		System.out.println(response.getStatusCode());

		// Validating Response Header
		String contentType = response.getHeader("Content-Type");
		Assert.assertNotNull(contentType, "Content-Type header is missing");
		Assert.assertTrue(contentType.contains("application/json"), "Content-Type is not application/json");
		
		if (expectedStatusCode == 201) {
			
			int expectedgetuserIdStatuscode=(int) userData.get("GetUserByIdSC");
			int expectedgetuserFirstnameStatuscode=(int) userData.get("GetUserByFirstNameSC");
			int expectedgetDeluserbyIdSC=(int) userData.get("DeleteUserByIdSC");
			
			
			// Validating Response Data Type
			Assert.assertTrue(response.getBody().asString().contains("user_first_name"),
					"Response does not contain user_first_name");

			// Validate JSON structure or fields
			Map<String, Object> responseBody = response.jsonPath().getMap("$");
			Assert.assertTrue(responseBody.containsKey("userAddress"), "Response is missing 'userAddress'");
			Assert.assertTrue(responseBody.containsKey("user_first_name"), "Response is missing 'user_first_name'");
			Assert.assertTrue(response.getBody().asString().contains("user_id"));
			
			userJsonId = response.jsonPath().getInt("user_id");

			// Validating Response Data
			if (responseBody.containsKey("user_first_name")) {
				Assert.assertEquals(responseBody.get("user_first_name"), firstName,
						"Incorrect user_first_name in response");
			}
			
			//**** GET request for get user by Id***********
			
			Response getByIdResponse = RestAssured.given().auth().basic(objbasetest.username, objbasetest.password)
					.contentType("application/json").when().get("/uap/user/" + userJsonId).then().extract().response();

			// Assertion
			Assert.assertEquals(getByIdResponse.statusCode(), expectedgetuserIdStatuscode, "User not found by ID");
			System.out.println("User retrieved by ID: " + getByIdResponse.getBody().asString());

			//**** GET Request by First Name***********//
			Response getByFirstNameResponse = RestAssured.given().auth().basic(objbasetest.username, objbasetest.password)
					.contentType("application/json").when().get("/uap/users/username/" + firstName).then().extract()
					.response();

			// Assertion
			Assert.assertEquals(getByFirstNameResponse.statusCode(), expectedgetuserFirstnameStatuscode, "User not found by First Name");
			System.out.println("User retrieved by First Name: " + getByFirstNameResponse.getBody().asString());
			
			//**** DELETE request by user ID***********//
			Response deleteByIdResponse = RestAssured.given().auth().basic(objbasetest.username, objbasetest.password)
					.contentType("application/json").when().delete("/uap/deleteuser/" + userJsonId).then().extract().response();

			// Assertion
			Assert.assertEquals(deleteByIdResponse.statusCode(), expectedgetDeluserbyIdSC, "Failed to delete user by ID");
			System.out.println("User deleted by ID: " + deleteByIdResponse.getBody().asString());			
		}
	}
}
