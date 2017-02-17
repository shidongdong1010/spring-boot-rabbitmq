package spring.boot.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by SDD on 2017/1/20.
 */
@RabbitListener(queues = "thread_queue")
public class ThreadConsumer2 {

    @RabbitHandler
    public void process(@Payload String msg) throws Exception{
        ThreadConsumer.print("消费2：" + msg);
    }
}
