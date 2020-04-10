Feature: "Login"


  @regressao @login @teste2 @Firefox
  Scenario Outline: Login with right credentials
    Given I open the homepage
    And I click in the login button
    And I use my email as "<email>" to login
    And I use my password as "<password>" to login
  	Then I click the login button
    And I verify if I am logged 
    Examples: 
      | email      											 |	password  		   | 
      |	sijed18072@fft-mail.com				 	 |    testeloja123   |	
     
    
    @regressao @login @teste3 @Firefox
  Scenario Outline: Login with invalid credentials
    Given I open the homepage
    And I click in the login button
    And I use my email as "<email>" to login
    And I use my password as "<password>" to login
    Then I click the login button
    And I verify that I am not logged 
    Examples: 
      | email      											 |	password  		   | 
      |	sijed18072@fft-mail.com				 	 |    testeloja1234  |	
       
      