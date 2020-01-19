package dtu.models;

public class AddTransactionToUserByAccountId {

    String accountId;
    String transactionId;

    public AddTransactionToUserByAccountId(String accountId, String transactionId) {
        this.accountId = accountId;
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
