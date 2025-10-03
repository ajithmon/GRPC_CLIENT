Feature: Validate bidirectional streaming functionality
  Scenario: Verify the client streams message and server streams correctly
    Given The grpc server is running
    When  the client sends the following messages "Hi,How are you,This is bidirectional streaming"