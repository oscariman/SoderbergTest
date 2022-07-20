Feature: GET Requests

  Background:
    Given system is up and running

  Scenario: Get a list of users
    When we make a request to get the list of users for the result page 2
    Then we check we have a valid response and the list of users
    When get the user with id 11
    Then check user field values matching the expected

  Scenario: Get a user
    When we make a request to get a user with user id 11
    Then check user field values matching the expected

  Scenario Outline: Check user is the same among responses from 2 different endpoints
    When we make a request to get the list of users for the result page 2
    And get the user with id <id>
    Then check user field values matching the expected
    When we make a request to get a user with user id <id>
    Then check user field values are the same among the 2 responses

    Examples:
      | id |
      | 11 |