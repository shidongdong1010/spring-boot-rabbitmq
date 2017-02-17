package spring.boot.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by SDD on 2017/1/20.
 */
@RabbitListener(queues = "login_log")
public class LoginLogConsumer {


    @RabbitHandler
    public void process(@Payload String msg) throws Exception{
        System.out.println("消费1：" + ": " + msg);
        //throw new Exception("报错了");
    }

    @RabbitHandler
    public void process2(@Payload String msg) throws Exception{
        System.out.println("消费2：" + ": " + msg);
        //throw new Exception("报错了");
    }
}
