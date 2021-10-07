package step_definitions;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinitions extends Utils {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    Response response;
    TestDataBuild data = new TestDataBuild();

    @Given("Add Place Payload with {string}  {string}  {string}")
    public void add_place_payload_with(String name, String language, String address) {
        requestSpecification=given().spec(requestSpecification())
                .body(data.getAddPlacePayload(name, language, address));
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String httpMethod) {
        APIResources resourceAPI = APIResources.valueOf(resource);
        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if(httpMethod.equalsIgnoreCase("GET")){
            response =requestSpecification.when().get(resourceAPI.getResource());
        }
        else if(httpMethod.equalsIgnoreCase("POST")){
            response =requestSpecification.when().post(resourceAPI.getResource());
        }
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
