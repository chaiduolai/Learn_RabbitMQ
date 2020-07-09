package com.duoduo.rabbitmq.发布订阅模式;

import com.duoduo.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @ClassName WorkProduce.java
 * @Author 拾光
 * @Date 2020年07月08日 17:03:00
 * @Description 工作模式生产者
 */
public class PublicProduce {
    /**
     * 交换机名称
     */
    static  final String FANOUT_EXCHANGE="fanout_exchange";
    //队列名称
    static  final String FANOUT_QUEUE_1="fanout_queue_1";
    //队列名称
    static  final String FANOUT_QUEUE_2="fanout_queue_2";

    public static void main(String[] args)throws Exception {
        //创建连接
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //申明交换机
        /**
         * 申明交换机
         * 参数一： 交换机名称
         * 参数二： 交换机类型 fanout ,topic, direct,headers
         */
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        //声明（创建）队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列
         * 参数3：是否独占本次连接
         * 参数4：是否在不使用的时候删除队列
         * 参数5：队列其他参数
         */
        channel.queueDeclare(FANOUT_QUEUE_1,true,false,false,null);
        channel.queueDeclare(FANOUT_QUEUE_2,true,false,false,null);
        //队列绑定交换机
        channel.queueBind(FANOUT_QUEUE_1,FANOUT_EXCHANGE,"");
        channel.queueBind(FANOUT_QUEUE_2,FANOUT_EXCHANGE,"");
        for (int i = 1; i < 10; i++) {
            String message="你好朋友，发布订阅模式"+i;
            /**
             *   参数  1：交换机名称，如果没有指定则使用默认  Default   Exchage
             *   参数  2：路由  key,简单模式可以传递队列名称
             *   参数  3：消息其它属性
             *   参数  4：消息内容
             */
            channel.basicPublish(FANOUT_EXCHANGE,"",null,message.getBytes());
            System.out.println("已发送消息:"+message);
        }
        //关闭资源
        channel.close();
        connection.close();
    }
}
