Feature: "Buy a product"

   @regressao @compra	@teste1	@Firefox
  Scenario Outline: Choosing  a product
    Given I open the homepage
    And I search for "<product>"
    Then I chose a product to buy with the index <index>
    And I send it to my cart
    Examples: 
      | product                 |index      | 
      | caneleira 					    | 2         | 
      | caneleira 					    | 3         | 
      | caneleira 					    | 4         | 
      