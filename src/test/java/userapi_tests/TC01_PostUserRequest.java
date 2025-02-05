package userapi_tests;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseTest;

public class TC01_PostUserRequest extends BaseTest{
	
	//Post Request to Create User
	@Test
	public void createUser() {
		
		 // Create address map// generated user id :18173
	    Map<String, String> userAddress = new HashMap<>();
	    userAddress.put("plotNumber", "pl-03");
	    userAddress.put("Street", "Some Place");
	    userAddress.put("state", "Texas");
	    userAddress.put("Country", "USA");
	    userAddress.put("zipCode", "75071");

	    // Create user map
	    Map<String, Object> user = new HashMap<>();
	    user.put("user_first_name", "Geetakqw");
	    user.put("user_last_name", "Kuch");
	    user.put("user_contact_number", "1486237984");
	    user.put("user_email_id", "geeta.Kuch@example.com");
	    user.put("userAddress", userAddress);

	    // Send POST request
	    Response response = RestAssured
	            .given()
	                .auth().basic("Numpy@gmail.com", "userapi@2025")
	                .contentType(ContentType.JSON)
	                .body(user) // Pass the map collection
	            .when()
	                .post("/uap/createusers")
	            .then()
	                .extract()
	                .response();

	    System.out.println("Response Body: " + response.getBody().asString());
	    Assert.assertEquals(response.getStatusCode(), 201);
	    Assert.assertTrue(response.getBody().asString().contains("user_id"));
	}
	@Test
	public void createUser2() {
		
		 // Create address map// generated user id :18173
	    Map<String, String> userAddress = new HashMap<>();
	    userAddress.put("plotNumber", "pl-03");
	    userAddress.put("Street", "Some Place");
	    userAddress.put("state", "Texas");
	    userAddress.put("Country", "USA");
	    userAddress.put("zipCode", "75071");

	    // Create user map
	    Map<String, Object> user = new HashMap<>();
	    user.put("user_first_name", "geetazaq");
	    user.put("user_last_name", "Kuchzaq");
	    user.put("user_contact_number", "1486237468");
	    user.put("user_email_id", "geeta.Kuchzaq@example.com");
	    user.put("userAddress", userAddress);

	    // Send POST request
	    Response response = RestAssured
	            .given()
	                .auth().basic("Numpy@gmail.com", "userapi@2025")
	                .contentType(ContentType.JSON)
	                .body(user) // Pass the map collection
	            .when()
	                .post("/uap/createusers")
	            .then()
	                .extract()
	                .response();

	    System.out.println("Response Body: " + response.getBody().asString());
	    Assert.assertEquals(response.getStatusCode(), 201);
	    Assert.assertTrue(response.getBody().asString().contains("user_id"));
	}



}
