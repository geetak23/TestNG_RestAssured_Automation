package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class BaseTest {

	// Setting up Global Auth
	@BeforeClass
	public void setupGlobalAuth() {
		RestAssured.baseURI = "https://userserviceapp-f5a54828541b.herokuapp.com";
		RestAssured.authentication = RestAssured.basic("Numpy@gmail.com", "userapi@2025");
	}

	public static String generateUniqueString() {
		//Only Alphabets for First Name
		String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder randomString = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 5; i++) {
			int index = random.nextInt(alphabets.length());
			randomString.append(alphabets.charAt(index));
		}
		return randomString.toString();
	}
	public static String generateRandomNum()
	{
		String digits = "0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(digits.length());
            randomString.append(digits.charAt(index));
        }

        return randomString.toString();
	}

	public static String generateUniqueEmail() {
		String randomString = generateUniqueString();
		return "user" + randomString + "@example.com";
	}
	
	public static String username="";
	public static String password="";
	
	
	 public void getUserCredentials() throws IOException {
        // File path to the properties file
        String filePath = "src/test/resources/testdata/userconfig.properties";

        // Load the properties
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Print all properties
        properties.forEach((key, value) -> System.out.println(key + ": " + value));

        // Get username and password
        username = "Numpy@gmail.com";//properties.getProperty("username");
        password = "userapi@2025";//properties.getProperty("password");
        System.out.println(username);
	}
}
