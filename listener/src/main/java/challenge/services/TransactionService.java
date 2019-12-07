package challenge.services;

import challenge.common.PaymentResponse;
import challenge.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import challenge.repository.ITransactionRepository;

import java.util.*;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    ITransactionRepository transactionRepository;

    String transactionURL;

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
    public void update(Transaction tr) {
        transactionRepository.save(tr);
    }

    @Override
    public ResponseEntity<PaymentResponse> checkStatus(String id) {

        String uri = transactionURL + "{id}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        PaymentResponse paymentResponse = restTemplate.getForObject(uri, PaymentResponse.class, params);

        return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.OK);
    }

    @Override
    public Transaction get(String id) {
        if(transactionRepository.existsById(id)) {
            return transactionRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public void update(Transaction tr, HttpStatus statusCode) {
        tr.setStatus(statusCode.getReasonPhrase());

        transactionRepository.save(tr);
    }
}
