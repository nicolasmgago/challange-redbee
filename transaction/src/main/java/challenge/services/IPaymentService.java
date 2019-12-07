package challenge.services;

import challenge.model.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IPaymentService {
    ResponseEntity<String> process(Payment payment);
}
