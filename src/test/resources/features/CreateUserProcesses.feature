Feature: Create user and Delete
  Scenario: Successfully create a user
    Given a new user with name "John Doe", email "john.doe22@example.com", gender "male", and status "active"
    When the user is created
    Then the user creation should be successful
    And the user should exist in the system

  Scenario: Fail to create a user with an existing email
    Given a new user with name "John Doe", email "john.doe22@example.com", gender "male", and status "active"
    When the user is created
    Then the user creation should fail with status 422
    And the user creation should fail with message "has already been taken"

  Scenario: Delete a user with an existing email
    Given get list of all users
    When find id of user with email "john.doe22@example.com"
    Then the user should deleted with status 204
