/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package spring.boot.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginLogProvider extends Thread{
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private Queue loginLogQueue;

	//@Scheduled(fixedDelay = 1000L)
	public void send1() {
		String msg =  "生产1：" + sdf.format(new Date()) + ", " + "hello1";
		this.send(msg);
	}

	//@Scheduled(fixedDelay = 1000L)
	public void send2() {
		String msg = "生产2：" + sdf.format(new Date()) + ", " + "hello2";
		this.send(msg);
	}


	//@Scheduled(fixedDelay = 1000L)
	public void send3() {
		String msg = "生产3：" + sdf.format(new Date()) + ", " + "hello3";
		this.send(msg);
	}


	private void send(String msg){
		System.out.println(msg);
		this.rabbitTemplate.convertAndSend(loginLogQueue.getName());
		this.rabbitTemplate.convertAndSend("login_log", msg);
	}

}
