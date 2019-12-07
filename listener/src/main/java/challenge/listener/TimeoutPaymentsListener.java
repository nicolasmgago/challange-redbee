package challenge.listener;

import challenge.services.ITransactionService;
import challenge.common.PaymentResponse;
import challenge.model.Payment;
import challenge.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import challenge.rabbit.ITimeoutPaymentsSender;
import challenge.rabbit.RabbitConfig;

@Component
public class TimeoutPaymentsListener {

    static final Logger logger = LoggerFactory.getLogger(TimeoutPaymentsListener.class);
    private ITransactionService transactionService;
    private ITimeoutPaymentsSender timeoutPaymentsSender;

    @Autowired
    public TimeoutPaymentsListener(ITransactionService transactionService, ITimeoutPaymentsSender timeoutPaymentsSender) {
        this.transactionService = transactionService;
        this.timeoutPaymentsSender = timeoutPaymentsSender;
    }

    //Tomamos payments cuyo resultado contra decidir fue Timeout de la cola.
    @RabbitListener(queues = RabbitConfig.QUEUE_ORDERS)
    public void processOrder(Payment payment) {

        Transaction tr = transactionService.get(payment.getClientOperationId());
        if(tr != null) {

            // Verificamos el estado de la transacción de ese pago contra decidir
            ResponseEntity<PaymentResponse> transactionResponse = transactionService.checkStatus(payment.getClientOperationId());

            if(transactionResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                // Si obtenemos timeout nuevamente, enviamos nuevamente el pago a la cola para que eventualmente sea procesado.
                this.timeoutPaymentsSender.sendPayment(payment);
            } else {
                // Si obtenemos otro tipo de respuesta, implica que tenemos el estado de la transacción y actualizamos el mismo en nuestra base de datos
                // para que matchee con el estado de la transacción en DECIDIR.
                transactionService.update(tr, transactionResponse.getStatusCode());
            }
        }
    }
}