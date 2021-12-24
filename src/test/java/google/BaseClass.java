package google;

import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import static google.TextConstants.H_RAPIDAPI_HOST;
import static google.TextConstants.H_RAPID_API_KEY;
import static google.Utils.*;

public class BaseClass {
    private static final Properties globalProperties = getPropValues();
    public static final String host = globalProperties.getProperty("RootUrl");

    @BeforeClass
    public void init() {
        RestAssured.baseURI = host;
        //Log response in case of failure (status codes 400+)
        RestAssured.filters(new ErrorLoggingFilter());
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        System.out.println("\n*****Test: " + method.getName() + " started*****\n");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        System.out.println("\n>>>>>Test: " + result.getName() + " finished<<<<<\n");
    }

    private static Properties getPropValues() {
        Properties properties = null;
        String rootProjectPath = new File("").getAbsolutePath();
        String propFilePath = "src/main/resources/properties/config.properties";
        Path path = Paths.get(rootProjectPath, propFilePath);

        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            properties = new Properties();
            properties.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return properties;
    }


    //If more headers added then better to use Builder pattern
    public static Response getGeneric(String endPoint)
    {
        return getGeneric(endPoint, getRapidApiHost(), getRapidApiKey(), null);
    }

    public static Response getGeneric(String endPoint, Map<String, Object> queryParams)
    {
        return getGeneric(endPoint, getRapidApiHost(), getRapidApiKey(), queryParams);
    }

    public static Response getGeneric(String endPoint, String hostHeader, String keyHeader, Map<String, Object> queryParams) {
        RequestSpecification request = RestAssured.given();
        if (queryParams != null)
            request.params(queryParams);
        if (hostHeader != null)
            request.header(H_RAPIDAPI_HOST, hostHeader);
        if (keyHeader != null)
            request.header(H_RAPID_API_KEY, keyHeader);
        return request.get(endPoint);
    }
}
