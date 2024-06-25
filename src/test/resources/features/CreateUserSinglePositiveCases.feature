Feature: Create user single cases
  Scenario: Create a female user
    Given a new user with name "Jane Doe", email "jane.doe.timestamp@example.com", gender "female", and status "active"
    When the user is created
    Then the user creation should be successful
    And the user should exist in the system

  Scenario: Create a male user
    Given a new user with name "John Doe", email "john.doe.timestamp@example.com", gender "male", and status "active"
    When the user is created
    Then the user creation should be successful
    And the user should exist in the system

  Scenario: Create an active user
    Given a new user with name "Active User", email "active.user.timestamp@example.com", gender "male", and status "active"
    When the user is created
    Then the user creation should be successful
    And the user should exist in the system

  Scenario: Create an inactive user
    Given a new user with name "Inactive User", email "inactive.user.timestamp@example.com", gender "female", and status "inactive"
    When the user is created
    Then the user creation should be successful
    And the user should exist in the system
