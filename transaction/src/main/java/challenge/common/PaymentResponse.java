package challenge.common;

import java.io.Serializable;

public class PaymentResponse implements Serializable {
    private Integer transactionId;
    private String transactionStatus;
    private Integer code;

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionID) {
        this.transactionId = transactionID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
