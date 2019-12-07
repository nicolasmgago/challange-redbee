package challenge.services;

import org.springframework.stereotype.Service;

@Service
public class VendorService implements IVendorService {
//
//    @Override
//    public ResponseEntity<String> validate(Integer id, Number amount) {
//
//        ResponseEntity<String> response;
//        RestTemplate rt = new RestTemplate();
//        VendorRequest vr = new VendorRequest();
//        vr.setId(id);
//        vr.setAmount(amount);
//
//        try {
//            // We validate if merchant exists and his limits
//            response = rt.postForEntity("http://localhost:8080/validate", vr, String.class);
//
//            return response;
//
//        } catch (HttpStatusCodeException exception) {
//
//            return new ResponseEntity<String>(exception.getResponseBodyAsString(), exception.getStatusCode());
//        }
//    }
}
