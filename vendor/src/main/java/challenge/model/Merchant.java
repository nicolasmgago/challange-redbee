package challenge.model;

import javax.persistence.*;

@Entity
@Table(name="Merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private double MerchantLimit;
    private double MerchantAccummulation;

    public double getMerchantLimit() {
        return MerchantLimit;
    }

    public void setMerchantLimit(double merchantLimit) {
        MerchantLimit = merchantLimit;
    }

    public double getMerchantAccummulation() {
        return MerchantAccummulation;
    }

    public void setMerchantAccummulation(double merchantAccummulation) {
        MerchantAccummulation = merchantAccummulation;
    }

    public double getLimit() {
        return MerchantLimit;
    }

    public void setLimit(double limit) {
        MerchantLimit = limit;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
