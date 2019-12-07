package challenge.services;

import challenge.model.Merchant;
import challenge.repository.IMerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MerchantService implements IMerchantService {

    @Autowired
    private IMerchantRepository merchantRepository;

    public ResponseEntity<String> validate(int id, Number amount) {

        if(this.merchantRepository.existsById(id)) {

            Merchant vendor = this.merchantRepository.findById(id).get();
            Boolean isValid = this.CheckLimits(vendor, amount);

            if(isValid) {
                return new ResponseEntity<>("Merchant Exists and has sufficient funds", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Insufficient Funds", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Vendor not found", HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<String> update(int id, Number amount) {
        Merchant vendor = this.merchantRepository.findById(id).get();
        if(vendor != null) {
            vendor.setMerchantAccummulation(vendor.getMerchantAccummulation() + amount.floatValue());

            this.merchantRepository.save(vendor);
        } else {
            return new ResponseEntity<String>("Merchant not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Merchant updated!", HttpStatus.OK);
    }

    private Boolean CheckLimits(Merchant vendor, Number amount) {
        return vendor.getMerchantAccummulation() + amount.floatValue() < vendor.getLimit();
    }
}
