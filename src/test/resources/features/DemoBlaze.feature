Feature: Account Flow - Login functionality

  
@LoginWithValidCred
  Scenario: TC001 - Login with valid credentials
    When user clicks on LogIn button
    Then user enters username and password
    Then user should be logged in and redirected to homepage
    
 @LoginWithInValidCred
  Scenario: TC002 - Login with Invalid credentials
    When user clicks on LogIn button
    Then user enters username and password
    Then user should validate message in Alert Box 
  
 @SignUp
  Scenario: TC003 - Sign Up with New credentials
    When user clicks on SignUP button
    Then user enters username and password For SignUP
    Then user validate successful message in Alert Box 
 
 @LogOut
  Scenario: TC004 - LogOut Functionality
    When user clicks on LogIn button
    Then user enters username and password
    Then user should be logged in and redirected to homepage
    When user clicks on LogOut button
 
 @SignUpWithExistingCred
  Scenario: TC005 - Sign Up with Existing credentials
    When user clicks on SignUP button
    Then user enters username and password For SignUP
    Then user validate Warning message in Alert Box 
 
 @FieldValidationOnSignUp
  Scenario: TC005 - Field Validation on SignUp
    When user clicks on SignUP button
    Then user enters username and password For SignUP 
    Then user validate Warning message While signUp in Alert Box 
 
 @BrowseCategort
  Scenario: TC006 - Validation of selected category appears or not
    When User clicks on the category of product
    Then All displayed products should belong to the selected category
  
  @SearchProduct
  Scenario: TC007 - Validation of selected product from selected category
    When User clicks on the category of product
    Then All displayed products should belong to the selected category
    Then User click on the product
    Then User check selected product is display
   
  @AddToCart
  Scenario: TC008 - Validation of product added in Cart
    When User clicks on the category of product
    Then All displayed products should belong to the selected category
    Then User click on the product
    Then User check selected product is display
    Then User click AddToCartButton
    Then User click on Cart Link 
    Then User Validate product in Cart
  
  @DeleteProductFromCart
  Scenario: TC009 - Validation of product delete from Cart
    When User clicks on the category of product
    Then All displayed products should belong to the selected category
    Then User click on the product
    Then User check selected product is display
    Then User click AddToCartButton
    Then User click on Cart Link 
    Then User Validate product in Cart
    Then User Delete Product from Cart
    Then User Validate Product is Deleted 
  
  @ValidateProductDetails
  Scenario: TC010 - Validation of selected product details from selected category
    When User clicks on the category of product
    Then All displayed products should belong to the selected category
    Then User click on the product
    Then User check selected product is display
    Then User validates product details on Product Page
    
  @ScrollingNavigation
  Scenario: TC011 - Validation of selected product details from selected category
   When User clicks on the category of product
   Then All displayed products should belong to the selected category
   Then User fetches all products across pages 
   
   @PlaceOrder
  Scenario: TC012 - Place order with valid details
  	When user clicks on LogIn button
    Then user enters username and password
    Then user should be logged in and redirected to homepage
    When User clicks on the category of product
    Then All displayed products should belong to the selected category
    Then User click on the product
    Then User check selected product is display
    Then User click AddToCartButton
    Then User click on Cart Link 
    Then User Validate product in Cart
    Then User Place an Order
    Then User Enter Deatils on Purchase Order Form
    Then User Validate Details From confirmation Screen
  
  @PlaceOrderWithMissingData
  Scenario: TC012 - Place order with valid details
  	When user clicks on LogIn button
    Then user enters username and password
    Then user should be logged in and redirected to homepage
    When User clicks on the category of product
    Then All displayed products should belong to the selected category
    Then User click on the product
    Then User check selected product is display
    Then User click AddToCartButton
    Then User click on Cart Link 
    Then User Validate product in Cart
    Then User Place an Order
    Then User Enter Deatils on Purchase Order Form
    Then User Enter On Alert Box that speficies Data missing
    
    @MultipleProductSelection11
  	Scenario: TC0013 - Validate total price calculation
    When User clicks on the category of product
    Then All displayed products should belong to the selected category
    Then User click on the product
    Then User check selected product is display
    Then User click AddToCartButton
    Then User click on Cart Link 
    Then User Validate product in Cart
  
  	@MultipleProductSelection
  	Scenario: TC0014 - Add multiple products and verify total
  	When user clicks on LogIn button
    Then user enters username and password
    Then user should be logged in and redirected to homepage
    Given User add multiple products from Excel
    Then User click on Cart Link
    Then User Validate total amount
  
  	@ScrollTillParticularElement
  	Scenario: TC0015 - Scroll the page till particular element comes
  	When User clicks on the category of product
  	Then User scroll the page till particular product

  	@KeyboardAction
  	Scenario: TC0016 - Login using Keyboard Actions
  	When user clicks on LogIn button
    Then user enters details using keyboard actions
    Then user should be logged in and redirected to homepage