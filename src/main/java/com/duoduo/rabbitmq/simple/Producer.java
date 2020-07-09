package com.duoduo.rabbitmq.simple;

import com.duoduo.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @ClassName Producer.java
 * @Author 拾光
 * @Date 2020年07月07日 17:58:00
 * @Description mq生产者
 */
public class Producer {
    static final String QUEUE_NAME = "simple_que";

    public static void main(String[] args) throws Exception {
        //创建连接工厂
        ConnectionFactory connectionFactory=new ConnectionFactory();
        //主机地址;默认为 localhost
        connectionFactory.setHost("localhost");
        //虚拟主机名称 默认为 /
        connectionFactory.setVirtualHost("/");
        //设置端口 默认为 5672
        connectionFactory.setPort(5672);
        //连接用户名；默认为  guest
        connectionFactory.setUsername("guest");
        //连接密码；默认为  guest
        connectionFactory.setPassword("guest");
        //创建新的连接
        Connection connection = connectionFactory.newConnection();
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
        String msg="你好啊，小宝贝";
        /**
         *   参数  1：交换机名称，如果没有指定则使用默认  Default   Exchage
         *   参数  2：路由  key,简单模式可以传递队列名称
         *   参数  3：消息其它属性
         *   参数  4：消息内容
         */
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("已发送消息:"+msg);
        //关闭资源
        channel.close();
        connection.close();
    }
}
