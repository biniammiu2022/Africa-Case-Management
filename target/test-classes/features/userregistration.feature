Feature: User Registration

  Scenario: Successful user registration
    Given a user with firstname "Selalia", lastname "Gomida", email "korida@acm.com", username "testuser1", and password "password"
    When the user registers
    Then the response status should be 201
    And the response should contain the user's id and username










