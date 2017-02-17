package spring.boot.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by SDD on 2017/1/20.
 */
@RabbitListener(queues = "foo")
public class SimpleConsumer2 {


    @RabbitHandler
    public void process(@Payload String foo) throws Exception{
        System.out.println("SimpleConsumer2消费：" + ": " + foo);
        //throw new Exception("报错了");
    }
}
