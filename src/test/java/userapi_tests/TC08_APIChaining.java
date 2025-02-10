package userapi_tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.BaseTest;

public class TC08_APIChaining extends BaseTest {

	//End to End API testing using static data
	
	private static int userId;
	private static String userFirstName = "";
	private static String userLastName = "";
	private static String email="";
	private static String contactNum="";
	
	public void generateUniqueValues()
	{
		userFirstName = generateUniqueString();
		userLastName = "NumpyNinja";
		email = generateUniqueEmail();
		contactNum = generateRandomNum();
	}

	@Test
	public void apiChainingTestWithMaps() {
	
		generateUniqueValues();
		// 1. POST Request: Create a new user for user ID
		Map<String, Object> userAddress = new HashMap<>();
		userAddress.put("plotNumber", "Lj-53");
		userAddress.put("street", "ProsaccoPrairie");
		userAddress.put("state", "South Zechariah");
		userAddress.put("country", "Congo");
		userAddress.put("zipCode", "739");

		Map<String, Object> user = new HashMap<>();
		user.put("user_first_name", userFirstName);
		user.put("user_last_name", userLastName);
		user.put("user_contact_number",contactNum );
		user.put("user_email_id", email);
		user.put("userAddress", userAddress);

		// 1. POST request to create a new user
		Response postResponse = RestAssured.given().auth().basic("Numpy@gmail.com", "userapi@2025")
				.contentType("application/json").body(user).when().post("/uap/createusers").then().extract().response();

		// Extract the userId from the POST response
		userId = postResponse.jsonPath().getInt("user_id");

		System.out.println("User created with ID: " + userId);

		// 2. GET Request by ID
		Response getByIdResponse = RestAssured.given().auth().basic("Numpy@gmail.com", "userapi@2025")
				.contentType("application/json").when().get("/uap/user/" + userId).then().extract().response();

		// Assertion
		Assert.assertEquals(getByIdResponse.statusCode(), 200, "User not found by ID");
		System.out.println("User retrieved by ID: " + getByIdResponse.getBody().asString());

		// 3. GET Request by First Name
		Response getByFirstNameResponse = RestAssured.given().auth().basic("Numpy@gmail.com", "userapi@2025")
				.contentType("application/json").when().get("/uap/users/username/" + userFirstName).then().extract()
				.response();

		// Assertion
		Assert.assertEquals(getByFirstNameResponse.statusCode(), 200, "User not found by First Name");
		System.out.println("User retrieved by First Name: " + getByFirstNameResponse.getBody().asString());

		generateUniqueValues();
		
		// 4. PUT request update the user details
		Map<String, Object> updatedUserAddress = new HashMap<>();
		updatedUserAddress.put("plotNumber", "Lj-53");
		updatedUserAddress.put("street", "ProsaccoPrairie");
		updatedUserAddress.put("state", "South Zechariah");
		updatedUserAddress.put("country", "Congo");
		updatedUserAddress.put("zipCode", "999");

		Map<String, Object> updatedUser = new HashMap<>();
		updatedUser.put("user_first_name", userFirstName);
		updatedUser.put("user_last_name", userLastName);
		updatedUser.put("user_contact_number",contactNum);
		updatedUser.put("user_email_id",email);
		updatedUser.put("userAddress", updatedUserAddress);
		Response putResponse = RestAssured.given().auth().basic("Numpy@gmail.com", "userapi@2025")
				.contentType("application/json").body(updatedUser).when().put("/uap/updateuser/" + userId).then()
				.extract().response();

		// Assertion
		Assert.assertEquals(putResponse.statusCode(), 200, "Failed to update user");
		System.out.println("User updated: " + putResponse.getBody().asString());

		// 5. DELETE request by user ID
		Response deleteByIdResponse = RestAssured.given().auth().basic("Numpy@gmail.com", "userapi@2025")
				.contentType("application/json").when().delete("/uap/deleteuser/" + userId).then().extract().response();

		// Assertion
		Assert.assertEquals(deleteByIdResponse.statusCode(), 200, "Failed to delete user by ID");
		System.out.println("User deleted by ID: " + deleteByIdResponse.getBody().asString());

		// 1. POST Request: Create a new user to test delete by user First name
		 generateUniqueValues();
		
		Map<String, Object> uAddressToDeleteByFN = new HashMap<>();
		uAddressToDeleteByFN.put("plotNumber", "Lj-53");
		uAddressToDeleteByFN.put("street", "ProsaccoPrairie");
		uAddressToDeleteByFN.put("state", "South Zechariah");
		uAddressToDeleteByFN.put("country", "Congo");
		uAddressToDeleteByFN.put("zipCode", "739");
		
		
		Map<String, Object> userToDeleteByFN = new HashMap<>();
		userToDeleteByFN.put("user_first_name", userFirstName);
		userToDeleteByFN.put("user_last_name", userLastName);
		userToDeleteByFN.put("user_contact_number",contactNum);
		userToDeleteByFN.put("user_email_id", email);
		userToDeleteByFN.put("userAddress", uAddressToDeleteByFN);		
	
		Response postResponsedel = RestAssured.given().auth().basic("Numpy@gmail.com", "userapi@2025")
				.contentType("application/json").body(userToDeleteByFN).when().post("/uap/createusers").then().extract().response();
				
		// 6. DELETE request by user First Name
		Response deleteByFirstNameResponse = RestAssured.given().auth().basic("Numpy@gmail.com", "userapi@2025")
				.contentType("application/json").when().delete("/uap/deleteuser/username/" + userFirstName).then()
				.extract().response();

			
		// Assertion
		Assert.assertEquals(deleteByFirstNameResponse.statusCode(), 200, "Failed to delete user by First Name");
		System.out.println("User deleted by First Name: " + deleteByFirstNameResponse.getBody().asString());
		
	}
}
