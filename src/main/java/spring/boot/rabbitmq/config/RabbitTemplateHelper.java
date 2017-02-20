package spring.boot.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by SDD on 2017/2/14.
 */
@Service
public class RabbitTemplateHelper {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RabbitRetryCache rabbitRetryCache;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitTemplateHelper(RabbitRetryCache rabbitRetryCache, RabbitTemplate rabbitTemplate){
        this.rabbitRetryCache = rabbitRetryCache;
        this.rabbitTemplate = rabbitTemplate;

        // 启动
        retry();
    }

    /**
     * 发送消息
     * @param queue 对列名称
     * @param msg 消息体
     */
    public void send(String queue, Object msg){
        String id = rabbitRetryCache.add(null, null, queue, msg);

        rabbitTemplate.convertAndSend(queue, msg, new CorrelationData(id));
    }

    /**
     * 发送消息
     * @param exchange 交换机名称
     * @param routingKey 路由Key
     * @param msg 消息体
     */
    public void send(String exchange, String routingKey, Object msg){
        String id = rabbitRetryCache.add(exchange, routingKey, null, msg);

        rabbitTemplate.convertAndSend(exchange, routingKey, msg, new CorrelationData(id));
    }


    /**
     * 重发
     */
    public void retry(){
        new Thread(new Runnable(){

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Collection<RabbitRetryCache.RabbitMessage> list = rabbitRetryCache.getRabbitMessage();
                    if (!list.isEmpty()) {
                        for (RabbitRetryCache.RabbitMessage message : list) {
                            if (message.getRetryNum() < 5) {
                                // 重试
                                rabbitRetryCache.retry(message.getId());
                                if (message.getExchange() != null && message.getRoutingKey() != null) {
                                    rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), message.getMessage());
                                } else {
                                    rabbitTemplate.convertAndSend(message.getQueue(), message.getMessage());
                                }
                            } else {
                                // TODO 记入加入数据库
                                rabbitRetryCache.del(message.getId());
                                logger.error("发送重试5次以上，记录到数据库: {}", message);
                            }
                        }
                    }
                }
            }
        }).start();
    }
}
