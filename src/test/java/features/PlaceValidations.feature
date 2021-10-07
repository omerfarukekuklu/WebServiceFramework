Feature: Place API Tests

  Scenario Outline: Verify if place is successfully added using AppPlaceAPI
    Given Add Place Payload with "<name>"  "<language>"  "<address>"
    When user calls "AddPlaceAPI" with Post http request
    Then the API call got success with status code 200
    And  "status" in response body is "OK"
    And  "scope" in response body is "APP"

  Examples:
    |name    |language  |address           |
    |A house |English   |World cross center|