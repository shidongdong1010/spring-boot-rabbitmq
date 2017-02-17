package spring.boot.rabbitmq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by SDD on 2017/1/23.
 */

@RabbitListener(
        bindings = @QueueBinding(
                value = @org.springframework.amqp.rabbit.annotation.Queue(value = "testPublishModeQueue1",durable = "true"),
                exchange = @Exchange(value = "testPublishModeExchange",type = ExchangeTypes.TOPIC),
                key = "key1")
)
public class AckConsumer {

    @RabbitHandler
    public void process3(@Payload String msg) throws Exception{
        System.out.println("消费1：" + ": " + msg);
        //throw new Exception("报错了");
    }

}
