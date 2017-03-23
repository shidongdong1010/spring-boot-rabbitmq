package spring.boot.rabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by SDD on 2017/1/23.
 */
@RabbitListener(queues = "testTopicModeQueue.1.sdd", containerFactory = "rabbitListenerContainerFactory")
public class TopicConsumer1 {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    static  int i = 0;
    @RabbitHandler
    public void process3(@Payload java.util.Date msg) throws Exception {
        logger.warn("消费1：" + ": " + msg+ ", 重试次数：" + (i++));
        throw new Exception("报错了");
        //throw new AmqpRejectAndDontRequeueException("test-dead-letter");
    }


}
