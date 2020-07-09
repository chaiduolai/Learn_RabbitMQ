package com.duoduo.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @ClassName ConnectionUtil.java
 * @Author 拾光
 * @Date 2020年07月07日 18:43:00
 * @Description 连接mq工具类
 */
public class ConnectionUtil {
    public static Connection getConnection() throws Exception {
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
        //创建连接
        return connectionFactory.newConnection();
    }
}
