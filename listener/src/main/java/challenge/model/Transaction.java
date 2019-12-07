package challenge.model;

import challenge.common.AbstractBaseEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Transaction extends AbstractBaseEntity {

    private String status;
    private Integer statusCode;
    private Integer idExternalTransaction;
    private Integer merchantId;
    private Date date;
    private double amount;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getIdExternalTransaction() {
        return idExternalTransaction;
    }

    public void setIdExternalTransaction(Integer idExternalTransaction) {
        this.idExternalTransaction = idExternalTransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
