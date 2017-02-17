package spring.boot.rabbitmq.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by SDD on 2017/2/14.
 */
@Service
public class RabbitTemplateHelper {

    @Autowired
    private RabbitRetryCache rabbitRetryCache;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param queue 对列名称
     * @param msg 消息体
     */
    public void send(String queue, Object msg){
        String id = rabbitRetryCache.add(msg);

        rabbitTemplate.convertAndSend(queue, msg, new CorrelationData(id));
    }

    /**
     * 发送消息
     * @param exchange 交换机名称
     * @param routingKey 路由Key
     * @param msg 消息体
     */
    public void send(String exchange, String routingKey, Object msg){
        String id = rabbitRetryCache.add(msg);

        rabbitTemplate.convertAndSend(exchange, routingKey, msg, new CorrelationData(id));
    }
}
