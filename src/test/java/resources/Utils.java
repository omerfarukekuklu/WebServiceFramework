package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {
    private static RequestSpecification requestSpecification;
    public RequestSpecification requestSpecification(){
        if(requestSpecification == null){
            PrintStream log= null;
            try{
                log = new PrintStream(new FileOutputStream("logs/logging.txt"));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            requestSpecification =new RequestSpecBuilder().setBaseUri(getGlobalValues("baseUrl")).addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }
        return requestSpecification;
    }

    public static String getGlobalValues (String key){
        Properties prop = new Properties();
        String value = null;
        try{
            FileInputStream inputStream = new FileInputStream("C:\\Users\\Ömer Faruk\\Desktop\\Rest Assured Eğitimi\\Framework Development\\WebServiceFramework\\src\\test\\java\\resources\\global.properties");
            prop.load(inputStream);
            value = prop.getProperty("baseUrl");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return  value;
    }

    public String getJsonPath(Response response, String key){
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }
}
