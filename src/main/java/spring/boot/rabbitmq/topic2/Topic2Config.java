package spring.boot.rabbitmq.topic2;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.boot.rabbitmq.topic.TopicProvider;

import java.util.Map;

/**
 * Created by SDD on 2017/3/22.
 */
@Configuration
public class Topic2Config {

/*	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("topic2ModeExchange", true, false);
	}*/



/*	@Bean
	public Binding bindingTestTopicModeQueue1() {
		return BindingBuilder.bind(testTopicModeQueue1()).to(topicExchange()).with("key1");
	}*/

	// 生产者配置
/*	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("topic2ModeExchange", true, false);
	}

	@Bean
	public Binding bindingTestTopicModeQueue1() {
		return BindingBuilder.bind(topic2Queue()).to(topicExchange()).with("topic2Key");
	}*/

/*	@Bean
	public Queue topic2Queue() {
		Queue queue = new Queue("topic2Queue", true);
		return queue;
	}*/

/*	@Bean
	public Topic2Provider topic2Provider() {
		return new Topic2Provider();
	}*/


	// 消费者配置：
	@Bean
	public Queue topic2Queue() {
		Queue queue = new Queue("topic2Queue.2", true);
		return queue;
	}
	@Bean
	public Topic2Consumer topic2Consumer() {
		return new Topic2Consumer();
	}

}
