Feature: Get All Users

  Scenario: Successfully get all users
    Given users exist in the system
    When a request is made to GET /users
    Then the response should contain a list of users

  Scenario: No users exist
    Given no users exist in the system
    When a request is made to GET /users
    Then the response should contain an empty list
