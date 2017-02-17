package spring.boot.rabbitmq.fanout;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by SDD on 2017/1/23.
 */
public class FanoutProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Scheduled(initialDelay=1000, fixedDelay=50000000)
    //@Scheduled(fixedDelay = 5000L)
    public void test2(){
        //rabbitTemplate.convertAndSend("key1", "testTopicModeExchange消息");
        //rabbitTemplate.convertAndSend("testFanoutModeExchange", "key1", "testFanoutModeExchange消息");
        rabbitTemplate.convertAndSend("testFanoutModeExchange", "key2", "testFanoutModeExchange消息key2");
        System.out.println("调用完成:1");
    }
}
