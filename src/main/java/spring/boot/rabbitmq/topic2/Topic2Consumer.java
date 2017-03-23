package spring.boot.rabbitmq.topic2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by SDD on 2017/1/23.
 */
@RabbitListener(queues = "topic2Queue.2", containerFactory = "rabbitListenerContainerFactory")
public class Topic2Consumer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    static  int i = 0;
    @RabbitHandler
    public void process3(@Payload java.util.Date msg) throws Exception {
        logger.warn("消费1：" + ": " + msg+ ", 重试次数：" + (i++));
        throw new Exception("报错了");
        //throw new AmqpRejectAndDontRequeueException("test-dead-letter");
    }


}
