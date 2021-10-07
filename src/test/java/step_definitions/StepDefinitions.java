package step_definitions;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinitions extends Utils {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    Response response;
    TestDataBuild data = new TestDataBuild();

    @Given("Add Place Payload")
    public void add_place_payload() {
        requestSpecification=given().spec(requestSpecification())
                .body(data.getAddPlacePayload());
    }

    @When("user calls {string} with Post http request")
    public void user_calls_with_post_http_request(String string) {
        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
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
