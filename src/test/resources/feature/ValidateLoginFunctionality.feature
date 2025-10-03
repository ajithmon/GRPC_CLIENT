Feature: Validate the user login functionality
  Scenario Outline: Verify user login with valid credentials
    Given The grpc server is running
    When  user login with username "<valid.username>" and password "<valid.password>"
    Then  verify login response message "Login Successful"
    Examples:

    |valid.username         |valid.password|
    |alice@example.com      |password123   |
    |charlie.j@example.com  |charlie2025   |
    |bob.smith@example.com  |qwerty789     |

  Scenario Outline: Verify user login with invalid credentials
    Given The grpc server is running
    When  user login with username "<invalid.username>" and password "<invalid.password>"
    Then  verify login response message contains "Invalid"
    Examples:

      |invalid.username       |invalid.password|
      |alice@example.com      |password12      |
      |charlie.j@example.co   |charlie2025     |
      |bob.smith@example.com  |hdo376764       |
