package spring.boot.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SDD on 2017/1/23.
 */
public class ThreadTask extends Thread{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private RabbitTemplate rabbitTemplate;

    private int thread;

    public static long providerCount = 0;
    public static long consumerCount = 0;
    public static long confirmCount = 0;
    public static long returnCount = 0;

    public ThreadTask(RabbitTemplate rabbitTemplate, int thread){
        this.rabbitTemplate = rabbitTemplate;
        this.thread = thread;
    }

    @Override
    public void run() {
        super.run();

        for(int i = 0; i < 5000; i++){
            String msg = "线程"+thread+"：" + sdf.format(new Date()) + ", " + "生产第" + i+"消息";
            send(msg);
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(String msg){
        System.out.println("生产第:"+ThreadTask.providerCountAdd() + ", " +  msg);
        this.rabbitTemplate.convertAndSend("thread_queue", msg);
    }

    public static synchronized String providerCountAdd(){
        ++providerCount;
        return toStr();
    }

    public static synchronized String consumerCountAdd(){
        ++consumerCount;
        return toStr();
    }

    public static synchronized String confirmCountAdd(){
        ++confirmCount;
        return toStr();
    }

    public static synchronized String returnCountAdd(){
        ++returnCount;
        return toStr();
    }

    public static String toStr(){
        return "[providerCount: " + providerCount + ", consumerCount: " + consumerCount + ", confirmCount: " + confirmCount + ", returnCount: " + returnCount+"] ";
    }
}
