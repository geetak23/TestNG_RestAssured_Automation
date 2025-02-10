package utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;

public class JsonDataProvider {

    @DataProvider(name = "getUserJsonDataProvider")
    public Object[][] getUserJsonDataProvider() {
        Object[][] userData = null;

        try {
        	
        	String jsonContent_UserData = FileUtils.readFileToString(new File(FileNameConstants.JSON_USER_DATA), "UTF-8");
            
            List<Map<String, Object>> jsonArray = JsonPath.read(jsonContent_UserData, "$");

            userData = new Object[jsonArray.size()][1];
            for (int i = 0; i < jsonArray.size(); i++) {
            	userData[i][0] = jsonArray.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userData;
    }
}