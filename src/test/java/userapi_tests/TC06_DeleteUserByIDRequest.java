package userapi_tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseTest;

public class TC06_DeleteUserByIDRequest extends BaseTest{
	
	 @Test
	    public void deleteUserById() {
		 
	        int userId = 18180;
	        Response response = RestAssured
	                .given()
	                    .auth().basic("Numpy@gmail.com", "userapi@2025") // Basic Authentication
	                    .contentType("application/json") 
	                .when()
	                    .delete("/uap/deleteuser/" + userId) 
	                .then()
	                    .extract()
	                    .response();

	        System.out.println("Response Status Code: " + response.getStatusCode());
	        System.out.println("Response Body: " + response.getBody().asString());

	        // Assertions
	        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but got " + response.getStatusCode());
	        Assert.assertTrue(response.getBody().asString().contains("User is deleted successfully"),
	                "Response does not contain success message");
	    }
}
