package spring.boot.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by SDD on 2017/1/23.
 */
@RabbitListener(queues = "testFanoutModeQueue.2", containerFactory = "rabbitListenerContainerFactory")
public class FanoutConsumer2 {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    static  int i = 0;
    @RabbitHandler
    public void process3(@Payload String msg) throws Exception{
        logger.warn("testFanoutModeQueue消费2：" + ": " + msg+ ", 重试次数：" + (i++));
        //throw new Exception("报错了");
    }

}
