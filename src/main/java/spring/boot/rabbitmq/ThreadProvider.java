package spring.boot.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by SDD on 2017/1/23.
 */
public class ThreadProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(initialDelay=1000, fixedDelay=50000000000L)
    public void send() {
        for(int i = 0; i < 50; i++){
            new ThreadTask(this.rabbitTemplate, i).start();
        }
    }
}
