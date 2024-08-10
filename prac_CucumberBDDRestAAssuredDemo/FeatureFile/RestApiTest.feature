Feature: Dummy Rest API Functionality Scenarios

  Scenario Outline: Dummy Rest Api GET Objects
    Given Get Call to "<url>"
    Then Response Code "<responseMessage>" is validated

    Examples:
      | url      | responseMessage |
      | /6	     | 200             |

  Scenario Outline:  Verify Response Data Fields Count
    Given Get Call to "<url>"
    Then Response is array total "<total>"

    Examples:
      | url      | total |
      | /6       | 2    |