package userapi_tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseTest;

public class TC05_PutUpdateUsesrRequest extends BaseTest {
	// 18173

	@Test
	public void updateUserWithMap() {
		
		//
		Map<String, String> userAddress = new HashMap<>();
        userAddress.put("plotNumber", "Lj-53");
        userAddress.put("street", "ProsaccoPrairie");
        userAddress.put("state", "South Zechariah");
        userAddress.put("country", "Congo");
        userAddress.put("zipCode", "739");

    
        Map<String, Object> user = new HashMap<>();
        user.put("user_first_name", "qwer");
        user.put("user_last_name", "asdf");
        user.put("user_contact_number", "3884804164");
        user.put("user_email_id", "abc125@gmail.com");
        user.put("userAddress", userAddress);

		
		Response response = RestAssured.given().auth().basic("Numpy@gmail.com", "userapi@2025")
				.contentType(ContentType.JSON).body(user) 
				.when().put("/uap/updateuser/18194").then().extract().response();

	
		System.out.println("Response Body: " + response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), 200);		
	}
}
