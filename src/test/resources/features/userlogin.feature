Feature: User Login

  Scenario: Successful user login
    Given a registered user with username "testuser2" and password "password2"
    When the user logs in with username "testuser2" and password "password2"
    Then the response status should be 200
    And the response should contain the user's id and username



