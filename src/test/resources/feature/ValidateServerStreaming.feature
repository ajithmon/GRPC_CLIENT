Feature: Server streaming greetings
  Scenario: Client sends a name and receives multiple greeting messages
    Given The grpc server is running
    When the client sends the name "Alice" to the server
    Then the client should receive 3 greeting messages