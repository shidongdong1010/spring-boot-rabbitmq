package spring.boot.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import spring.boot.rabbitmq.fanout.FanoutConsumer1;
import spring.boot.rabbitmq.fanout.FanoutConsumer2;
import spring.boot.rabbitmq.fanout.FanoutProvider;
import spring.boot.rabbitmq.topic.DeadConsumer;
import spring.boot.rabbitmq.topic.TopicConsumer1;
import spring.boot.rabbitmq.topic.TopicConsumer2;
import spring.boot.rabbitmq.topic.TopicProvider;

import java.util.HashMap;
import java.util.Map;

@EnableScheduling
@SpringBootApplication
public class SpringBootRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRabbitmqApplication.class, args);
	}


/*	@Bean
	public SimpleProvider simpleProvider() {
		return new SimpleProvider();
	}

	@Bean
	public SimpleConsumer simpleConsumer() {
		return new SimpleConsumer();
	}*/

	/*@Bean
	public SimpleConsumer2 simpleConsumer2() {
		return new SimpleConsumer2();
	}*/

/*
	@Bean
	public Queue loginLogQueue() {
		return new Queue("login_log");
	}

	*/
/*@Bean
	public LoginLogProvider loginLogProvide() {
		return new LoginLogProvider();
	}*//*



*/
/*
	@Bean
	public LoginLogConsumer loginLogConsumer() {
		return new LoginLogConsumer();
	}
*//*


	@Bean
	public Queue key1Queue() {
		return new Queue("key1");
	}

*/
/*	@Bean
	public AckProvider ackProvider() {
		return new AckProvider();
	}

	@Bean
	public AckConsumer AckConsumer() {
		return new AckConsumer();
	}*//*


	@Bean
	public Queue threadQueue() {
		return new Queue("thread_queue");
	}
*/


/*	@Bean
	public ThreadProvider threadProvider(){
		return new ThreadProvider();
	}*/

/*	@Bean
	public ThreadConsumer threadConsumer(){
		return new ThreadConsumer();
	}

	@Bean
	public ThreadConsumer2 threadConsumer2(){
		return new ThreadConsumer2();
	}

	@Bean
	public ThreadConsumer3 threadConsumer3(){
		return new ThreadConsumer3();
	}

	@Bean
	public ThreadConsumer4 threadConsumer4(){
		return new ThreadConsumer4();
	}*/




/** topic 模式**/

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("testTopicModeExchange", true, false);
	}


	@Bean
	public TopicExchange deadExchange() {
		return new TopicExchange("exchange.dlx", true, false);
	}


	@Bean
	public Queue testTopicModeQueue1() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x-dead-letter-exchange", "exchange.dlx");//设置死信交换机
		map.put("x-dead-letter-routing-key", "queue.fail");//设置死信routingKey
		Queue queue = new Queue("testTopicModeQueue.1", true, false, false, map);
		//Queue queue = new Queue("testTopicModeQueue.1", true);
		return queue;
	}

/*	@Bean
	public Queue testTopicModeQueue2() {
		Queue queue = new Queue("testTopicModeQueue.2", true);
		return queue;
	}*/

	@Bean
	public Queue testQueueDead(){
		Queue queue = new Queue("deadQueue", true);
		return queue;
	}

	@Bean
	public Binding bindingTestTopicModeQueue1() {
		return BindingBuilder.bind(testTopicModeQueue1()).to(topicExchange()).with("key1");
	}

/*	@Bean
	public Binding bindingTestTopicModeQueue2() {
		return BindingBuilder.bind(testTopicModeQueue2()).to(topicExchange()).with("key1");
	}*/

	@Bean
	public Binding bindingTestTopicModeQueueDead() {
		return BindingBuilder.bind(testQueueDead()).to(deadExchange()).with("queue.fail");
	}


	@Bean
	public TopicProvider topicProvider() {
		return new TopicProvider();
	}

	@Bean
	public TopicConsumer1 topicConsumer1() {
		return new TopicConsumer1();
	}

/*
	@Bean
	public TopicConsumer2 topicConsumer2() {
		return new TopicConsumer2();
	}
*/

	@Bean
	public DeadConsumer deadConsumer() {
		return new DeadConsumer();
	}


/** Fanout模式 **/

/*
	@Bean
	public FanoutExchange fanout() {

		return new FanoutExchange("testFanoutModeExchange", false, false);
	}

	@Bean
	public Queue testFanoutModeQueue1() {
		return new Queue("testFanoutModeQueue.1");
	}

	@Bean
	public Queue testFanoutModeQueue2() {
		return new Queue("testFanoutModeQueue.2");
	}

	@Bean
	public Binding bindingTestFanoutModeQueue1() {
		return BindingBuilder.bind(testFanoutModeQueue1()).to(fanout());
	}

	@Bean
	public Binding bindingTestFanoutModeQueue2() {
		return BindingBuilder.bind(testFanoutModeQueue2()).to(fanout());
	}


	@Bean
	public FanoutProvider FanoutProvider() {
		return new FanoutProvider();
	}

	@Bean
	public FanoutConsumer1 fanoutConsumer1() {
		return new FanoutConsumer1();
	}
*/

/*	@Bean
	public FanoutConsumer2 fanoutConsumer2() {
		return new FanoutConsumer2();
	}*/
}
