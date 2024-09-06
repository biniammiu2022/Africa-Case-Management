Feature: Password Reset

  Scenario: Successfully reset password
    Given a user with username "johndoe" and password "oldpassword" exists
    When the user resets the password to "newpassword" with old password "oldpassword"
    Then the response status should be 200
    And the password for username "johndoe" should be "newpassword"


