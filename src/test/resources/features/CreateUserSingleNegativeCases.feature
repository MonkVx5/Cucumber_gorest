Feature: Create user single negative cases

  Scenario: Create a user without a name
    Given a new user with name "", email "noname.user.timestamp@example.com", gender "female", and status "active"
    When the user is created
    Then the user creation should fail with status 422
    And the user creation should fail with message "can't be blank"

  Scenario: Create a user without an email
    Given a new user with name "No Email User", email "", gender "male", and status "active"
    When the user is created
    Then the user creation should fail with status 422
    And the user creation should fail with message "can't be blank"

  Scenario: Create a user without a gender
    Given a new user with name "No Gender User", email "nogender.user.timestamp@example.com", gender "", and status "active"
    When the user is created
    Then the user creation should fail with status 422
    And the user creation should fail with message "can't be blank, can be male of female"

  Scenario: Create a user without a status
    Given a new user with name "No Status User", email "nostatus.user.timestamp@example.com", gender "female", and status ""
    When the user is created
    Then the user creation should fail with status 422
    And the user creation should fail with message "can't be blank"

  Scenario: Create a user without authorization token
    Given a new user with name "Unauthorized User", email "unauthorized.user.timestamp@example.com", gender "male", and status "active"
    When the user is created without token
    Then the user creation should fail with status 401