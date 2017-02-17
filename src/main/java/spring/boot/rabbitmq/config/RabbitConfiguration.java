package spring.boot.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;
import spring.boot.rabbitmq.ThreadTask;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by SDD on 2017/1/23.
 */
@EnableRabbit
@Configuration
public class RabbitConfiguration implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitRetryCache rabbitRetryCache;

/*    @Autowired
    public RabbitConfiguration(RabbitTemplate template) {
        //template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setConfirmCallback(this);
        template.setReturnCallback(this);
    }*/

    /*
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }*/

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setConfirmCallback(this);
        template.setReturnCallback(this);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    /**
     * confirm 主要是用来判断消息是否有正确到达交换机，如果有，那么就 ack 就返回 true；如果没有，则是 false。
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            if(correlationData != null) {
                rabbitRetryCache.retryNum(correlationData.getId());
            }
            logger.warn("rabbitmq发送消息出错, confirm结果: " + cause + correlationData);
        } else {
            if(correlationData != null) {
                rabbitRetryCache.del(correlationData.getId());
            }
        }
    }

    /**
     * return 表示如果消息已经正确到达交换机，但是后续处理出错了，那么就会回调 return，并且把信息执行return（前提是需要设置了 Mandatory，不设置那么就丢弃）;
     * 如果消息没有到达交换机，那么不会调用 return 的东西。
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.error("rabbitmq发送消息出错, return结果。replyCode: {}, replyText: {}, exchange: {}, routingKey: {}, message: {}", replyCode, replyText, exchange, routingKey, message);
        // 重新发布
        /*RepublishMessageRecoverer recoverer = new RepublishMessageRecoverer(rabbitTemplate, exchange, routingKey);
        Throwable cause = new Exception(new Exception("route_fail_and_republish"));
        recoverer.recover(message, cause);*/
    }
}
