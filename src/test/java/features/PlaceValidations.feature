Feature: Place API Tests

  Scenario: Verify if place is successfully added using AppPlaceAPI
    Given Add Place Payload
    When user calls "AddPlaceAPI" with Post http request
    Then the API call got success with status code 200
    And  "status" in response body is "OK"
    And  "scope" in response body is "APP"