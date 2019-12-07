package challenge.controller;

import challenge.common.PaymentResponse;
import challenge.model.Payment;
import challenge.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import challenge.rabbit.ITimeoutPaymentsSender;
import challenge.rabbit.TimeoutPaymentsSender;

@EnableCircuitBreaker
@SpringBootApplication(scanBasePackages={"listener", "challenge/repository", "challenge/services", "challenge/rabbit"})
@RestController
public class TransactionController {

    private IPaymentService paymentService;
    private ITransactionService transactionService;
    private ITimeoutPaymentsSender timeoutPaymentsSender;
    private IVendorService vendorService;

    @Autowired
    TransactionController(PaymentService paymentService, ITransactionService transactionService, TimeoutPaymentsSender timeoutPaymentsSender, VendorService vendorService){
        this.paymentService = paymentService;
        this.transactionService = transactionService;
        this.timeoutPaymentsSender = timeoutPaymentsSender;
        this.vendorService = vendorService;
    }

    @RequestMapping("/")
    public String home() {
        return "Hello TRANSACTIONS!";
    }

    // Recibimos un request con el pago a procesar
    @RequestMapping("/payment")
    public ResponseEntity<String> payment(Payment payment) {

        ResponseEntity<String> response;

        try {
            // Preguntamos al microservicio de Vendedores si el vendedor existe y si tiene limite para recibir este pago.
            response = vendorService.validate(payment.getMerchantId(), payment.getAmount());

            if(response.getStatusCode().equals(HttpStatus.OK)) {
                // Si la respuesta es satisfactoria, procesamos el pago contra DECIDIR
                return this.paymentService.process(payment);
            }

        } catch (HttpStatusCodeException exception) {
            // Informamos al cliente por una validación insatisfactoria
            return new ResponseEntity<String>(exception.getMessage(), exception.getStatusCode());
        }

        return response;
    }

    //Endpoint para consultar  el estado de una transaccion contra la base local.

    @RequestMapping(value = "/transaction/{id}")
    public ResponseEntity<String> transaction(@PathVariable("id") String id)
    {
        return transactionService.checkExternalStatus(id);
    }
}