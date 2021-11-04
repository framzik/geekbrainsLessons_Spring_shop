Feature: Add/delete category

  Scenario Outline: Add category successful
    Given I open web browser
    When I navigate to category.html page
    And I provide username as "<username>" and password as "<password>"
    And I click on login button
    Then I see button "Add Category"
    And I click on Add category button
    And I provide Name as "<categoryName>" and Description as "<description>"
    And I click on Submit button
    Then in table i see category "<categoryName>" "<description>"
    And i delete category "<categoryName>" "<description>"
    Then in table i do not see category "<categoryName>" "<description>"

    Examples:
      | username | password | categoryName | description |
      | admin    | admin    | some         | best        |