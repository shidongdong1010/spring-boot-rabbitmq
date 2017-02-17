package spring.boot.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by SDD on 2017/1/20.
 */
@RabbitListener(queues = "thread_queue")
public class ThreadConsumer {

    public static long count = 0;

    @RabbitHandler
    public void process(@Payload String msg) throws Exception{
       ThreadConsumer.print("消费1：" + msg);
    }

    public static void print(String msg) throws Exception{
        System.out.println(ThreadTask.consumerCountAdd() + msg);
    }

    public static synchronized long add(){
        return ++count;
    }
}
