package challenge.services;

import challenge.common.PaymentResponse;
import challenge.model.Transaction;
import challenge.model.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import challenge.rabbit.ITimeoutPaymentsSender;
import challenge.repository.IPaymentRepository;
import challenge.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    private final String paymentURL;

    private RabbitTemplate rabbitTemplate;
    private IPaymentRepository paymentRepository;
    private ITransactionService transactionService;
    private ITimeoutPaymentsSender timeoutPaymentsSender;

    RestTemplateBuilder restTemplateBuilder;
    RestTemplate restTemplate;

    @Autowired
    public PaymentService(RabbitTemplate rabbitTemplate,
                          PaymentRepository paymentRepository,
                          TransactionService transactionService,
                          ITimeoutPaymentsSender timeoutPaymentsSender,
                          @Value("${url.decidir.payment}") String paymentURL,
                          RestTemplateBuilder restTemplateBuilder){

        this.rabbitTemplate = rabbitTemplate;
        this.paymentRepository = paymentRepository;
        this.transactionService = transactionService;
        this.timeoutPaymentsSender = timeoutPaymentsSender;
        this.paymentURL = paymentURL;

        restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
    }

    @Override
    public ResponseEntity<String> process(Payment payment) {

        RestTemplate rt = new RestTemplate();
        String uri = paymentURL + payment.getInstallments();

        try {
            // Usamos la cantidad de cuotas como parametro para pegarle al mockserver porque Postman no permite utilizar el Body
            // para derivar las llamadas a distintas respuestas. Por eso agrego 1 parametro mas a la URL y creo distintas respuestas basados en dicho parametro.
            Transaction tr = transactionService.create(payment.getMerchantId(), payment.getAmount());
            payment.setClientOperationId(tr.getId());

            PaymentResponse paymentResponse = rt.postForObject(uri, payment, PaymentResponse.class);

            if(paymentResponse.getTransactionStatus().equals("APPROVED")) {
                tr.setIdExternalTransaction(paymentResponse.getTransactionId());
                tr.setStatus((paymentResponse.getTransactionStatus()));

                transactionService.update(tr);
            }

            return new ResponseEntity<String>("Su pago ha sido procesado! N° de Operación: " + payment.getClientOperationId(), HttpStatus.OK);

        } catch (HttpStatusCodeException exception) {

            if(exception.getStatusCode() == HttpStatus.REQUEST_TIMEOUT || exception.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                timeoutPaymentsSender.sendPayment(payment);
            }

            return new ResponseEntity<String>("Su pago no ha podido procesarse: " + exception.getMessage(), exception.getStatusCode());
        }
    }
}
