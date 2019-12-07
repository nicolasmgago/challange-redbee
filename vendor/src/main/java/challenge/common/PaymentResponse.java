package challenge.common;

public class PaymentResponse {
    private String transactionId;

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    private String transactionStatus;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionID) {
        this.transactionId = transactionID;
    }
}
