Feature: File Logger

  Scenario: log to file
    Given I set the logging level to "info"
    And I set the logging type to "file"
    And I run the app
    Then it should log to file

  Scenario: log to file as error only
    Given I set the logging level to "error"
    And I set the logging type to "file"
    And I run the app
    Then it should not log as "info"
    And it should not log as "warning"
