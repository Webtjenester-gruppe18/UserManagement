package dtu.models;

/**
 * @author Frederik Hjorth s175109
 */

public class Merchant extends DTUPayUser {

    public Merchant() {
    }

    public Merchant(String accountId, String firstName, String lastName, String cprNumber) {
        super(accountId, firstName, lastName, cprNumber);
    }

}
