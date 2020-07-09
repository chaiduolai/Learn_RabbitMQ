package com.duoduo.rabbitmq.work;

import com.duoduo.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName Consumer.java
 * @Author 拾光
 * @Date 2020年07月07日 18:47:00
 * @Description mq消费者
 */
public class WorkConsumer1 {
    static final String QUEUE_NAME = "work_queue";
    public static void main(String[] args) throws Exception {
        //获取连接
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
        channel.queueDeclare(WorkConsumer1.QUEUE_NAME,true,false,false,null);
        //一次只接受并处理一条消息
        channel.basicQos(1);
        //创建消费者
       DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
           /**
            * @param consumerTag 消息者标签
            * @param envelope  消息包的内容，可以从中获取ID，消息交换机，消息路由
            * @param properties  属性信息
            * @param body 消息
            */
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               try {
                   //路由key
                   System.out.println("路由key为："+envelope.getRoutingKey());
                   //交换机
                   System.out.println("交换机为："+envelope.getExchange());
                   //消息ID
                   System.out.println("消息id为："+envelope.getDeliveryTag());
                   //收到的消息
                   System.out.println("收到的消息为："+new String(body,"utf-8"));
                   Thread.sleep(10000);
                   //确认消息
                   channel.basicAck(envelope.getDeliveryTag(),false);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       };
       //监听消息
        /**
         * 参数一 队列名称
         * 参数二 是否自动确认
         * 参数三 消息接受到后回调
         */
        channel.basicConsume(WorkConsumer1.QUEUE_NAME,false,defaultConsumer);

        //关闭资源
//        channel.close();
//        connection.close();
    }
}
