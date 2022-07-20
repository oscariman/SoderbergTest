Feature: POST Requests

  Background:
    Given system is up and running

  Scenario: Create user
    When create a new user with name "Peter" and job "Sales"
    Then check user has been properly created

  Scenario: Unsuccessful register email
    When unsuccessful register a user with email "testmail@testsoder.com"
    Then check an error is returned