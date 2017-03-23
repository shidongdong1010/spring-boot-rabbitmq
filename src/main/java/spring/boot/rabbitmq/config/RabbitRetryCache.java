package spring.boot.rabbitmq.config;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息缓存
 * Created by SDD on 2017/2/16.
 */
@Service
public class RabbitRetryCache {

    public static Map<String, RabbitMessage> map = new ConcurrentHashMap<>();

    /**
     * 添加消息到缓存
     * @param message
     */
    public synchronized String add(String exchange, String routingKey, String queue, Object message){
        String id = UUID.randomUUID().toString();

        long time = System.currentTimeMillis();
        RabbitMessage msg = new RabbitMessage(id, time, time, exchange, routingKey, queue, message, 0);
        map.put(id, msg);
        return id;
    }

    /**
     * 更新重发次数
     * @param id
     */
    public synchronized boolean retry(String id){
        if(!map.containsKey(id)){
            return false;
        }
        RabbitMessage msg = map.get(id);
        long time = System.currentTimeMillis();
        RabbitMessage newMsg = new RabbitMessage(id, msg.getTime(), time, msg.exchange, msg.getRoutingKey(), msg.getQueue(), msg.getMessage(), msg.getRetryNum() + 1);
        map.put(id, newMsg);
        return true;
    }

    /**
     * 更新重发次数
     * @param id
     */
    public synchronized boolean del(String id){
        if(!map.containsKey(id)){
            return false;
        }
        map.remove(id);
        return true;
    }

    /**
     * 获得所有消息
     * @return
     */
    public Collection<RabbitMessage> getRabbitMessage(){
        return map.values();
    }

    /**
     * 消息对象
     */
    public static class RabbitMessage{
        private String id;
        /**
         * 发送时间
         */
        private long time;
        /**
         * 最后发送时间
         */
        private long lastTime;
        /**
         * 交换机
         */
        private String exchange;
        /**
         * 路由
         */
        private String routingKey;
        /**
         * 对列名称
         */
        private String queue;
        /**
         * 消息
         */
        private Object message;
        /**
         * 重试次数
         */
        private int retryNum;

        public RabbitMessage(String id, long time, long lastTime, String exchange, String routingKey, String queue, Object message, int retryNum){
            this.id = id;
            this.time = time;
            this.lastTime = lastTime;
            this.exchange = exchange;
            this.routingKey = routingKey;
            this.queue = queue;
            this.message = message;
            this.retryNum = retryNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public long getLastTime() {
            return lastTime;
        }

        public void setLastTime(long lastTime) {
            this.lastTime = lastTime;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public int getRetryNum() {
            return retryNum;
        }

        public void setRetryNum(int retryNum) {
            this.retryNum = retryNum;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

        public String getQueue() {
            return queue;
        }

        public void setQueue(String queue) {
            this.queue = queue;
        }

        @Override
        public String toString() {
            return "RabbitMessage{" +
                    "id='" + id + '\'' +
                    ", time=" + time +
                    ", lastTime=" + lastTime +
                    ", exchange='" + exchange + '\'' +
                    ", routingKey='" + routingKey + '\'' +
                    ", queue='" + queue + '\'' +
                    ", message=" + message +
                    ", retryNum=" + retryNum +
                    '}';
        }
    }
}
