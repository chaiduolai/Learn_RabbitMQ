package com.duoduo.rabbitmq.route;

import com.duoduo.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @ClassName WorkProduce.java
 * @Author 拾光
 * @Date 2020年07月08日 17:03:00
 * @Description 路由模式
 */
public class RouteProduce {
    /**
     * 交换机名称
     */
    static  final String DIRECT_EXCHANGE="direct_exchange";
    //队列名称
    static  final String DIRECT_QUEUE_INSERT="direct_queue_insert";
    //队列名称
    static  final String DIRECT_QUEUE_UPDATE="direct_queue_update";

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
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明（创建）队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列
         * 参数3：是否独占本次连接
         * 参数4：是否在不使用的时候删除队列
         * 参数5：队列其他参数
         */
        channel.queueDeclare(DIRECT_QUEUE_INSERT,true,false,false,null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE,true,false,false,null);
        //队列绑定交换机
        channel.queueBind(DIRECT_QUEUE_INSERT,DIRECT_EXCHANGE,"insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE,DIRECT_EXCHANGE,"update");
            /**
             *   参数  1：交换机名称，如果没有指定则使用默认  Default   Exchage
             *   参数  2：路由  key,简单模式可以传递队列名称
             *   参数  3：消息其它属性
             *   参数  4：消息内容
             */
            String message="新增了路由模式：route key为insert";
        channel.basicPublish(DIRECT_EXCHANGE,"insert",null,message.getBytes());
        System.out.println("已发送消息:"+message);
         message="新增了路由模式：route key为update";
        channel.basicPublish(DIRECT_EXCHANGE,"update",null,message.getBytes());
        System.out.println("已发送消息:"+message);

        //关闭资源
        channel.close();
        connection.close();
    }
}
