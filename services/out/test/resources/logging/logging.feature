Feature: Console Logger

  Scenario: log to console
    Given I have the app running
    When I set the logging to console
    Then it should log to console
