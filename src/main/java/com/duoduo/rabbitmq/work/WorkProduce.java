package com.duoduo.rabbitmq.work;

import com.duoduo.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @ClassName WorkProduce.java
 * @Author 拾光
 * @Date 2020年07月08日 17:03:00
 * @Description 工作模式生产者
 */
public class WorkProduce {
    static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        //通过使用工具类来创建连接
        Connection connection = ConnectionUtil.getConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //声明（创建）队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列
         * 参数3：是否独占本次连接
         * 参数4：是否在不使用的时候删除队列
         * 参数5：队列其他参数
         */
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //要发送的消息
        for (int i = 0; i < 20; i++) {
            String msg="你好啊，小宝贝-----工作模式--"+i;
            /**
             *   参数  1：交换机名称，如果没有指定则使用默认  Default   Exchage
             *   参数  2：路由  key,简单模式可以传递队列名称
             *   参数  3：消息其它属性
             *   参数  4：消息内容
             */
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("已发送消息:"+msg);
        }


        //关闭资源
        channel.close();
        connection.close();
    }
}
