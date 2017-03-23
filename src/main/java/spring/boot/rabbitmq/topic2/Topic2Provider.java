package spring.boot.rabbitmq.topic2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import spring.boot.rabbitmq.config.RabbitTemplateHelper;

/**
 * Created by SDD on 2017/1/23.
 */
public class Topic2Provider {

    @Autowired
    private RabbitTemplateHelper rabbitTemplateHelper;


    @Scheduled(initialDelay=1000, fixedDelay=50000000)
    public void test2(){
        java.util.Date d = new java.util.Date();
        //rabbitTemplateHelper.send("topic2ModeExchange", "topic2Key", d);
        rabbitTemplateHelper.send("topic2Queue.1", d);
        System.out.println("发送消息：" + d);
    }
}
