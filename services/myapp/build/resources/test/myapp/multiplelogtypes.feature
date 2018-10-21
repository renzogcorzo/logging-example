Feature: Multiple Logger Types

Scenario: log to console and file
  Given I set the logging type to
  |   console    |
  |   file       |
  And I run the app
  Then it should log to
  |   console    |
  |   file       |
  And it should not log to
  |   database    |