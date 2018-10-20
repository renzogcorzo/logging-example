Feature: Console Logger

  Scenario: log to console
    Given I run the app
    When I set the logging type to "console"
    Then it should log to console

  Scenario: log to console with warnings and errors
    Given I set the logging level to "warning"
    And I run the app
    Then it should not log as "info"

   Scenario: log to console with errors only
     Given I set the logging level to "error"
     And I run the app
     Then it should not log as "info"
     And it should not log as "warning"

    Scenario: log to console with errors only and change to info
      Given I set the logging level to "error"
      And I run the app
      Then it should not log as "info"
      And it should not log as "warning"
      Given I set the logging level to "info"
      And I run the app
      Then it should log as "info"

