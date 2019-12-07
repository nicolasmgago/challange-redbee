package challenge.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IVendorService {
    ResponseEntity<String> validate(Integer id, double amount);
}
