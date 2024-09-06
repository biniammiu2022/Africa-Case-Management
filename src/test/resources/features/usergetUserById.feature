Feature: Get User by ID

  Scenario: Successfully get a user by ID
    Given a user exists with the ID
    When a request is made to GET /users by id
    Then the response should contain the user's details including ID, name, and email

