package spring.boot.rabbitmq.config;

import org.springframework.stereotype.Service;

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
    public synchronized String add(Object message){
        String id = UUID.randomUUID().toString();

        long time = System.currentTimeMillis();
        RabbitMessage msg = new RabbitMessage(id, time, time, message, 0);
        map.put(id, msg);
        return id;
    }

    /**
     * 更新重发次数
     * @param id
     */
    public synchronized boolean retryNum(String id){
        if(!map.containsKey(id)){
            return false;
        }
        RabbitMessage msg = map.get(id);
        long time = System.currentTimeMillis();
        RabbitMessage newMsg = new RabbitMessage(id, msg.getTime(), time, msg.getMessage(), msg.getRetryNum() + 1);
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
     * 消息对象
     */
    public static class RabbitMessage{
        private String id;
        private long time;
        private long lastTime;
        private Object message;
        private int retryNum;

        public RabbitMessage(String id, long time, long lastTime, Object message, int retryNum){
            this.id = id;
            this.time = time;
            this.lastTime = lastTime;
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
    }
}
