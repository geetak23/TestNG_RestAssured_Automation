package userapi_tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseTest;
public class TC03_GetDetailsByFirstNameRequest extends BaseTest{
	@Test
    public void getUserById() {
        
		// local variables
        String firstName = "FNameVzpQr"; // Static User Id 
        String endpoint = "/uap/users/username/" + firstName; // appending to endpoint

       
        Response response = RestAssured
                .given()
                    .auth().basic("Numpy@gmail.com", "userapi@2025") // Basic Auth
                    .contentType(ContentType.JSON)
                .when()
                    .get(endpoint) // GET request for user details by first name
                .then()
                    .statusCode(200) // Validating status code
                    .extract()
                    .response();
      
        System.out.println("Response Body: " + response.getBody().asString());

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but got " + response.getStatusCode());
        Assert.assertTrue(response.getBody().asString().contains("user_first_name"), firstName);
    }
}
