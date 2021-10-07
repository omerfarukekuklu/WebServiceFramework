package step_definitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo_classes.AddPlace;
import pojo_classes.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class StepDefinitions {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    Response response;

    @Given("Add Place Payload")
    public void add_place_payload() {
        RestAssured.baseURI="https://rahulshettyacademy.com";

        AddPlace p =new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("https://rahulshettyacademy.com");
        p.setName("Frontline house");
        List<String> myList =new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");

        p.setTypes(myList);
        Location l =new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);

        RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        responseSpecification=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        requestSpecification=given().spec(req)
                .body(p);
    }

    @When("user calls {string} with Post http request")
    public void user_calls_with_post_http_request(String string) {
        response =requestSpecification.when().post("/maps/api/place/add/json").
                then().spec(responseSpecification).extract().response();
    }

    @Then("the API call got success with status code {int}")
    public void the_apı_call_got_success_with_status_code(Integer int1) {
        assertEquals(response.getStatusCode(), 200);
    }

    @And("{string} in response body is {string}")
    public void in_response_body_is(String key, String expectedValue) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        assertEquals(js.get(key).toString(), expectedValue);
    }

}