Feature: Resource upload and parsing feature
  Scenario: test checks
    Given url 'http://localhost:8088/resources/1'
    When method GET
    Then status 200