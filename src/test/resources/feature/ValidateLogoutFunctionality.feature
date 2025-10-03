Feature: Validate the user logout functionality

  Background:
    Given The grpc server is running
    When  user login with username "alice@example.com" and password "password123"
    Then  verify login response message "Login Successful"

  Scenario: Verify user logout with valid token
    When  user try to logout with valid token "user.token"
    Then  verify logout response message "Logout successful"

  Scenario: Verify user logout with invalid token
    When  user try to logout with invalid token "invalid.token"
    Then  verify logout response message "Invalid token or already logged out"


