package challenge.services;

import challenge.model.Transaction;
import challenge.repository.ITransactionRepository;
import challenge.common.PaymentResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionService implements ITransactionService {

    private final String transactionURL;

    @Autowired
    ITransactionRepository transactionRepository;

    RestTemplateBuilder restTemplateBuilder;
    RestTemplate restTemplate;

    @Autowired
    public TransactionService(RestTemplateBuilder restTemplateBuilder, @Value("${url.decidir.transaction}") String transactionURL) {

        restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        this.transactionURL = transactionURL;
    }

    @Override
    public Transaction create(Integer merchantId, double amount) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));

        Transaction transaction = new Transaction();
        transaction.setStatus("PENDING");
        transaction.setDate(date);
        transaction.setMerchantId(merchantId);
        transaction.setAmount(amount);
        transaction.setStatusCode(200);

        try {
            return transactionRepository.save(transaction);
        } catch(Exception ex) {
            throw(ex);
        }
    }

    public void update(Transaction tr) {
        transactionRepository.save(tr);
    }

    @Override
    public ResponseEntity<String> checkExternalStatus(String transactionId) {

        try {

            if(transactionRepository.existsById(transactionId)) {

                Transaction tr = transactionRepository.findById(transactionId).get();
                return new ResponseEntity<String>(tr.getStatus(), HttpStatus.OK);

            } else {
                return new ResponseEntity<String>("Transacción no encontrada", HttpStatus.OK);
            }

        } catch (HttpStatusCodeException exception) {

            return new ResponseEntity<String>("Hubo un error al procesar la consulta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
