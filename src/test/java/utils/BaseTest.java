package utils;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseTest {
	
	 // Set up Global Auth
	@BeforeClass
	public void setupGlobalAuth() {
	    RestAssured.baseURI = "https://userserviceapp-f5a54828541b.herokuapp.com";
	    RestAssured.authentication = RestAssured.basic("Numpy@gmail.com", "userapi@2025");
	}
}
