package spring.boot.rabbitmq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by SDD on 2017/1/23.
 */

//@Component
public class AckProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

/*    @Scheduled(initialDelay=1000, fixedDelay=50000000)
    public void test(){
        rabbitTemplate.convertAndSend("testPublishModeExchange","key1", "交换机和路由键都是正确");
    }*/

/*    @Scheduled(initialDelay=1000, fixedDelay=50000000)
    public void test1(){
        rabbitTemplate.convertAndSend("testPublishModeExchange","key11", "交换机正确，路由键错误");
    }*/

/*    @Scheduled(initialDelay=1000, fixedDelay=50000000)
    public void test2(){
        //rabbitTemplate.convertAndSend("testPublishModeExchange1","key1", "交换机错误，路由键正确");
        rabbitTemplate.convertSendAndReceive("testPublishModeExchange1","key1", "交换机错误，路由键正确");
    }*/


/*    @Scheduled(initialDelay=1000, fixedDelay=50000000)
    public void test3(){
        rabbitTemplate.convertAndSend("testPublishModeExchange1","key11", "交换机错误，路由键正确");
    }*/

    /*    @Scheduled(initialDelay=1000, fixedDelay=50000000)
    public void test2(){
        //rabbitTemplate.convertAndSend("testPublishModeExchange1","key1", "交换机错误，路由键正确");
        rabbitTemplate.convertSendAndReceive("key11", "交换机错误，路由键正确");
    }*/

    @Scheduled(initialDelay=1000, fixedDelay=50000000)
    public void test2(){
        //rabbitTemplate.convertAndSend("testPublishModeExchange1","key1", "交换机错误，路由键正确");
        rabbitTemplate.setMandatory(true);
        //Object o = rabbitTemplate.convertSendAndReceive("1", "key1", "交换机错误，路由键正确");
        // System.out.println("调用完成:" + o);
        rabbitTemplate.convertAndSend("1", "key1", "交换机错误，路由键正确");
        System.out.println("调用完成:");
    }
}
