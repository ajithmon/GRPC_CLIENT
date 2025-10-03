Feature: Validate client streaming functionality
  Scenario: Verify the client streams message and server responds correctly
    Given The grpc server is running
    When  the client sends the following messages in a stream "Hi,How are you,This is client streaming"
    Then  the client receives a response "3"
    And   the stream completes successfully