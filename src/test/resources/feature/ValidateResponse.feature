Feature: Verify Greet Functionality
  Scenario: Verify greet response data
    Given The grpc server is running
    When  user sends the greet message
    Then  verify response message "Hello from server"

    Scenario: Verify greet by passing name and message
      Given The grpc server is running
      When  user sends the greet message "How are you" and name "Ajith"
      Then  verify response message "Hello from serve"