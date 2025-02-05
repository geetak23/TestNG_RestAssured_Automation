package userapi_tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseTest;

public class TC04_GetAllUsersRequest extends BaseTest {

	// Test GET All Users Details with Authentication
	@Test
	public void getAllUsersDetailsWithBasicAuth() {
		Response response = RestAssured
				.given()
				.auth().basic("Numpy@gmail.com", "userapi@2025") // Basic Auth directly
				.contentType(ContentType.JSON)
				.when().get("/uap/users").then().statusCode(200) // Validating status code
				.extract().response();

		System.out.println("Response Body: " + response.getBody().asString());

		// Assertions
		Assert.assertTrue(response.getBody().asString().contains("user_id"), "Response does not contain 'user_id'");
	}
}
