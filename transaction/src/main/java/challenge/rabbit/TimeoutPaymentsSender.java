package challenge.rabbit;

import challenge.model.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeoutPaymentsSender implements ITimeoutPaymentsSender{
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TimeoutPaymentsSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //It sends payment that threw timeout to the queue
    public void sendPayment(Payment payment) {
        this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_ORDERS, payment);
    }
}