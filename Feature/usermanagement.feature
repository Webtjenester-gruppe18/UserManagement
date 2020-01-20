Feature: User management

  Scenario: Create a customer
    Given The customer has a CPR number
    When the service receives the "CREATE_CUSTOMER" event
    Then the customer is registered
    And the "CREATE_CUSTOMER_RESPONSE" event is broadcast

  Scenario: Create a Merchant
    Given The merchant has a CPR number
    When the service receives the "CREATE_MERCHANT" event
    Then the merchant is registered
    And the "CREATE_MERCHANT_RESPONSE" event is broadcast

  Scenario: Delete a customer
    Given The customer is registered
    When the service receives the "DELETE_CUSTOMER" event
    Then the customer is deleted
    And the "DELETE_CUSTOMER_RESPONSE" event is broadcast

  Scenario: Delete a merchant
    Given The merchant is registered
    When the service receives the "DELETE_MERCHANT" event
    Then the merchant is deleted
    And the "DELETE_MERCHANT_RESPONSE" event is broadcast

  Scenario: Retrieve customer
    Given the customer is registered
    When the service receives  the "RETRIEVE_CUSTOMER" event
    Then the customer is received
    And the "RETRIEVE_CUSTOMER_RESPONSE" event is broadcast

  Scenario: Retrieve merchant
    Given the merchant is registered
    When the service receives  the "RETRIEVE_MERCHANT" event
    Then the customer is received
    And the "RETRIEVE_MERCHANT_RESPONSE" event is broadcast