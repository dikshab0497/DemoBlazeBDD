Feature: Dropdown Selection
  
  @DropdownSelection
  Scenario: Verify user can select value from dropdown
  Given I navigate to the dropdown page
  When I select "Option 2" from the dropdown
  Then the selected value should be "Option 2"

    
  @HoverAction  
  Scenario: Verify hover displays sub-menu text
  Given I navigate to the hover page
  When I hover over the first image
  Then I should see the user information displayed
  
  @DragAndDrop
  Scenario: Verify drag and drop functionality
  Given I navigate to the drag and drop page
  When I drag the element A and drop it onto element B
  Then the elements should be swapped successfully
    
  @WindowSwitch
  Scenario: Verify new window opens and switch works correctly
  Given I navigate to the window page
  When I click on the "Click Here" link
  Then a new window should open
  And I switch to the new window
  And I verify the page title is "New Window"
  And I close the new window and return to the parent window
  
  @TableValidation
  Scenario: Validate table sorting by last name
    Given I navigate to the table page
    When I click on the "Last Name" column header
    Then the table should be sorted in ascending order by "Last Name"
    
    