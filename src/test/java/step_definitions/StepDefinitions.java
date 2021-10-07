package step_definitions;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.PayloadManager;
import resources.Utils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinitions extends Utils {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    Response response;
    PayloadManager payloadManager = new PayloadManager();
    static String place_id;

    @Given("Add Place Payload with {string}  {string}  {string}")
    public void add_place_payload_with(String name, String language, String address) {
        requestSpecification=given().spec(requestSpecification())
                .body(payloadManager.getAddPlacePayload(name, language, address));
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
        assertEquals(getJsonPath(response, key), expectedValue);
    }

    @Then("verify place_id created maps to {string} using {string}")
    public void verify_created_maps_to_using(String expedtedValue, String resourceName) {
        place_id = getJsonPath(response, "place_id");
        requestSpecification = given().spec(requestSpecification().queryParam("place_id", place_id));
        user_calls_with_http_request(resourceName, "GET");
        String actualValue = getJsonPath(response, "name");
        assertEquals(actualValue, expedtedValue);
    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() {
        requestSpecification = given().spec(requestSpecification()).body(payloadManager.getDeletePlacePayload(place_id));
    }

}
