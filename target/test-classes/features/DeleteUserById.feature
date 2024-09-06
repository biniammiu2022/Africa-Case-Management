Feature: Delete User by ID
  As an administrator
  I want to delete a user by ID
  So that the user is marked as inactive
#  first scenario
  Scenario: Successfully delete an existing user by ID
    Given a user exists with ID 1 and is active
    When I delete the user with ID 1
    Then the user with ID 1 should be marked as inactive
# second scenario
  Scenario: Attempting to delete a non-existing user
    When I attempt to delete a non-existing user with ID 456
    Then I should receive a "User not found with id: 456" error
