package challenge.rabbit;

import challenge.model.Payment;
import org.springframework.stereotype.Component;

@Component
public interface ITimeoutPaymentsSender {
    void sendPayment(Payment payment);
}
