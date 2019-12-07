package challenge.common;

public class PaymentResponse {
    private Integer transactionId;
    private String transactionStatus;
    private Integer code;

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionID) {
        this.transactionId = transactionID;
    }
}
