package spring.boot.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by SDD on 2017/1/20.
 */
@RabbitListener(queues = "foo")
public class SimpleConsumer {


    @RabbitHandler
    public void process(@Payload String foo) throws Exception{
        System.out.println("SimpleConsumer消费：" + ": " + foo);
        //throw new Exception("报错了");
    }
}
