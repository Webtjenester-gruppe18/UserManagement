Feature: User management

  Scenario: Create a customer
    When the service receives the "CREATE_CUSTOMER" event
    Then the customer is registered
    And the "CREATE_CUSTOMER_RESPONSE" event is broadcast

  Scenario: Create a Merchant
    When the service receives the "CREATE_MERCHANT" event
    Then the merchant is registered
    And the "CREATE_MERCHANT_RESPONSE" event is broadcast

  Scenario: Delete a customer
    When the service receives the "DELETE_CUSTOMER" event
    Then the customer is deleted
    And the "DELETE_CUSTOMER_RESPONSE" event is broadcast

  Scenario: Delete a merchant
    When the service receives the "DELETE_MERCHANT" event
    Then the merchant is deleted
    And the "DELETE_MERCHANT_RESPONSE" event is broadcast

  Scenario: Retrieve customer
    When the service receives the "RETRIEVE_CUSTOMER" event
    Then the customer is received
    And the "RETRIEVE_CUSTOMER_RESPONSE" event is broadcast

  Scenario: Retrieve merchant
    When the service receives the "RETRIEVE_MERCHANT" event
    Then the merchant is received
    And the "RETRIEVE_MERCHANT_RESPONSE" event is broadcast