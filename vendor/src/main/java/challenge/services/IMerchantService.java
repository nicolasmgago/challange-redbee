package challenge.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IMerchantService {
    ResponseEntity<String> validate(int id, Number amount);
}
