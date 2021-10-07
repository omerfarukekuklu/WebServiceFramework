package step_definitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		//execute this code only when place id is null
		//write a code that will give you place id
		
		StepDefinitions stepDefinition = new StepDefinitions();
		if(StepDefinitions.place_id==null)
		{
			stepDefinition.add_place_payload_with("Shetty", "French", "Asia");
			stepDefinition.user_calls_with_http_request("AddPlaceAPI", "POST");
			stepDefinition.verify_created_maps_to_using("Shetty", "GetPlaceAPI");
		}
	}
}
