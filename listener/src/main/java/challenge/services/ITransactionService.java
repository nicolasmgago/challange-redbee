package challenge.services;

import challenge.common.PaymentResponse;
import challenge.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ITransactionService {
    void update(Transaction tr);

    ResponseEntity<PaymentResponse> checkStatus(String id);

    Transaction get(String id);

    void update(Transaction tr, HttpStatus statusCode);
}
