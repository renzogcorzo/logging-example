Feature: DB Logger

  Scenario: log to database
    Given I set the logging level to "info"
    And I set the logging type to "database"
    And I run the app
    Then it should log to database