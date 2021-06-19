Feature: Pivotal
  Test Pivotal methods

  Scenario: Get a Project
    Given I build "GET" request
    When I execute "projects/{projectId}" request
    Then the response status code should be "OK"

  Scenario: Get a Workspace
    Given The method is "GET"
    When I execute the endpoint "my/workspaces/{workspaceId}" request
    Then status code should be "OK"

  Scenario: Get a Epic
    Given The method for epic is "GET"
    When find epic "projects/{projectId}/epics/{epicId}" request
    Then the result is "OK"

  Scenario: Get a Label
    Given The method for label is "GET"
    When find label "projects/{projectId}/labels/{labelId}" request
    Then the label result is "OK"

  Scenario: Get a Story
    Given The method for story is "GET"
    When find story "/projects/{projectId}/stories/{storyId}" request
    Then the story result is "OK"

