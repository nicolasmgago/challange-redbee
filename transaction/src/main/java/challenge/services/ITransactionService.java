package challenge.services;

import challenge.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ITransactionService {
    Transaction create(Integer merchantId, double amount);

    void update(Transaction tr);

    ResponseEntity<String> checkExternalStatus(String externalTransactionId);
}
