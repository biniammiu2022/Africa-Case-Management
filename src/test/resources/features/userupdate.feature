Feature: Update a user
  Scenario: Successfully update an existing user
    Given a user exists with the user update details are: firstName "firstName", lastName "lastName", email "email", username "username", and password "password"
    And the user update details are:
      | field     | value            |
      | firstName | John             |
      | lastName  | Doe              |
      | email     | john.doe@test.com|
      | username  | johndoe          |
      | password  | securepassword   |
    When a request is made to PUT /updateUser with updated details
   # Then the response status should be 200 OK
    And the user's details should be updated in the system
