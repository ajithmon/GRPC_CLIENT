Feature: Verify Greet Functionality with json data
  Scenario: Verify greet by passing json data
    Given The grpc server is running
    When  user sends the greet message from "input_message.json"
    Then  verify response message "Hello from server"