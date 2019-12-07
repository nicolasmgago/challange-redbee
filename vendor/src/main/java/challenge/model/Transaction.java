package challenge.model;

import java.io.Serializable;

public class Transaction implements Serializable {

    private Integer Id;

    private Integer ExternalId;
    private String StatusCode;
    private String StatusDescription;
    private java.util.Date TransactionDate;
    private double Amount;
    private Integer MerchantId;

    public Integer getExternalId() {
        return ExternalId;
    }

    public void setExternalId(Integer externalId) {
        ExternalId = externalId;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public java.util.Date getDate() {
        return TransactionDate;
    }

    public void setDate(java.util.Date date) {
        TransactionDate = date;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public Integer getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(Integer merchantId) {
        MerchantId = merchantId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
