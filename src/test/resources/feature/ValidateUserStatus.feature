Feature: Validate the user status from server
  Scenario: Verify the status of user
    Given The grpc server is running
    When  user send the greet message "The user name is" and name "Arjun"
    Then  verify the response with name "Arjun"
