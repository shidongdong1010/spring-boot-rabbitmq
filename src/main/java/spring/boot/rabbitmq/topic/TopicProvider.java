package spring.boot.rabbitmq.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spring.boot.rabbitmq.config.RabbitTemplateHelper;

/**
 * Created by SDD on 2017/1/23.
 */
public class TopicProvider {

    @Autowired
    private RabbitTemplateHelper rabbitTemplateHelper;


    //@Scheduled(initialDelay=1000, fixedDelay=50000000)
    @Scheduled(fixedDelay = 5000L)
    public void test2(){
        //rabbitTemplate.convertAndSend("key1", "testTopicModeExchange消息");
        java.util.Date d = new java.util.Date();
        rabbitTemplateHelper.send("testTopicModeExchange", "key1", d);
        System.out.println("发送消息：" + d);
    }
}
